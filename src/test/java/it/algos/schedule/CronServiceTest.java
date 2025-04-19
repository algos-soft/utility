package it.algos.schedule;

import it.algos.utility.schedule.CronService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.stream.Stream;

import static it.algos.vbase.boot.BaseCost.NULLO;
import static it.algos.vbase.boot.BaseCost.VUOTA;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CronServiceTest {

    @Autowired
    private CronService cronService;


    private static final Map<String, String> cronPatterns = Map.ofEntries(
            Map.entry("ogni minuto", "0 * * * * ?"),
            Map.entry("ogni 15 minuti", "0 */15 * * * ?"),
            Map.entry("ogni 30 minuti nelle ore lavorative", "0 */30 9-17 * * ?"),
            Map.entry("mezzanotte", "0 0 0 * * ?"),
            Map.entry("mezzogiorno", "0 0 12 * * ?"),
            Map.entry("alle 3:30", "0 30 3 * * ?"),
            Map.entry("ogni ora", "0 0 * * * ?"),
            Map.entry("ogni domenica alle 9", "0 0 9 ? * SUN"),
            Map.entry("lunedì e mercoledì alle 14:30", "0 30 14 ? * MON,WED"),
            Map.entry("ogni fine settimana a mezzanotte", "0 0 0 ? * SAT,SUN"),
            Map.entry("primo del mese alle 6:00", "0 0 6 1 * ?"),
            Map.entry("ultimo giorno del mese 23:00", "0 0 23 L * ?"),
            Map.entry("ogni 6 ore", "0 0 */6 * * ?"),
            Map.entry("ogni 2 ore dalle 8 alle 16", "0 0 8-16/2 * * ?"),
            Map.entry("ogni 10 minuti nel primo trimestre", "0 */10 * * 1-3 ?"),
            Map.entry("ogni venerdì 13 alle 13:13", "0 13 13 13 * FRI"),
            Map.entry("ogni 15 minuti nei giorni feriali", "0 */15 * ? * MON-FRI"),
            Map.entry("ogni ora nei weekend", "0 0 * ? * SAT,SUN"),
            Map.entry("ultimi 5 giorni del mese alle 22", "0 0 22 L-5 * ?"),
            Map.entry("primo lunedì del mese alle 9", "0 0 9 ? * 2#1")
    );

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
}