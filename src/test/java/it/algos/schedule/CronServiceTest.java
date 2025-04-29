package it.algos.schedule;

import it.algos.utility.schedule.CronService;
import it.algos.utility.schedule.CronUtils;
import it.algos.utility.schedule.CronValidationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.support.CronExpression;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import static it.algos.vbase.boot.BaseCost.NULLO;
import static it.algos.vbase.boot.BaseCost.VUOTA;
import static java.lang.System.out;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestMethodOrder(OrderAnnotation.class)
class CronServiceTest {


    private static final Map<String, String> CRON_PATTERNS = new LinkedHashMap<>() {{
        put("ogni minuto", "0 * * * * ?");
        put("ogni 15 minuti", "0 */15 * * * ?");
        put("ogni 30 minuti nelle ore lavorative", "0 */30 9-17 * * ?");
        put("mezzanotte", "0 0 0 * * ?");
        put("mezzogiorno", "0 0 12 * * ?");
        put("alle 3:30", "0 30 3 * * ?");
        put("ogni ora", "0 0 * * * ?");
        put("ogni domenica alle 9", "0 0 9 ? * SUN");
        put("lunedì e mercoledì alle 14:30", "0 30 14 ? * MON,WED");
        put("ogni fine settimana a mezzanotte", "0 0 0 ? * SAT,SUN");
        put("primo del mese alle 6:00", "0 0 6 1 * ?");
        put("ultimo giorno del mese 23:00", "0 0 23 L * ?");
        put("ogni 6 ore", "0 0 */6 * * ?");
        put("ogni 2 ore dalle 8 alle 16", "0 0 8-16/2 * * ?");
        put("ogni 10 minuti nel primo trimestre", "0 */10 * * 1-3 ?");
        put("ogni venerdì 13 alle 13:13", "0 13 13 13 * FRI");
        put("ogni 15 minuti nei giorni feriali", "0 */15 * ? * MON-FRI");
        put("ogni ora nei weekend", "0 0 * ? * SAT,SUN");
        put("ultimi 5 giorni del mese alle 22", "0 0 22 L-5 * ?");
        put("primo lunedì del mese alle 9", "0 0 9 ? * 2#1");
    }};

    private static final Map<String, String> CRON_PATTERNS_ERRORE = new LinkedHashMap<>() {{
        put("spazi vuoti", "0 *   *   * ?");
        put("senza mese o settimana", "0 * * 30 * ?");
        put("doppi spazi vuoti", "0 *   *   * ?");
        put("colonna delle ore vuota", "0 *   *   * ?");
        put("mese invalido", "0 * 25 * * ?");
        put("spazio vuoto nella colonna dei giorni", "0 * *   * ?");
    }};


    @Autowired
    private CronService cronService;


    static Stream<Arguments> cronPatternsErrore() {
        return CRON_PATTERNS_ERRORE.entrySet()
                .stream()
                .map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }


    @Nested
    @Order(1)
    @DisplayName("1 - CronServiceClazz")
    @TestMethodOrder(OrderAnnotation.class)
    class CronServiceClazz {

        @Test
        @Order(1)
        @DisplayName("1 - testEspressioneVuota")
        void testEspressioneVuota() {
            out.println("1 - testEspressioneVuota");
            out.println(VUOTA);

            String cron = VUOTA;
            out.println(String.format("[%s] -> %s", cron, NULLO));
            assertEquals("", cronService.info(cron));
        }

        @Test
        @Order(2)
        @DisplayName("2 - testOgniMinuto")
        void testOgniMinuto() {
            out.println("2 - testOgniMinuto");
            out.println(VUOTA);

            String cron = "0 * * * * *";
            String expected = "Esegue ogni minuto";
            out.println(String.format("[%s] -> %s", cron, expected));
            assertEquals(expected, cronService.info(cron));
        }

        @Test
        @Order(3)
        @DisplayName("3 - testOgniGiornoMezzanotte")
        void testOgniGiornoMezzanotte() {
            out.println("3 - testOgniGiornoMezzanotte");
            out.println(VUOTA);

            String cron = "0 0 0 * * *";
            String expected = "Esegue tutti i giorni a mezzanotte";
            out.println(String.format("[%s] -> %s", cron, expected));
            assertEquals(expected, cronService.info(cron));
        }

        @Test
        @Order(4)
        @DisplayName("4 - testOgniGiornoOraSpecifica")
        void testOgniGiornoOraSpecifica() {
            out.println("4 - testOgniGiornoOraSpecifica");
            out.println(VUOTA);

            String cron = "0 30 14 * * *";
            String expected = "Esegue tutti i giorni alle 14:30";
            out.println(String.format("[%s] -> %s", cron, expected));
            assertEquals(expected, cronService.info(cron));
        }

        @Test
        @Order(5)
        @DisplayName("5 - testGiorniSpecifici")
        void testGiorniSpecifici() {
            out.println("5 - testGiorniSpecifici");
            out.println(VUOTA);
            String cron = "0 0 12 * * MON,WED,FRI";
            String expected = "Esegue all'ora 12 di lunedì, mercoledì e venerdì";
            out.println(String.format("[%s] -> %s", cron, expected));
            assertEquals(expected, cronService.info(cron));
        }
    }

