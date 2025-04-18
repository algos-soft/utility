package it.algos.utility.schedule;

import lombok.Getter;
import lombok.Setter;

import static it.algos.vbase.boot.BaseCost.SPAZIO;

public class WrapTask {

    private static final String TASK = "task";

    private final CronService cronService;

    @Getter
    public boolean status;

    @Getter
    public String sigla;

    @Getter
    public String description;

    @Getter
    public boolean scheduled;

    @Getter
    public String cron;

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
            boolean status,
            String description,
            boolean scheduled,
            String cron,
            int durataTotaleMinuti) {
        this.cronService = cronService;
        this.sigla = sigla;
        this.status = status;
        this.description = description;
        this.scheduled = scheduled;
        this.cron = cron;
        this.durataTotaleMinuti = durataTotaleMinuti;
    }


    public String infoCron() {
        String stato = status ? "acceso" : "spento";
        String durata = durataTotaleMinuti < 1 ? "meno di 1 minuto" : durataTotaleMinuti + SPAZIO + "minuti";
        return String.format("%s (%s) - %s [%s] (in %s)", sigla, stato, description, scheduled ? cron : "not scheduled", durata);
    }

    public String getSiglaBreve() {
        return sigla.startsWith(TASK) ? sigla.substring(TASK.length()) : sigla;
    }

    public String infoText() {
        String stato = status ? "acceso" : "spento";
        String durata = durataTotaleMinuti < 1 ? "meno di 1 minuto" : durataTotaleMinuti + SPAZIO + "minuti";
        return String.format("%s (%s) - %s [%s] (in %s)", sigla, stato, description, scheduled ? getCronText() : "not scheduled", durata);
    }

    public String getCronText() {
        return cronService.info(cron);
    }

}
