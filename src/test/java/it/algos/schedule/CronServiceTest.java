package it.algos.schedule;

import it.algos.utility.schedule.CronService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static it.algos.vbase.boot.BaseCost.NULLO;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CronServiceTest {

    @Autowired
    private CronService cronService;

    @Test
    @Order(1)
    void testEspressioneVuota() {
        String cron = "";
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