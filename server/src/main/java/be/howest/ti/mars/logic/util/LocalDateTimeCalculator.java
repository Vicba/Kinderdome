package be.howest.ti.mars.logic.util;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public final class LocalDateTimeCalculator {
    private static final int LAST_HOUR = 23;
    private static final int LAST_MINUTE = 23;
    private static final int LAST_SECOND = 23;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final String MOCKTIME_RANGE = "2052-05-06 00:00:00";


    LocalDateTimeCalculator() {
        throw new IllegalStateException("Utility class");
    }

    public static List<LocalDateTime> getCurrentDayLocalTimeDateRange(){
        List<LocalDateTime> range = new ArrayList<>();
        LocalDateTime localDateTime = getCurrentTime();
        range.add(localDateTime.toLocalDate().atStartOfDay());
        range.add(localDateTime.toLocalDate().atTime(LAST_HOUR, LAST_MINUTE, LAST_SECOND));
        return range;
    }

    public static List<LocalDateTime> getMockDayRange(){
        List<LocalDateTime> range = new ArrayList<>();
        LocalDateTime localDateTime = LocalDateTime.parse(MOCKTIME_RANGE,formatter);
        range.add(localDateTime);
        range.add(localDateTime.toLocalDate().atTime(LAST_HOUR, LAST_MINUTE, LAST_SECOND));
        return range;
    }

    public static LocalDateTime getCurrentTime(){
        return LocalDateTime.now(ZoneId.of("UTC+01:00"));
    }
}
