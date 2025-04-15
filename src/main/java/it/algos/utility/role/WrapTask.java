package it.algos.utility.role;

import lombok.Getter;
import lombok.Setter;

import static it.algos.vbase.boot.BaseCost.SPAZIO;

public class WrapTask {

    private static final String TASK = "task";

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

    @Setter
    @Getter
    private int durataTotaleMinuti;

    @Setter
    @Getter
    private String backgroundColor;

    @Setter
    @Getter
    private String textColor;


    public WrapTask(String sigla, boolean status, String descrizione, boolean scheduled, String cron, int durataTotaleMinuti) {
        this.sigla = sigla;
        this.status = status;
        this.descrizione = descrizione;
        this.scheduled = scheduled;
        this.cron = cron;
        this.durataTotaleMinuti = durataTotaleMinuti;
    }

    public String info() {
        String stato = status ? "acceso" : "spento";
        String durata = durataTotaleMinuti < 1 ? "meno di 1 minuto" : durataTotaleMinuti + SPAZIO + "minuti";
        return String.format("%s (%s) - %s [%s] (in %s)", sigla, stato, descrizione, scheduled ? cron : "not scheduled", durata);
    }

    public String getSiglaBreve() {
        return sigla.startsWith(TASK) ? sigla.substring(TASK.length()) : sigla;
    }

}
