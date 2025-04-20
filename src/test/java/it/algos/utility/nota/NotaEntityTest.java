package it.algos.utility.nota;

import it.algos.vbase.enumeration.LogLevel;
import it.algos.vbase.enumeration.TypeLog;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotaEntityTest {

    @Test
    void testEntityCreation() {
        // Test entity creation using builder
        LocalDate today = LocalDate.now();
        NotaEntity nota = NotaEntity.builder()
                .typeLog(TypeLog.debug)
                .typeLevel(LogLevel.info)
                .inizio(today)
                .descrizione("Test note")
                .fatto(false)
                .build();
        
        // Verify properties
        assertEquals(TypeLog.debug, nota.getTypeLog());
        assertEquals(LogLevel.info, nota.getTypeLevel());
        assertEquals(today, nota.getInizio());
        assertEquals("Test note", nota.getDescrizione());
        assertFalse(nota.isFatto());
        assertNull(nota.getFine());
        
        // Test toString method
        String expectedToString = DateTimeFormatter.ofPattern("d-MMM-yy").format(today);
        assertEquals(expectedToString, nota.toString());
        
        System.out.println("[DEBUG_LOG] NotaEntity created: " + nota);
        System.out.println("[DEBUG_LOG] NotaEntity toString: " + nota.toString());
    }
    
    @Test
    void testCompletedNote() {
        // Test completed note
        LocalDate startDate = LocalDate.now().minusDays(7);
        LocalDate endDate = LocalDate.now();
        
        NotaEntity nota = NotaEntity.builder()
                .typeLog(TypeLog.system)
                .typeLevel(LogLevel.warn)
                .inizio(startDate)
                .descrizione("Completed note")
                .fatto(true)
                .fine(endDate)
                .build();
        
        // Verify properties
        assertTrue(nota.isFatto());
        assertEquals(endDate, nota.getFine());
        
        System.out.println("[DEBUG_LOG] Completed NotaEntity: " + nota);
    }
}