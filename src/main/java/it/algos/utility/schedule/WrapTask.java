package it.algos.utility.schedule;

import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.Locale;

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

    @Getter
    public String cronSpringDesc;

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
        this.cronSpringDesc = fixCronDesc(cronSpring);
        this.durataTotaleMinuti = durataTotaleMinuti;
    }

    public String fixCronDesc(String cronSpring) {
        String cronSpringDesc = VUOTA;
        CronParser parser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.SPRING));
        Cron cron = null;

        if (StringUtils.hasText(cronSpring)) {
            cron = parser.parse(cronSpring);
        }
        // Generazione della descrizione in italiano
        CronDescriptor descriptor = CronDescriptor.instance(Locale.ENGLISH);

        if (cron != null) {
            cronSpringDesc = descriptor.describe(cron);
        }

        return cronSpringDesc;
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

    public String infoList() {
        String message = sigla + SPAZIO + description + SPAZIO;
        boolean flag = masterEnabled && taskEnabled;
        String status = flag ? "<span style=\"color: red;font-weight:bold\">accesa</span>" : "<span style=\"color: blue;font-weight:bold\">spenta</span>";
        message += "[" + status + SPAZIO + cronSpringDesc + "]";
        return message;
    }

    // Metodo di utilità per verificare se la task è effettivamente eseguibile
    public boolean isExecutable() {
        return masterEnabled && taskEnabled && scheduled;
    }

    public String getStatus() {
        String statoGlobale = masterEnabled ? "abilitate" : "disabilitate";
        String statoTask = taskEnabled ? "acceso" : "spento";
//        return String.format("(%s/%s)", statoGlobale, statoTask);
        return masterEnabled && taskEnabled ? "(accesa)" : "(spenta)";
    }

}

