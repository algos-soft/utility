package it.algos.utility.schedule;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.HashMap;
import java.util.Map;

import static it.algos.vbase.boot.BaseCost.VUOTA;

/**
 * Project utility
 * Created by Algos
 * User: gac
 * Date: sab, 19-apr-2025
 * Time: 07:47
 */
public final class CronUtils {

    // Aggiungi questa mappa come campo statico nella classe CronDescriptor
    private static final Map<String, String> MESI = Map.ofEntries(
            Map.entry("1", "gennaio"),
            Map.entry("2", "febbraio"),
            Map.entry("3", "marzo"),
            Map.entry("4", "aprile"),
            Map.entry("5", "maggio"),
            Map.entry("6", "giugno"),
            Map.entry("7", "luglio"),
            Map.entry("8", "agosto"),
            Map.entry("9", "settembre"),
            Map.entry("10", "ottobre"),
            Map.entry("11", "novembre"),
            Map.entry("12", "dicembre")
    );


    // Mappa per la conversione da stringhe a numeri
    private static final Map<String, Integer> DAY_MAPPING = new HashMap<>() {{
        put("SUN", 0);
        put("MON", 1);
        put("TUE", 2);
        put("WED", 3);
        put("THU", 4);
        put("FRI", 5);
        put("SAT", 6);
    }};

    private static final Map<String, String> GIORNI_SETTIMANA = new HashMap<>() {{
        put("MON", "lunedì");
        put("TUE", "martedì");
        put("WED", "mercoledì");
        put("THU", "giovedì");
        put("FRI", "venerdì");
        put("SAT", "sabato");
        put("SUN", "domenica");
    }};
    private static final Map<String, String> GIORNI_SETTIMANA_NUMERI = Map.of(
            "1", "lunedì",
            "2", "martedì",
            "3", "mercoledì",
            "4", "giovedì",
            "5", "venerdì",
            "6", "sabato",
            "7", "domenica"
    );



    public static String descriviCron(String expression) throws CronValidationException {
        if (expression == null || expression.trim().isEmpty()) {
            throw new CronValidationException("L'espressione cron non può essere vuota");
        }

        String[] parts = expression.trim().split("\\s+");
        if (parts.length != 6) {
            throw new CronValidationException("L'espressione cron deve avere 6 parti, trovate: " + parts.length);
        }

        String seconds = parts[0];
        String minutes = parts[1];
        String hours = parts[2];
        String dayOfMonth = parts[3];
        String month = parts[4];
        String dayOfWeek = parts[5];

        validateDayOfWeek(dayOfWeek);
        validaMese(month);

        // Casi speciali comuni
        if (isEveryMinute(seconds, minutes, hours, dayOfMonth, month, dayOfWeek)) {
            return "Esegue ogni minuto";
        }

        if (isDailyExecution(seconds, minutes, hours, dayOfMonth, month, dayOfWeek)) {
            return formatDailyExecution(hours, minutes);
        }

        StringBuilder descrizione = new StringBuilder("Esegue");

        // Secondi
        if (!"0".equals(seconds)) {
            descrizione.append(" al secondo ").append(seconds);
        }

        // Minuti
        if (!"0".equals(minutes)) {
            descrizione.append(" al minuto ").append(minutes);
        }

        // Ore
        if (!"*".equals(hours)) {
            descrizione.append(" all'ora ").append(hours);
        }

        // Giorni della settimana
        if (!"*".equals(dayOfWeek) && !"?".equals(dayOfWeek)) {
            if (dayOfWeek.contains(",")) {
                String[] giorni = dayOfWeek.split(",");
                descrizione.append(" di ");
                for (int i = 0; i < giorni.length; i++) {
                    String giorno = GIORNI_SETTIMANA.get(giorni[i]);
                    if (i == giorni.length - 1) {
                        descrizione.append(" e ").append(giorno);
                    } else if (i == 0) {
                        descrizione.append(giorno);
                    } else {
                        descrizione.append(", ").append(giorno);
                    }
                }
            } else if (dayOfWeek.contains("-")) {
                String[] range = dayOfWeek.split("-");
                descrizione.append(" da ").append(GIORNI_SETTIMANA.get(range[0]))
                        .append(" a ").append(GIORNI_SETTIMANA.get(range[1]));
            } else {
                if (dayOfWeek != null) {
                    if (GIORNI_SETTIMANA.containsKey(dayOfWeek)) {
                        descrizione.append(" di ").append(GIORNI_SETTIMANA.get(dayOfWeek));
                    } else if (GIORNI_SETTIMANA_NUMERI.containsKey(dayOfWeek)) {
                        descrizione.append(" di ").append(GIORNI_SETTIMANA_NUMERI.get(dayOfWeek));
                    } else {
                        throw new CronValidationException("Giorno della settimana non valido: " + dayOfWeek);
                    }
                }
            }
        }

        // Mesi
        if (!"*".equals(month)) {
            descrizione.append(" nel mese ").append(month);
        }

        // Giorni del mese
        if (!"?".equals(dayOfMonth) && !"*".equals(dayOfMonth)) {
            descrizione.append(" il giorno ").append(dayOfMonth);
        }

        return descrizione.toString();
    }

