package it.algos.utility.role;

import it.algos.vbase.enumeration.TypeColor;
import lombok.Getter;
import lombok.Setter;

import static it.algos.vbase.boot.BaseCost.SPAZIO;

public class WrapTask {

    private static final String TASK="task";
    @Getter
    public boolean status;

    @Getter
    public String sigla;

    @Getter
    public String descrizione;

    @Getter
    public boolean scheduled;

    @Getter
    public String cron;

    @Getter
    private int minuti;

    @Setter
    @Getter
    private String coloreBackground;

    @Setter
    @Getter
    private String coloreTesto;

    public WrapTask(String sigla, boolean status, String descrizione, boolean scheduled, String cron, int minuti) {
        this.sigla = sigla;
        this.status = status;
        this.descrizione = descrizione;
        this.scheduled = scheduled;
        this.cron = cron;
        this.minuti = minuti;
    }

    public String info() {
        String stato = status ? "acceso" : "spento";
        String durata = minuti < 1 ? "meno di 1 minuto" : minuti + SPAZIO + "minuti";
        return String.format("%s (%s) - %s [%s] (in %s)", sigla, stato, descrizione, scheduled ? cron : "not scheduled", durata);
    }

    public String getSiglaBreve() {
        return sigla.startsWith(TASK) ? sigla.substring(TASK.length()) : sigla;
    }

//    public String getColoreFinaleBackground() {
//        return status ? coloreBackground : blendWithGray(coloreBackground, 0.5);
//    }
//
//    // Mixa il colore originale con il grigio (#D3D3D3) al livello indicato (0.0 - solo grigio, 1.0 - solo colore originale)
//    private String blendWithGray(String htmlColor, double percentOriginal) {
//        String hex = htmlColorNameToHex(htmlColor.toLowerCase());
//        if (hex == null) return "#D3D3D3"; // fallback
//
//        int r = Integer.parseInt(hex.substring(1, 3), 16);
//        int g = Integer.parseInt(hex.substring(3, 5), 16);
//        int b = Integer.parseInt(hex.substring(5, 7), 16);
//
//        int grayR = 211, grayG = 211, grayB = 211; // RGB di #D3D3D3
//
//        int mixedR = (int) (r * percentOriginal + grayR * (1 - percentOriginal));
//        int mixedG = (int) (g * percentOriginal + grayG * (1 - percentOriginal));
//        int mixedB = (int) (b * percentOriginal + grayB * (1 - percentOriginal));
//
//        return String.format("#%02X%02X%02X", mixedR, mixedG, mixedB);
//    }
//
//    // Mappa alcuni nomi HTML comuni a esadecimale
//    private String htmlColorNameToHex(String name) {
//        return switch (name) {
//            case "red" -> "#FF0000";
//            case "green" -> "#008000";
//            case "blue" -> "#0000FF";
//            case "yellow" -> "#FFFF00";
//            case "orange" -> "#FFA500";
//            case "purple" -> "#800080";
//            case "cyan" -> "#00FFFF";
//            case "magenta" -> "#FF00FF";
//            case "pink" -> "#FFC0CB";
//            case "brown" -> "#A52A2A";
//            case "black" -> "#000000";
//            case "white" -> "#FFFFFF";
//            case "gray", "grey" -> "#808080";
//            default -> null;
//        };
//    }

//    public String getColoreFinaleBackground() {
//        if (status) {
//            return coloreBackground;
//        } else {
//            return getStripedBackground(coloreBackground);
//        }
//    }

//    private String getStripedBackground(String baseColor) {
//        return String.format("repeating-linear-gradient(45deg, %s, %s 5px, white 5px, white 10px)", baseColor, baseColor);
//    }

//    public String getColoreFinaleBackground() {
//        return coloreBackground;
//    }

//    public double getOpacity() {
//        return status ? 1.0 : 0.4;
//    }
}
