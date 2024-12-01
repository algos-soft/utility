package it.algos.utility;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static it.algos.vbase.boot.BaseCost.CAPO;
import static it.algos.vbase.boot.BaseCost.VUOTA;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: sab, 23-nov-2024
 * Time: 09:56
 */
public class StyleExtractor {


    /**
     * Converte un colore CSS in un codice ANSI.
     *
     * @param cssColor il colore CSS (es. "blue", "red", "#0000ff")
     * @return il codice ANSI corrispondente
     */
    public static String getAnsiColor(String cssColor) {
        return switch (cssColor.toLowerCase()) {
            case "black" -> "\u001B[30m";
            case "red" -> "\u001B[31m";
            case "green" -> "\u001B[32m";
            case "yellow" -> "\u001B[33m";
            case "blue" -> "\u001B[34m";
            case "magenta" -> "\u001B[35m";
            case "cyan" -> "\u001B[36m";
            case "white" -> "\u001B[37m";
            default -> ""; // Nessun colore se non riconosciuto
        };
    }


    // Mappa dei colori ANSI
    private static final Map<String, String> ANSI_COLORS = new HashMap<>() {{
        put("red", "\u001B[31m");
        put("green", "\u001B[32m");
        put("yellow", "\u001B[33m");
        put("blue", "\u001B[34m");
        put("reset", "\u001B[0m");
    }};

    // Mappa dei pesi del font
    private static final Map<String, String> ANSI_FONT_WEIGHT = new HashMap<>() {{
        put("bold", "\u001B[1m");
        put("normal", "");
    }};

    // Mappa degli stili del font
    private static final Map<String, String> ANSI_FONT_STYLE = new HashMap<>() {{
        put("italic", "\u001B[3m");
        put("normal", "");
    }};


    /**
     * Estrae gli stili di tutti i componenti figli di un layout e del layout stesso in una mappa.
     *
     * @param layout il layout Vaadin da cui estrarre gli stili
     * @return una mappa contenente le proprietà di stile di ciascun componente, inclusi gli stili del layout
     */
    /**
     * Estrae gli stili di tutti i componenti figli di un layout e del layout stesso in una mappa.
     *
     * @param component il componente Vaadin da cui estrarre gli stili
     * @return una mappa contenente le proprietà di stile di ciascun componente, inclusi gli stili del componente principale
     */
    public static Map<Component, Map<String, String>> extractStyles(Component component) {
        Map<Component, Map<String, String>> stylesMap = new LinkedHashMap<>();

        // Aggiungi gli stili del componente principale
        if (component instanceof HasStyle styledComponent) {
            Map<String, String> componentStyles = new LinkedHashMap<>();
            styledComponent.getStyle().getNames()
                    .forEach(styleName -> componentStyles.put(styleName, styledComponent.getStyle().get(styleName)));

            // Aggiungi il contenuto HTML del componente principale come proprietà speciale, se disponibile
            String innerHTML = component.getElement().getProperty("innerHTML");
            if (innerHTML != null) {
                componentStyles.put("innerHTML", innerHTML);
            }

            // Aggiungi il componente principale alla mappa
            stylesMap.put(component, componentStyles);
        }

        // Estrai gli stili dei componenti figli (se presenti)
        component.getChildren().forEach(child -> {
            if (child instanceof HasStyle styledChild) {
                Map<String, String> childStyles = new LinkedHashMap<>();
                styledChild.getStyle().getNames()
                        .forEach(styleName -> childStyles.put(styleName, styledChild.getStyle().get(styleName)));

                // Aggiungi il contenuto HTML come proprietà speciale, se disponibile
                String innerHTML = child.getElement().getProperty("innerHTML");
                if (innerHTML != null) {
                    childStyles.put("innerHTML", innerHTML);
                }

                // Aggiungi il componente figlio alla mappa
                stylesMap.put(child, childStyles);
            }
        });

        return stylesMap;
    }


    /**
     * Applica gli stili e stampa il testo nel terminale usando i codici ANSI.
     *
     */
    public static String getStyledText(String innerHTML, Map<String, String> styles) {
        // Iniziamo con una stringa vuota
        StringBuilder styledText = new StringBuilder();

        // Applica il colore (se presente)
        String color = styles.get("color");
        if (color != null) {
            styledText.append(ANSI_COLORS.getOrDefault(color, ANSI_COLORS.get("reset")));
        }

        // Applica il peso del font (se presente)
        String fontWeight = styles.get("font-weight");
        if (fontWeight != null) {
            styledText.append(ANSI_FONT_WEIGHT.getOrDefault(fontWeight, ANSI_FONT_WEIGHT.get("normal")));
        }

        // Applica lo stile del font (se presente)
        String fontStyle = styles.get("font-style");
        if (fontStyle != null) {
            styledText.append(ANSI_FONT_STYLE.getOrDefault(fontStyle, ANSI_FONT_STYLE.get("normal")));
        }

        // Aggiungi il contenuto HTML (se presente)
        if (innerHTML != null) {
            styledText.append(innerHTML);
        } else {
//            String testo = component.getElement().getText();
//            String testo2 = component.getElement().getOuterHTML();
//            styledText.append(component.getElement().getText());
        }

        // Ripristina il formato (resetta gli stili)
        styledText.append(ANSI_COLORS.get("reset"));

        // Stampa il testo formattato
        return styledText.toString();
    }


