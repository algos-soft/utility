package it.algos.schedule;

import it.algos.utility.schedule.CronService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CronServiceTest {

    @Autowired
    private CronService cronService;


    private static final Map<String, String> cronPatterns = new LinkedHashMap<>() {{
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

    static Stream<Arguments> cronPatterns() {
        return cronPatterns.entrySet().stream()
                .map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }

    @Test
    @Order(1)
    void testEspressioneVuota() {
        String cron = VUOTA;
        System.out.println(String.format("[%s] -> %s", cron, NULLO));
        assertEquals("", cronService.info(cron));
    }


    @Test
    @Order(2)
    void testOgniMinuto() {
        String cron = "0 * * * * *";
        String expected = "Esegue ogni minuto";
        System.out.println(String.format("[%s] -> %s", cron, expected));
        assertEquals(expected, cronService.info(cron));
    }

    @Test
    @Order(3)
    void testOgniGiornoMezzanotte() {
        String cron = "0 0 0 * * *";
        String expected = "Esegue tutti i giorni a mezzanotte";
        System.out.println(String.format("[%s] -> %s", cron, expected));
        assertEquals(expected, cronService.info(cron));
    }

    @Test
    @Order(3)
    void testOgniGiornoOraSpecifica() {
        String cron = "0 30 14 * * *";
        String expected = "Esegue tutti i giorni alle 14:30";
        System.out.println(String.format("[%s] -> %s", cron, expected));
        assertEquals(expected, cronService.info(cron));
    }

    @Test
    @Order(4)
    void testGiorniSpecifici() {
        String cron = "0 0 12 * * MON,WED,FRI";
        String expected = "Esegue all'ora 12 di lunedì, mercoledì e venerdì";
        System.out.println(String.format("[%s] -> %s", cron, expected));
        assertEquals(expected, cronService.info(cron));
    }

    @ParameterizedTest(name = "{0}")
    @Order(5)
    @MethodSource("cronPatterns")
    void testCronExpression(String description, String cronExpression) {
        try {
            CronExpression cron = CronExpression.parse(cronExpression);

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime next = cron.next(now);
            assertNotNull(next, "L'espressione dovrebbe produrre una data valida");

            LocalDateTime second = cron.next(next);
            assertNotNull(second, "L'espressione dovrebbe produrre multiple date valide");
            assertTrue(second.isAfter(next), "Le date successive devono essere in ordine crescente");

            log.info("✓ Valida: {} - {} (Prossima esecuzione: {})",
                    description, cronExpression,
                    next.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        } catch (Exception e) {
            fail(String.format("Espressione non valida: %s (%s). Errore: %s",
                    cronExpression, description, e.getMessage()));
        }
    }

}