    private static boolean isEveryMinute(String seconds, String minutes, String hours,
                                  String dayOfMonth, String month, String dayOfWeek) {
        return ("0".equals(seconds) || "*".equals(seconds)) &&
                "*".equals(minutes) &&
                "*".equals(hours) &&
                ("*".equals(dayOfMonth) || "?".equals(dayOfMonth)) &&
                "*".equals(month) &&
                ("*".equals(dayOfWeek) || "?".equals(dayOfWeek));
    }


    private static boolean isDailyExecution(String seconds, String minutes, String hours,
                                     String dayOfMonth, String month, String dayOfWeek) {
        // Verifica il pattern base per esecuzione giornaliera
        boolean isDaily = (
                "0".equals(seconds) &&                    // Secondi a 0
                        minutes.matches("\\d{1,2}") &&           // Minuti specifici
                        hours.matches("\\d{1,2}") &&             // Ore specifiche
                        ("*".equals(dayOfMonth) || "?".equals(dayOfMonth)) &&  // Ogni giorno
                        "*".equals(month) &&                     // Ogni mese
                        ("*".equals(dayOfWeek) || "?".equals(dayOfWeek))      // Ogni giorno della settimana
        );

        // Verifica che i valori numerici siano nel range corretto
        if (isDaily) {
            int minutesVal = Integer.parseInt(minutes);
            int hoursVal = Integer.parseInt(hours);
            return minutesVal >= 0 && minutesVal <= 59 && hoursVal >= 0 && hoursVal <= 23;
        }

        return false;
    }

    private static String formatDailyExecution(String hours, String minutes) {
        int hoursVal = Integer.parseInt(hours);
        int minutesVal = Integer.parseInt(minutes);

        if (hoursVal == 0 && minutesVal == 0) {
            return "Esegue tutti i giorni a mezzanotte";
        }

        StringBuilder description = new StringBuilder("Esegue tutti i giorni alle ");

        // Formatta ore
        description.append(String.format("%02d", hoursVal));

        // Aggiungi minuti solo se non sono 0
        if (minutesVal > 0) {
            description.append(String.format(":%02d", minutesVal));
        }

        return description.toString();
    }

    private static void validateDayOfWeek(String value) throws CronValidationException {
        if ("*".equals(value) || "?".equals(value)) {
            return;
        }

        String[] parts = value.contains(",") ? value.split(",") : new String[]{value};

        for (String part : parts) {
            String trimmedPart = part.trim().toUpperCase();

            // Gestisce i range (es: MON-FRI)
            if (trimmedPart.contains("-")) {
                String[] range = trimmedPart.split("-");
                if (range.length != 2) {
                    throw new CronValidationException("Formato range non valido per DAY_OF_WEEK: " + value);
                }
                validateDayPart(range[0].trim());
                validateDayPart(range[1].trim());
            } else {
                validateDayPart(trimmedPart);
            }
        }
    }

    private static void validateDayPart(String day) throws CronValidationException {
        // Verifica se è un numero valido
        try {
            int num = Integer.parseInt(day);
            if (num < 0 || num > 7) {
                throw new CronValidationException("Numero giorno non valido per DAY_OF_WEEK: " + day);
            }
            return;
        } catch (NumberFormatException ignored) {
            // Non è un numero, continua con la validazione del nome
        }

        // Verifica se è un nome di giorno valido
        if (!DAY_MAPPING.containsKey(day)) {
            throw new CronValidationException("Valore non valido per DAY_OF_WEEK: " + day);
        }
    }

    private static void validaMese(String mese) throws CronValidationException {
        if (!mese.equals("*")) {
            try {
                int meseNum = Integer.parseInt(mese);
                if (meseNum < 1 || meseNum > 12) {
                    throw new CronValidationException("Mese non valido: " + mese);
                }
            } catch (NumberFormatException e) {
                // Se non è un numero, verifichiamo che sia nella mappa dei mesi validi
                if (!MESI.containsKey(mese.toLowerCase())) {
                    throw new CronValidationException("Mese non valido: " + mese);
                }
            }
        }
    }
}