    @Nested
    @Order(2)
    @DisplayName("2 - CommonUseCaseTests")
    @TestMethodOrder(OrderAnnotation.class)
    class CommonUseCaseTests {

        @Test
        @Order(1)
        @DisplayName("1 - shouldHandleEveryMinute")
        void shouldHandleEveryMinute() throws CronValidationException {
            out.println("1 - shouldHandleEveryMinute");
            out.println(VUOTA);

            String cron = "0 * * * * *";
            String expected = "Esegue ogni minuto";
            out.println(String.format("[%s] -> %s", cron, expected));

            String result = CronUtils.descriviCron(cron);
            assertEquals(expected, result);
        }

        @Test
        @Order(2)
        @DisplayName("2 - shouldHandleDailyExecution")
        void shouldHandleDailyExecution() throws CronValidationException {
            out.println("2 - shouldHandleDailyExecution");
            out.println(VUOTA);

            String cron = "0 0 0 * * *";
            String expected = "Esegue tutti i giorni a mezzanotte";
            out.println(String.format("[%s] -> %s", cron, expected));

            String result = CronUtils.descriviCron(cron);
            assertEquals(expected, result);
        }

        @Test
        @Order(3)
        @DisplayName("3 - shouldHandleWeeklyExecution")
        void shouldHandleWeeklyExecution() throws CronValidationException {
            out.println("3 - shouldHandleWeeklyExecution");
            out.println(VUOTA);

            String cron = "0 0 0 * * 1";
            String expected = "Esegue all'ora 0 di lunedì";
            out.println(String.format("[%s] -> %s", cron, expected));

            String result = CronUtils.descriviCron(cron);
            assertEquals(expected, result);
        }

        @Test
        @Order(4)
        @DisplayName("4 - shouldHandleMonthlyExecution")
        void shouldHandleMonthlyExecution() throws CronValidationException {
            out.println("4 - shouldHandleMonthlyExecution");
            out.println(VUOTA);

            String cron = "0 0 0 1 * *";
            String expected = "Esegue all'ora 0 il giorno 1";
            out.println(String.format("[%s] -> %s", cron, expected));

            String result = CronUtils.descriviCron(cron);
            assertEquals(expected, result);
        }
    }

    @Nested
    @Order(3)
    @DisplayName("3 - SpecificTimeTests")
    @TestMethodOrder(OrderAnnotation.class)
    class SpecificTimeTests {

        @Test
        @Order(1)
        @DisplayName("1 - shouldHandleSpecificHourAndMinute")
        void shouldHandleSpecificHourAndMinute() throws CronValidationException {
            out.println("1 - shouldHandleSpecificHourAndMinute");
            out.println(VUOTA);

            String cron = "0 30 14 * * *";
            String expected = "Esegue tutti i giorni alle 14:30";
            out.println(String.format("[%s] -> %s", cron, expected));

            String result = CronUtils.descriviCron(cron);
            assertEquals(expected, result);
        }

        @Test
        @Order(2)
        @DisplayName("2 - shouldHandleSpecificHourOnly")
        void shouldHandleSpecificHourOnly() throws CronValidationException {
            out.println("2 - shouldHandleSpecificHourOnly");
            out.println(VUOTA);

            String cron = "0 0 12 * * *";
            String expected = "Esegue tutti i giorni alle 12";
            out.println(String.format("[%s] -> %s", cron, expected));

            String result = CronUtils.descriviCron(cron);
            assertEquals(expected, result);
        }
    }

    @Nested
    @Order(4)
    @DisplayName("4 - InvalidInputTests")
    @TestMethodOrder(OrderAnnotation.class)
    class InvalidInputTests {

        @Test
        @Order(1)
        @DisplayName("1 - shouldRejectInvalidDayOfWeek")
        void shouldRejectInvalidDayOfWeek() throws CronValidationException {
            out.println("1 - shouldRejectInvalidDayOfWeek");
            out.println(VUOTA);

            String cron = "0 0 0 * * 8";
            out.println(String.format("[%s] -> Exception attesa", cron));

            CronValidationException thrown = assertThrows(
                    CronValidationException.class,
                    () -> CronUtils.descriviCron(cron)
            );
            assertEquals("Numero giorno non valido per DAY_OF_WEEK: 8", thrown.getMessage());
        }

        @Test
        @Order(2)
        @DisplayName("2 - shouldRejectInvalidMonth")
        void shouldRejectInvalidMonth() throws CronValidationException {
            out.println("2 - shouldRejectInvalidMonth");
            out.println(VUOTA);

            String cron = "0 0 0 * 13 *";
            out.println(String.format("[%s] -> Exception attesa", cron));

            CronValidationException thrown = assertThrows(
                    CronValidationException.class,
                    () -> CronUtils.descriviCron(cron)
            );
            assertEquals("Mese non valido: 13", thrown.getMessage());
        }
    }

