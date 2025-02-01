package it.algos.utility;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import it.algos.vbase.annotation.IView;
import it.algos.vbase.components.SimpleHorizontalLayout;
import it.algos.vbase.components.SimpleVerticalLayout;
import it.algos.vbase.constant.Gruppo;
import it.algos.vbase.service.MongoService;
import it.algos.vbase.ui.view.AView;
import it.algos.evento24.view.EMainLayout;
import it.algos.vbase.ui.wrapper.ASpan;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Route(value = "reset", layout = EMainLayout.class)
@IView(menuGroup = Gruppo.UTILITY, menuName = "Reset", vaadin = VaadinIcon.REFRESH)
public class ResetView extends AView {

    @Autowired
    private UtilityService utilityService;

    ResetView() {
        super();
    }

    private Checkbox deleteAllBefore;

    private Checkbox mainProjectOnly;


    private SimpleVerticalLayout logPanel;

    @Autowired
    private MongoService mongoService;

    private UI ui;

    @PostConstruct
    private void init() {

        ui = UI.getCurrent();
        this.setSpacing(true);
        this.setMargin(true);
        this.setPadding(true);
        add(new H2("Reset di tutte le librerie di questa applicazione"));
        add(ASpan.text("Questa view viene attivata dal flag [algos.project.usa.check.resetStartup]").verde().bold().big());
        add(ASpan.text("Non dipende dal flag usaStartup() delle singole Entity").verde().bold().big());
        add(ASpan.text("Esegue per tutti i moduli che implementano reset()").verde().bold().big());
        add(ASpan.text("Cancella tutti i dati esistenti e li ricrea").rosso().bold().big());

        SimpleHorizontalLayout layout = new SimpleHorizontalLayout();
        layout.setWidthFull();
//        layout.add(createMysqlPanel());
//        layout.add(createMongoPanel());
        add(layout);

        SimpleHorizontalLayout startLayout = new SimpleHorizontalLayout();
        startLayout.setWidthFull();
        startLayout.setJustifyContentMode(JustifyContentMode.START);
        Button startButton = new Button("Start reset");
        startButton.setWidth(25, Unit.REM);
        startButton.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                startButtonClicked();
            }
        });

        mainProjectOnly = new Checkbox("mainProjectOnly", true);
        add(mainProjectOnly);

        deleteAllBefore = new Checkbox("deleteAllBefore");
        add(deleteAllBefore);

        startLayout.add(startButton);
        add(startLayout);

        // pannello che contiene i log del service (stato avanzamento)
        logPanel = new SimpleVerticalLayout();
        add(logPanel);

        // ascolta il log del migrationService e aggiungi una riga al log panel ogni volta che la ricevi
//        migrationService.setMigrationLogListener((level, text) -> {
//            ASpan span = ASpan.text(text).size("0.7rem");
//            switch (level){
//                case INFO -> span.color("black");
//                case WARN -> span.color("orange");
//                case ERROR -> span.color("red");
//                default ->  span.color("black");
//            }
//
//            // the call comes from a different thread
//            ui.access(() -> {
//                logPanel.add(span);
//                ui.getPage().executeJs("window.scrollTo(0, document.body.scrollHeight)");
//            });
//
//        });

    }


    public void startButtonClicked() {
        utilityService.resetStartup(deleteAllBefore.getValue(), mainProjectOnly.getValue());
//        CheckResult mongoResult = checkMongoDb();
//        if (!mongoResult.success()) {
//            Notification notification = new Notification("Mongo DB non risponde, controlla il server e la configurazione", 3000);
//            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
//            notification.open();
//            return;
//        }

//        CheckResult mysqlResult = checkMysqlDb();
//        if (!mysqlResult.success()) {
//            Notification notification = new Notification("Il database Mysql DB non risponde, controlla il server e la configurazione", 3000);
//            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
//            notification.open();
//            return;
//        }

//        ConfirmDialog dialog = new ConfirmDialog();
//        dialog.setCancelable(true);
//        dialog.setHeader("Migrazione MySql -> Mongodb");
//
//        Html html1 = new Html("<p style='color:red;font-weight:bold'>Tutti i dati su MongoDB verranno sovrascritti!</p>");
//        Html html2 = new Html("<p>Vuoi continuare?</p>");
//        Div div = new Div(html1, html2);
//        dialog.add(div);
//        dialog.setConfirmText("Procedi");
//        dialog.addConfirmListener((ComponentEventListener<ConfirmDialog.ConfirmEvent>) confirmEvent -> {
//            logPanel.removeAll();
////            migrationService.startMigration();
//        });
//        dialog.open();
//
    }


}
