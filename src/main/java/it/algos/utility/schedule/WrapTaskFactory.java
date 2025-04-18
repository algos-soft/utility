package it.algos.utility.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static it.algos.vbase.boot.BaseCost.VUOTA;

@Component
public class WrapTaskFactory {

    private final CronService cronService;

    @Autowired
    public WrapTaskFactory(CronService cronService) {
        this.cronService = cronService;
    }

    public WrapTask createWrapTask(
            String sigla,
            boolean status,
            String description,
            boolean scheduled,
            String cron,
            int durata) {

        // Passiamo il cronService insieme agli altri parametri
        return new WrapTask(
                cronService,  // Prima il servizio iniettato
                sigla != null ? sigla : VUOTA,
                status,
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
        private boolean status = false;
        private String description = "";
        private boolean scheduled = false;
        private String cron = VUOTA;
        private int durata = 0;

        public WrapTaskBuilder sigla(String sigla) {
            this.sigla = sigla;
            return this;
        }

        public WrapTaskBuilder status(boolean status) {
            this.status = status;
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
                    cronService,  // La dipendenza Spring viene dalla factory
                    sigla,
                    status,
                    description,
                    scheduled,
                    cron,
                    durata
            );
        }
    }
}