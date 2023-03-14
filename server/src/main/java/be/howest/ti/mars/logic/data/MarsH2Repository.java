package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.domain.*;
import be.howest.ti.mars.logic.domain.Child;
import be.howest.ti.mars.logic.domain.ChildcareCenter;
import be.howest.ti.mars.logic.domain.Parent;
import be.howest.ti.mars.logic.domain.events.*;
import be.howest.ti.mars.logic.domain.events.EmergencyStatus;
import be.howest.ti.mars.logic.exceptions.RepositoryException;
import org.h2.tools.Server;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
This is only a starter class to use an H2 database.
In this start project there was no need for a Java interface MarsRepository.
Please always use interfaces when needed.

To make this class useful, please complete it with the topics seen in the module OOA & SD
 */

public class MarsH2Repository {
    private static final String SQL_ALLCHILDCARECENTERS = "select * from childcarecenter;";
    private static final Logger LOGGER = Logger.getLogger(MarsH2Repository.class.getName());

    private static final String SQL_ALLCHILDREN = "select * from child;";

    private static final String SQL_CHILDREN_FROM_PARENT = "SELECT CHILD.* FROM CHILD LEFT JOIN FAMILY ON CHILD.CHILDID = FAMILY.CHILDID LEFT JOIN PARENT ON PARENT.PARENTID = FAMILY.PARENTID where PARENT.PARENTID = ?;";

    private static final String SQL_PARENT_FROM_CHILD = "SELECT PARENT.* FROM PARENT LEFT JOIN FAMILY ON PARENT.PARENTID = FAMILY.PARENTID LEFT JOIN CHILD ON CHILD.CHILDID = FAMILY.CHILDID where CHILD.CHILDID = ?;";

    private static final String SQL_CHILDREN_FROM_CHILDCARECENTER = "select * from child where CHILDCARECENTERID = ?;";
    private static final String SQL_CARETAKERS_FROM_CHILDCARECENTER = "select * from caretaker where childcareCenterID = ?;";

    private static final String SQL_CHILD_BY_ID = "select * from child where childID = ?;";
    private static final String SQL_PARENT_BY_ID = "select * from parent where parentID = ?;";
    private static final String SQL_EVENTS_WITH_RANGE = "select * from event where CHILDID = ? AND dateandtime BETWEEN ? AND ?;";
    private static final String SQL_CHILDCARECENTER_BY_ID = "select * from childcarecenter where childcareCenterID = ?;";
    private static final String SQL_EVENTTYPE_FROM_ID = "select NAME from EVENTTYPES where TYPEID = ?";
    private static final String SQL_ALL_EVENTTYPES = "select NAME from EVENTTYPES";
    private static final String SQL_UPDATE_CENTER_ID = "update CHILD set childcarecenterid = ? where childid = ?";
    private static final String NAME_KEY = "NAME";
    private static final String CHILDID_KEY = "CHILDID";
    private static final String PARENT_KEY = "YEAR";
    private static final String CHILDCARECENTERNAME_KEY = "CHILDCARECENTERNAME";
    private static final String CHILDCARECENTER_ID_KEY = "CHILDCARECENTERID";
    public static final String CHILDCARECENTER_LIMIT_KEY = "CHILDLIMIT";
    private static final String BIRTHDATE_KEY = "DOB";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final String PARENT_ID_KEY = "parentId";
    public static final String EVENT_ID_KEY = "EVENTID";
    public static final String DATETIME_KEY = "dateandtime";
    public static final String EVENT_DESCRIPTION_KEY = "description";
    public static final String EVENT_LONGITUDE_KEY = "longitude";
    public static final String EVENT_LATITUDE_KEY = "latitude";
    public static final String BODYTEMP_KEY = "bodytemp";
    public static final String EVENT_HEARTH_RATE_KEY = "hearthRate";
    public static final String EVENT_TYPE_KEY = "typeid";
    private static final String WORD_KEY = "word";
    private static final String WORD_ID_KEY = "WORDID";
    private static final String SQL_EMERGENCYSTATUS_FROM_EVENT_ID = "select STATUSNAME from EMERGENCYSTATUS join EMERGENCIES E on EMERGENCYSTATUS.STATUSID = E.STATUSID where EVENTID = ?" ;
    private static final String STATUSNAME_KEY = "STATUSNAME" ;
    private static final String SQL_WORD_FROM_EVENTID = "select * from SPOKENWORD join WORD W on W.WORDID = SPOKENWORD.WORDID where EVENTID = ?";
    private static final String SQL_DELETE_CHILDCARECENTER = "delete from childcarecenter where childcareCenterID = ?;";
    private static final String SQL_CARETAKER_FROM_CHILDCARECENTER = "select * from caretaker where childcareCenterID = ? AND caretakerID = ?;";
    private static final int NO_CENTER_ASSIGNED = -1;

