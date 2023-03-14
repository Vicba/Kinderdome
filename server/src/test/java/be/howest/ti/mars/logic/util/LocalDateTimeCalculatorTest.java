package be.howest.ti.mars.logic.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LocalDateTimeCalculatorTest {

    @Test
    void getCurrentDayLocalTimeDateRange() {
        List<LocalDateTime> localDateTimeCalculator = LocalDateTimeCalculator.getCurrentDayLocalTimeDateRange();

        Assertions.assertNotNull(localDateTimeCalculator);
    }

    @Test
    void asserNoConstructor() {
        assertThrows(IllegalStateException.class, LocalDateTimeCalculator::new);
    }

    @Test
    void getCurrentTime() {
        LocalDateTime time = LocalDateTimeCalculator.getCurrentTime();

        Assertions.assertNotNull(time);
    }

}
