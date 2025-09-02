package it.algos.utility.schedule;

import it.algos.vbase.service.HtmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static it.algos.vbase.boot.BaseCost.VUOTA;

@Component
public class WrapTaskFactory {

    private final CronService cronService;
    private final HtmlService htmlService;

    @Autowired
    public WrapTaskFactory(CronService cronService, HtmlService htmlService) {
        this.cronService = cronService;
        this.htmlService = htmlService;
    }

    public WrapTask createWrapTask(
            String sigla,
            boolean masterEnabled,
            boolean taskEnabled,
            String description,
            boolean scheduled,
            String cron,
            int durata) {

        // Passiamo il cronService insieme agli altri parametri
        return new WrapTask(
                cronService,  // Prima il servizio iniettato
                htmlService,  // Prima il servizio iniettato
                sigla != null ? sigla : VUOTA,
                masterEnabled,
                taskEnabled,
                description != null ? description : VUOTA,
                scheduled,
                cron != null ? cron : VUOTA,
                durata
        );
    }

    // Versione con Builder per una sintassi più pulita
    public WrapTaskBuilder builder() {
        return new WrapTaskBuilder();
    }

    public class WrapTaskBuilder {
        private String sigla = "";
        private boolean masterEnabled = false;
        private boolean taskEnabled = false;
        private String description = "";
        private boolean scheduled = false;
        private String cron = VUOTA;
        private int durata = 0;

        public WrapTaskBuilder sigla(String sigla) {
            this.sigla = sigla;
            return this;
        }

        public WrapTaskBuilder masterEnabled(boolean masterEnabled) {
            this.masterEnabled = masterEnabled;
            return this;
        }

        public WrapTaskBuilder taskEnabled(boolean taskEnabled) {
            this.taskEnabled = taskEnabled;
            return this;
        }

        public WrapTaskBuilder description(String description) {
            this.description = description;
            return this;
        }

        public WrapTaskBuilder scheduled(boolean scheduled) {
            this.scheduled = scheduled;
            return this;
        }

        public WrapTaskBuilder cron(String cron) {
            this.cron = cron;
            return this;
        }

        public WrapTaskBuilder durata(int durata) {
            this.durata = durata;
            return this;
        }

        public WrapTask build() {
            return new WrapTask(
                    cronService,
                    htmlService,// La dipendenza Spring viene dalla factory
                    sigla,
                    masterEnabled,
                    taskEnabled,
                    description,
                    scheduled,
                    cron,
                    durata
            );
        }
    }
}