    private final Server dbWebConsole;
    private final String username;
    private final String password;
    private final String url;

    public MarsH2Repository(String url, String username, String password, int console) {
        try {
            this.username = username;
            this.password = password;
            this.url = url;
            this.dbWebConsole = Server.createWebServer(
                    "-ifNotExists",
                    "-webPort", String.valueOf(console)).start();
            LOGGER.log(Level.INFO, "Database web console started on port: {0}", console);
            this.generateData();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "DB configuration failed", ex);
            throw new RepositoryException("Could not configure MarsH2repository");
        }
    }

    public Parent getParent(int id) {
        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQL_PARENT_BY_ID)
        ) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Parent(rs.getInt(PARENT_ID_KEY), rs.getString(NAME_KEY), rs.getInt(PARENT_KEY), getChildrenFromParent(rs.getInt(PARENT_ID_KEY)));
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to get parent.", ex);
            throw new RepositoryException("Could not get parent.");
        }
    }

    public Child getChild(int id) {
        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQL_CHILD_BY_ID)
        ) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int childcareCenterId = rs.getInt(CHILDCARECENTER_ID_KEY);
                    if (rs.wasNull()) {
                        childcareCenterId = NO_CENTER_ASSIGNED;
                    }
                    return new Child(rs.getInt(CHILDID_KEY), rs.getString(NAME_KEY), childcareCenterId, LocalDateTime.parse(rs.getString(BIRTHDATE_KEY),formatter));
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to get child.", ex);
            throw new RepositoryException("Could not get child.");
        }
    }

    public EmergencyStatus getEmergencyTypeFromEventId(int eventId){
        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQL_EMERGENCYSTATUS_FROM_EVENT_ID)
        ) {
            stmt.setInt(1, eventId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return EmergencyStatus.fromString(rs.getString(STATUSNAME_KEY));
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to get child.", ex);
            throw new RepositoryException("Could not get child.");
        }
    }

    public ChildcareCenter getChildcareCenter(int centerId) {
        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQL_CHILDCARECENTER_BY_ID)
        ) {
            stmt.setInt(1, centerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new ChildcareCenter(rs.getInt(CHILDCARECENTER_ID_KEY), rs.getString(CHILDCARECENTERNAME_KEY), rs.getInt(CHILDCARECENTER_LIMIT_KEY));
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to get childcare center.", ex);
            throw new RepositoryException("Could not get childcare center.");

        }
    }
    public List<Child> getChildrenFromParent(int parentId){
        List<Child> childrenFromParent = new ArrayList<>();
        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQL_CHILDREN_FROM_PARENT)
        ) { stmt.setInt(1,parentId);
            try (ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    int childcareCenterId = rs.getInt(CHILDCARECENTER_ID_KEY);
                    if (rs.wasNull()) {
                        childcareCenterId = NO_CENTER_ASSIGNED;
                    }
                    childrenFromParent.add(new Child(rs.getInt(CHILDID_KEY), rs.getString(NAME_KEY), childcareCenterId, LocalDateTime.parse(rs.getString(BIRTHDATE_KEY),formatter)));
                }
            }
            return childrenFromParent;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to get children from parentID", ex);
            throw new RepositoryException("Failed to get children from parentID");
        }
    }

    public List<Event> getEventsFromPeriod(LocalDateTime start, LocalDateTime stop, int childId){
        List<Event> events = new LinkedList<>();
        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQL_EVENTS_WITH_RANGE)
        ) { stmt.setInt(1,childId);
            stmt.setString(2,start.toString());
            stmt.setString(3,stop.toString());
            try (ResultSet rs = stmt.executeQuery()){
                while(rs.next()){

                    events.add(getEventFromResultSet(rs));
                }
            }
            return events;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to get events from childID", ex);
            throw new RepositoryException("Failed to get events from childID");
        }
    }

    public String getEventTypeFromId(int id){
        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQL_EVENTTYPE_FROM_ID)
        ) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(NAME_KEY);
                } else {
                    throw new RepositoryException("Event-typeID is not found");
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to get eventType.", ex);
            throw new RepositoryException("Could not get eventType.");
        }
    }

    public List<Parent> getParentsFromChild(int id){
        List<Parent> parentFromChildren = new ArrayList<>();
        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQL_PARENT_FROM_CHILD)
        ) { stmt.setInt(1,id);
            try (ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    parentFromChildren.add(new Parent(rs.getInt(PARENT_ID_KEY), rs.getString(NAME_KEY), rs.getInt("Year"),getChildrenFromParent(rs.getInt(PARENT_ID_KEY))));
                }
            }
            return parentFromChildren;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to get Parents from childId", ex);
            throw new RepositoryException("Failed to get Parents from childId");
        }
    }

    public Words getWordFromEventId(int id){
        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQL_WORD_FROM_EVENTID)
        ) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Words(rs.getInt(WORD_ID_KEY),rs.getString(WORD_KEY));
                } else {
                    throw new RepositoryException("WORD_ID is not found");
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to get eventType.", ex);
            throw new RepositoryException("Could not get eventType.");
        }
    }

    public void cleanUp() {
        if (dbWebConsole != null && dbWebConsole.isRunning(false))
            dbWebConsole.stop();

        try {
            Files.deleteIfExists(Path.of("./db-16.mv.db"));
            Files.deleteIfExists(Path.of("./db-16.trace.db"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Database cleanup failed.", e);
            throw new RepositoryException("Database cleanup failed.");
        }
    }

    public void generateData() {
        try {
            executeScript("db-create.sql");
            executeScript("db-populate.sql");
        } catch (IOException | SQLException ex) {
            LOGGER.log(Level.SEVERE, "Execution of database scripts failed.", ex);
        }
    }

    private void executeScript(String fileName) throws IOException, SQLException {
        String createDbSql = readFile(fileName);
        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(createDbSql)
        ) {
            stmt.executeUpdate();
        }
    }

    private String readFile(String fileName) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null)
            throw new RepositoryException("Could not read file: " + fileName);

        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public List<Child> getAllChildren() {
        List<Child> children = new ArrayList<>();
        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQL_ALLCHILDREN);
                ResultSet rs = stmt.executeQuery()
        ) {
            while(rs.next()){
                int childcareCenterId = rs.getInt(CHILDCARECENTER_ID_KEY);
                if (rs.wasNull()) {
                    childcareCenterId = NO_CENTER_ASSIGNED;
                }
                children.add(new Child(rs.getInt(CHILDID_KEY), rs.getString(NAME_KEY), childcareCenterId, LocalDateTime.parse(rs.getString(BIRTHDATE_KEY),formatter)));
            }

            return children;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to get all children.", ex);
            throw new RepositoryException("Could not get all children.");
        }
    }

    public List<Child> getAllChildrenFromChildcareCenter(int centerId) {
        List<Child> children = new ArrayList<>();
        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQL_CHILDREN_FROM_CHILDCARECENTER)
        ) {
            stmt.setInt(1, centerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while(rs.next()){
                    int childcareCenterId = rs.getInt(CHILDCARECENTER_ID_KEY);
                    if (rs.wasNull()) {
                        childcareCenterId = NO_CENTER_ASSIGNED;
                    }
                    children.add(new Child(rs.getInt(CHILDID_KEY), rs.getString(NAME_KEY), childcareCenterId, LocalDateTime.parse(rs.getString(BIRTHDATE_KEY),formatter)));
                }
            }
            return children;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to get all children from center.", ex);
            throw new RepositoryException("Could not get all children from center.");
        }
    }

    public List<Caretaker> getAllCaretakersFromChildcareCenter(int centerId) {
        List<Caretaker> caretakers = new ArrayList<>();
        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQL_CARETAKERS_FROM_CHILDCARECENTER)
        ) {
            stmt.setInt(1, centerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while(rs.next()){
                    caretakers.add(new Caretaker(rs.getInt("caretakerID"), rs.getString("name"), getChildcareCenter(rs.getInt(CHILDCARECENTER_ID_KEY)), rs.getDouble("salary")));
                }
            }
            return caretakers;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to get all caretakers from center.", ex);
            throw new RepositoryException("Could not get all caretakers from center.");
        }
    }

    public List<ChildcareCenter> getAllChildcareCenters() {
        List<ChildcareCenter> childCareCenters = new ArrayList<>();
        try (
                Connection con = getConnection();
                PreparedStatement stmt = con.prepareStatement(SQL_ALLCHILDCARECENTERS);
                ResultSet rs = stmt.executeQuery()
                ) {
            while(rs.next()){
                childCareCenters.add(new ChildcareCenter(rs.getInt(CHILDCARECENTER_ID_KEY), rs.getString("childcareCenterName"), rs.getInt("childLimit")));
            }
            return childCareCenters;

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to get all childcare centers.", ex);
            throw new RepositoryException("Could not get all childcare centers.");
        }
    }

    public Caretaker getCaretakerFromChildcareCenter(int centerId, int caretakerId) {
        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQL_CARETAKER_FROM_CHILDCARECENTER)
        ) {
            stmt.setInt(1, centerId);
            stmt.setInt(2, caretakerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Caretaker(rs.getInt("caretakerID"), rs.getString("name"), getChildcareCenter(rs.getInt(CHILDCARECENTER_ID_KEY)), rs.getDouble("salary"));
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to get caretaker from center.", ex);
            throw new RepositoryException("Could not get caretaker from center.");
        }
    }


    private Event getEventFromResultSet(ResultSet rs) throws SQLException {
        Event event;
        int eventID = rs.getInt(EVENT_ID_KEY);
        Location location = new Location(rs.getFloat(EVENT_LONGITUDE_KEY), rs.getFloat(EVENT_LATITUDE_KEY));
        Child child = getChild(rs.getInt(CHILDID_KEY));
        switch (EventType.fromString(getEventTypeFromId(rs.getInt(EVENT_TYPE_KEY)))) {
            case EATING:
                event = new Eating(
                        eventID,
                        location,
                        child,
                        1,
                        LocalDateTime.parse(rs.getString(DATETIME_KEY), formatter)
                );
                event.setBodyTemp(rs.getFloat(BODYTEMP_KEY));
                event.setDescription(rs.getString(EVENT_DESCRIPTION_KEY));
                event.setHearthRate(rs.getInt(EVENT_HEARTH_RATE_KEY));
                return event;
            case SLEEPING:
                event = new Sleeping(
                        eventID,
                        location,
                        child,
                        1,
                        LocalDateTime.parse(rs.getString(DATETIME_KEY), formatter)
                );
                event.setBodyTemp(rs.getFloat(BODYTEMP_KEY));
                event.setDescription(rs.getString(EVENT_DESCRIPTION_KEY));
                event.setHearthRate(rs.getInt(EVENT_HEARTH_RATE_KEY));
                return event;
            case EMERGENCY:
                event = new Emergency(
                        eventID,
                        location,
                        child,
                        LocalDateTime.parse(rs.getString(DATETIME_KEY), formatter),
                        getEmergencyTypeFromEventId(eventID)
                ) {
                };
                event.setBodyTemp(rs.getFloat(BODYTEMP_KEY));
                event.setDescription(rs.getString(EVENT_DESCRIPTION_KEY));
                event.setHearthRate(rs.getInt(EVENT_HEARTH_RATE_KEY));
                return event;
            case SPOKEN_WORD:
                event = new FirstWord(
                        eventID,
                        location,
                        child,
                        LocalDateTime.parse(rs.getString(DATETIME_KEY), formatter),
                        getWordFromEventId(rs.getInt(EVENT_ID_KEY))
                );
                event.setBodyTemp(rs.getFloat(BODYTEMP_KEY));
                event.setDescription(rs.getString(EVENT_DESCRIPTION_KEY));
                event.setHearthRate(rs.getInt(EVENT_HEARTH_RATE_KEY));
                return event;
            default:
                event = new Generic(
                        eventID,
                        location,
                        child,
                        LocalDateTime.parse(rs.getString(DATETIME_KEY), formatter)
                );
                event.setBodyTemp(rs.getFloat(BODYTEMP_KEY));
                event.setDescription(rs.getString(EVENT_DESCRIPTION_KEY));
                event.setHearthRate(rs.getInt(EVENT_HEARTH_RATE_KEY));
                return event;
        }
    }

    public List<String> getEventTypes() {
        List<String> eventTypes = new ArrayList<>();
        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQL_ALL_EVENTTYPES);
                ResultSet rs = stmt.executeQuery()
        ) {
            while(rs.next()){
                eventTypes.add(rs.getString("name"));
            }
            return eventTypes;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to get all eventTypes.", ex);
            throw new RepositoryException("Could not retrieve all eventTypes.");
        }
    }

    public void deleteChildcareCenter(int childcareCenterId) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE_CHILDCARECENTER)) {

             stmt.setInt(1, childcareCenterId);
             stmt.execute();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to delete childcare center.", ex);
            throw new RepositoryException("Could not delete childcare center.");
        }
    }

    public void setCenterId(int childId, int childCareCenterId) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_CENTER_ID)) {
            if (childCareCenterId == NO_CENTER_ASSIGNED) {
                stmt.setNull(1, Types.INTEGER);
            } else {
                stmt.setInt(1, childCareCenterId);
            }
            stmt.setInt(2, childId);
            stmt.execute();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to delete childcare center.", ex);
            throw new RepositoryException("Could not delete childcare center.");
        }
    }
}