    @Nested
    @Order(5)
    @DisplayName("5 - NotAccept")
    @TestMethodOrder(OrderAnnotation.class)
    class NotAccept {

        @Test
        @Order(1)
        @DisplayName("1 - shouldNotAcceptNull")
        void shouldNotAcceptNull() throws CronValidationException {
            out.println("2 - shouldNotAcceptNull");
            out.println(VUOTA);

            String cron = null;
            out.println(String.format("[%s] -> Exception attesa", cron));

            CronValidationException thrown = assertThrows(
                    CronValidationException.class,
                    () -> CronUtils.descriviCron(cron)
            );
            assertEquals("L'espressione cron non può essere nulla", thrown.getMessage());
        }

        @Test
        @Order(2)
        @DisplayName("2 - shouldNotAcceptEmpty")
        void shouldNotAcceptEmpty() throws CronValidationException {
            out.println("2 - shouldNotAcceptEmpty");
            out.println(VUOTA);

            String cron = "";
            out.println(String.format("[%s] -> Exception attesa", cron));

            CronValidationException thrown = assertThrows(
                    CronValidationException.class,
                    () -> CronUtils.descriviCron(cron)
            );
            assertEquals("L'espressione cron non può essere vuota", thrown.getMessage());
        }
    }


    @Nested
    @Order(6)
    @DisplayName("6 - ValoriValidi")
    @TestMethodOrder(OrderAnnotation.class)
    class ValoriValidi {


        protected static Stream<Arguments> CRON_VALIDE() {
            return Stream.of(
                    Arguments.of("ogni minuto", "0 * * * * ?"),
                    Arguments.of("ogni 15 minuti", "0 */15 * * * ?"),
                    Arguments.of("ogni 30 minuti nelle ore lavorative", "0 */30 9-17 * * ?"),
                    Arguments.of("mezzanotte", "0 0 0 * * ?"),
                    Arguments.of("mezzogiorno", "0 0 12 * * ?"),
                    Arguments.of("alle 3:30", "0 30 3 * * ?"),
                    Arguments.of("ogni ora", "0 0 * * * ?"),
                    Arguments.of("lunedì e mercoledì alle 14:30", "0 30 14 ? * MON,WED"),
                    Arguments.of("ogni fine settimana a mezzanotte", "0 0 0 ? * SAT,SUN"),
                    Arguments.of("ogni 6 ore", "0 0 */6 * * ?")
            );
        }


        @Test
        @Order(1)
        @DisplayName("1 - espressioniValide")
        protected void espressioniValide() {
            out.println("1 - espressioniValide");
            out.println(VUOTA);
            Object[] mat;
            String descrizione;
            String expression;

            for (Arguments args : CRON_VALIDE().toList()) {
                mat = args.get();
                descrizione = (String) mat[0];
                expression = (String) mat[1];

                try {
                    CronExpression.parse(expression);
                } catch (Exception e) {
                    fail(String.format("Espressione non valida: %s (%s). Errore: %s", expression, descrizione, e.getMessage()));
                }

                String message = String.format("[%s] -> %s", expression, descrizione);
                out.println(message);
            }
        }

    }


    @Nested
    @Order(7)
    @DisplayName("7 - ValoriErrati")
    @TestMethodOrder(OrderAnnotation.class)
    class ValoriErrati {

        protected static Stream<Arguments> CRON_ERRATE() {
            return Stream.of(
                    Arguments.of("ogni minuto", "0 * * * * ?"),
                    Arguments.of("ogni ora", "0 0 * * * ?"),
                    Arguments.of("mezzanotte", "0 0 0 * * ?"),
                    Arguments.of("ora > 24", "0 0 45 * * ?"),
                    Arguments.of("5 fields", "0 *   * * ?"),
                    Arguments.of("doppio spazio", "0  10 1 4,20 * ?"),
                    Arguments.of("mezzanotte", "0 0 0 * * ?"),
                    Arguments.of("", "0 0 0 * * 8"),
                    Arguments.of("", "0 0 0 * * 8 2"),
                    Arguments.of("", "4 0  * *"),
                    Arguments.of("", "0 0 0 * 13 *")
            );
        }


        @Test
        @Order(1)
        @DisplayName("1 - espressioniErrate")
        protected void espressioniErrate() {
            out.println("1 - espressioniErrate");
            out.println(VUOTA);
            Object[] mat;
            String descrizione;
            String expression;
            CronExpression cronExpression;
            String errore;
            String message;

            for (Arguments args : CRON_ERRATE().toList()) {
                mat = args.get();
                descrizione = (String) mat[0];
                expression = (String) mat[1];

                try {
                    cronExpression = CronExpression.parse(expression);
                } catch (Exception e) {
                    errore = "[\u001B[31m" + expression + "\u001B[0m]";
                    message = String.format("%s Errore: %s", errore, e.getMessage());
                    out.println(message);
                    continue;
                }

                message = String.format("[%s] -> %s", expression, descrizione);
                out.println(message);
            }
        }
     }

}