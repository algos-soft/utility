package it.algos.schedule;

import it.algos.utility.schedule.CronService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CronServiceTest {

    private CronService descriptor;

    @BeforeEach
    void setUp() {
        descriptor = new CronService();
    }

    @Test
    void shouldNotAcceptNull() throws CronService.CronValidationException {
        String cron = null;
        System.out.println(String.format("[%s] -> Exception attesa", cron));

        CronService.CronValidationException thrown = assertThrows(
                CronService.CronValidationException.class,
                () -> descriptor.descriviCron(cron)
        );
        assertEquals("L'espressione cron non può essere vuota", thrown.getMessage());
    }

    @Test
    void shouldNotAcceptEmpty() throws CronService.CronValidationException {
        String cron = "";
        System.out.println(String.format("[%s] -> Exception attesa", cron));

        CronService.CronValidationException thrown = assertThrows(
                CronService.CronValidationException.class,
                () -> descriptor.descriviCron(cron)
        );
        assertEquals("L'espressione cron non può essere vuota", thrown.getMessage());
    }

    @Nested
    class CommonUseCaseTests {

        @Test
        void shouldHandleEveryMinute() throws CronService.CronValidationException {
            String cron = "0 * * * * *";
            String expected = "Esegue ogni minuto";
            System.out.println(String.format("[%s] -> %s", cron, expected));

            String result = descriptor.descriviCron(cron);
            assertEquals(expected, result);
        }

        @Test
        void shouldHandleDailyExecution() throws CronService.CronValidationException {
            String cron = "0 0 0 * * *";
            String expected = "Esegue tutti i giorni a mezzanotte";
            System.out.println(String.format("[%s] -> %s", cron, expected));

            String result = descriptor.descriviCron(cron);
            assertEquals(expected, result);
        }

        @Test
        void shouldHandleWeeklyExecution() throws CronService.CronValidationException {
            String cron = "0 0 0 * * 1";
            String expected = "Esegue all'ora 0 di lunedì";
            System.out.println(String.format("[%s] -> %s", cron, expected));

            String result = descriptor.descriviCron(cron);
            assertEquals(expected, result);
        }

        @Test
        void shouldHandleMonthlyExecution() throws CronService.CronValidationException {
            String cron = "0 0 0 1 * *";
            String expected = "Esegue all'ora 0 il giorno 1";
            System.out.println(String.format("[%s] -> %s", cron, expected));

            String result = descriptor.descriviCron(cron);
            assertEquals(expected, result);
        }
    }

    @Nested
    class SpecificTimeTests {

        @Test
        void shouldHandleSpecificHourAndMinute() throws CronService.CronValidationException {
            String cron = "0 30 14 * * *";
            String expected = "Esegue tutti i giorni alle 14:30";
            System.out.println(String.format("[%s] -> %s", cron, expected));

            String result = descriptor.descriviCron(cron);
            assertEquals(expected, result);
        }

        @Test
        void shouldHandleSpecificHourOnly() throws CronService.CronValidationException {
            String cron = "0 0 12 * * *";
            String expected = "Esegue tutti i giorni alle 12";
            System.out.println(String.format("[%s] -> %s", cron, expected));

            String result = descriptor.descriviCron(cron);
            assertEquals(expected, result);
        }
    }

    @Nested
    class InvalidInputTests {

        @Test
        void shouldRejectInvalidDayOfWeek() throws CronService.CronValidationException {
            String cron = "0 0 0 * * 8";
            System.out.println(String.format("[%s] -> Exception attesa", cron));

            CronService.CronValidationException thrown = assertThrows(
                    CronService.CronValidationException.class,
                    () -> descriptor.descriviCron(cron)
            );
            assertEquals("Numero giorno non valido per DAY_OF_WEEK: 8", thrown.getMessage());
        }

        @Test
        void shouldRejectInvalidMonth() throws CronService.CronValidationException {
            String cron = "0 0 0 * 13 *";
            System.out.println(String.format("[%s] -> Exception attesa", cron));

            CronService.CronValidationException thrown = assertThrows(
                    CronService.CronValidationException.class,
                    () -> descriptor.descriviCron(cron)
            );
            assertEquals("Mese non valido: 13", thrown.getMessage());
        }
    }
}