    /**
     * Applica gli stili e stampa il testo nel terminale usando i codici ANSI.
     *
     * @param component il componente di cui si vogliono applicare gli stili
     */
    public static String getStyledText(Component component) {
        StringBuilder buffer = new StringBuilder();
        String outPut;
        Map<Component, Map<String, String>> stylesMap = extractStyles( component);

        Map mappa = stylesMap.get(component);
        stylesMap.remove(component);
        if (stylesMap.size() == 1) {
            Component compConSpan = stylesMap.keySet().iterator().next();
            String testo = getTextFromSpan(compConSpan);
            return getStyledText(testo, mappa);
        } else {
            for (Component comp : stylesMap.keySet()) {
                String testo = getTextFromSpan(comp);
                outPut = getStyledText(testo, mappa);
                buffer.append(outPut);
                buffer.append(CAPO);
            }
        }

        return buffer.toString();
    }


//    /**
//     * Stampa la mappa degli stili in un formato leggibile.
//     *
//     * @param stylesMap la mappa contenente gli stili dei componenti
//     */
//    public static void printStyles(Map<Component, Map<String, String>> stylesMap) {
//        stylesMap.forEach((component, styles) -> {
//            System.out.println("Component: " + component.getClass().getSimpleName());
//            styles.forEach((key, value) ->
//                    System.out.println("  " + key + ": " + (value != null ? value : "default")));
//            System.out.println("-------------------------");
//        });
//    }

    public static String getTextFromSpan(Component component) {
        // Ottieni HTML del layout
        String html = component.getElement().getOuterHTML();

        // Usa Jsoup per analizzare HTML
        Document doc = Jsoup.parse(html);

        // Seleziona tutti i tag <span>
        Elements spans = doc.select("span");

        // Itera sui tag <span> [dovrebbe essercene uno solo]
        Optional<Element> optSpan = spans.stream().findFirst();

        return optSpan.isPresent() ? optSpan.get().text() : VUOTA;
    }

//    public static void test() {
//        // Creazione di un esempio di layout
//        FlexLayout layout = new FlexLayout();
//
//        // Aggiungi componenti con stili
//        FlexComponent div1 = new FlexLayout();
//        div1.getElement().setText("Esempio testo 1");
//        div1.getStyle().set("font-weight", "bold").set("font-size", "16px");
//
//        FlexComponent div2 = new FlexLayout();
//        div2.getElement().setText("Esempio testo 2");
//        div2.getStyle().set("font-style", "italic").set("line-height", "1.5");
//
//        layout.add((Component) div1, (Component) div2);
//
//        // Estrarre e stampare gli stili
//        Map<Component, Map<String, String>> stylesMap = extractStyles(layout);
//        printStyles(stylesMap);
//    }

    /**
     * Estrae il valore di una proprietà CSS da una stringa di stile inline.
     *
     * @param style   lo stile inline (es. "color:blue; background-color:yellow;")
     * @param property il nome della proprietà CSS (es. "color")
     * @return il valore della proprietà (es. "blue") o null se non trovato
     */
    public static String extractCssProperty(String style, String property) {
        for (String rule : style.split(";")) {
            String[] keyValue = rule.split(":");
            if (keyValue.length == 2 && keyValue[0].trim().equalsIgnoreCase(property)) {
                return keyValue[1].trim();
            }
        }
        return null;
    }


    public static String getRigaColorata(Component component) {
        StringBuilder riga = new StringBuilder();

        // Itera sui figli del componente per trovare l'elemento con lo stile
        component.getElement().getChildren().forEach(child -> {
            String styleAttribute = child.getAttribute("style");
            String color = null;

            // Estrai il colore dallo stile se presente
            if (styleAttribute != null) {
                color = StyleExtractor.extractCssProperty(styleAttribute, "color");
            }

            // Converti il colore in ANSI
            String ansiColor = (color != null) ? StyleExtractor.getAnsiColor(color) : "";

            // Estrai il testo dell'elemento
            String text = child.getText();

            // Restituisce il testo con il colore
            riga.append(ansiColor).append(text).append("\u001B[0m");
        });

        return riga.toString();
    }

}

