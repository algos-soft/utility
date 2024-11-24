package it.algos.utility;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
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
    public static Map<Component, Map<String, String>> extractStyles(FlexComponent layout) {
        Map<Component, Map<String, String>> stylesMap = new HashMap<>();

        // Aggiungi gli stili del layout principale
        if (layout instanceof HasStyle styledLayout) {
            Map<String, String> layoutStyles = new HashMap<>();
            styledLayout.getStyle().getNames()
                    .forEach(styleName -> layoutStyles.put(styleName, styledLayout.getStyle().get(styleName)));

            // Aggiungi il contenuto HTML del layout principale come proprietà speciale, se disponibile
            String innerHTML = layout.getElement().getProperty("innerHTML");
            if (innerHTML != null) {
                layoutStyles.put("innerHTML", innerHTML);
            }

            // Aggiungi il layout principale alla mappa
            stylesMap.put((Component) layout, layoutStyles);
        }

        // Estrai gli stili dei componenti figli
        layout.getChildren().forEach(component -> {
            if (component instanceof HasStyle styledComponent) {
                Map<String, String> styles = new HashMap<>();
                styledComponent.getStyle().getNames()
                        .forEach(styleName -> styles.put(styleName, styledComponent.getStyle().get(styleName)));

                // Aggiungi il contenuto HTML come proprietà speciale, se disponibile
                String innerHTML = component.getElement().getProperty("innerHTML");
                if (innerHTML != null) {
                    styles.put("innerHTML", innerHTML);
                }

                // Aggiungi il componente figlio alla mappa
                stylesMap.put(component, styles);
            }
        });

        return stylesMap;
    }


//    /**
//     * Applica gli stili e stampa il testo nel terminale usando i codici ANSI.
//     *
//     * @param component il componente di cui si vogliono applicare gli stili
//     */
//    public static String printStyledText(Component component, Map<String, String> styles) {
//        // Iniziamo con una stringa vuota
//        StringBuilder styledText = new StringBuilder();
//
//        // Applica il colore (se presente)
//        String color = styles.get("color");
//        if (color != null) {
//            styledText.append(ANSI_COLORS.getOrDefault(color, ANSI_COLORS.get("reset")));
//        }
//
//        // Applica il peso del font (se presente)
//        String fontWeight = styles.get("font-weight");
//        if (fontWeight != null) {
//            styledText.append(ANSI_FONT_WEIGHT.getOrDefault(fontWeight, ANSI_FONT_WEIGHT.get("normal")));
//        }
//
//        // Applica lo stile del font (se presente)
//        String fontStyle = styles.get("font-style");
//        if (fontStyle != null) {
//            styledText.append(ANSI_FONT_STYLE.getOrDefault(fontStyle, ANSI_FONT_STYLE.get("normal")));
//        }
//
//        // Aggiungi il contenuto HTML (se presente)
//        String innerHTML = styles.get("innerHTML");
//        if (innerHTML != null) {
//            styledText.append(innerHTML);
//        } else {
////            String testo = component.getElement().getText();
////            String testo2 = component.getElement().getOuterHTML();
////            styledText.append(component.getElement().getText());
//        }
//
//        // Ripristina il formato (resetta gli stili)
//        styledText.append(ANSI_COLORS.get("reset"));
//
//        // Stampa il testo formattato
//        return styledText.toString();
//    }


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
        Map<Component, Map<String, String>> stylesMap = extractStyles((FlexComponent) component);

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
}

