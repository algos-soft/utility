package it.algos.utility.schedule;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import static it.algos.vbase.boot.BaseCost.SPAZIO;
import static it.algos.vbase.boot.BaseCost.VUOTA;

public class WrapTask {

    private static final String TASK = "task";

    private final CronService cronService;

    @Getter
    public boolean masterEnabled;

    @Getter
    public boolean taskEnabled;

    @Getter
    public String sigla;

    @Getter
    public String description;

    @Getter
    public boolean scheduled;

    @Getter
    public String cronSpring;

    @Setter
    @Getter
    private int durataTotaleMinuti;

    @Setter
    @Getter
    private String backgroundColor;

    @Setter
    @Getter
    private String textColor;


    public WrapTask(
            CronService cronService,  // Dipendenza Spring come primo parametro
            String sigla,
            boolean masterEnabled,
            boolean taskEnabled,
            String description,
            boolean scheduled,
            String cronSpring,
            int durataTotaleMinuti) {
        this.cronService = cronService;
        this.sigla = sigla;
        this.masterEnabled = masterEnabled;
        this.taskEnabled = taskEnabled;
        this.description = description;
        this.scheduled = scheduled;
        this.cronSpring = cronSpring;
        this.durataTotaleMinuti = durataTotaleMinuti;
    }

    public String getSiglaBreve() {
        return sigla.startsWith(TASK) ? sigla.substring(TASK.length()) : sigla;
    }

    public String getCronText() {
        return StringUtils.hasText(cronSpring) ? cronService.info(cronSpring) : VUOTA;
    }

    private String info(String cronSpringText) {
        String durata = durataTotaleMinuti < 1 ? "meno di 1 minuto" : durataTotaleMinuti + SPAZIO + "minuti";
        return String.format("%s %s - %s [%s] (previsti %s)", sigla, getStatus(), description, scheduled ? cronSpringText : "not scheduled", durata);
    }

    public String infoCron() {
        return info(cronSpring);
    }

    public String infoText() {
        return info(getCronText());
    }

    // Metodo di utilità per verificare se la task è effettivamente eseguibile
    public boolean isExecutable() {
        return masterEnabled && taskEnabled && scheduled;
    }

    public String getStatus() {
        String statoGlobale = masterEnabled ? "abilitate" : "disabilitate";
        String statoTask = taskEnabled ? "acceso" : "spento";
        return String.format("(%s/%s)", statoGlobale, statoTask);
    }

}

