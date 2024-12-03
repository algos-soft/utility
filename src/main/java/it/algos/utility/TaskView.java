package it.algos.utility;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import it.algos.vbase.annotation.IView;
import it.algos.vbase.components.SimpleVerticalLayout;
import it.algos.vbase.constant.Gruppo;
import it.algos.vbase.pref.IPref;
import it.algos.vbase.service.MongoService;
import it.algos.vbase.ui.view.AView;
import it.algos.vbase.ui.view.MainLayout;
import it.algos.vbase.ui.wrapper.ASpan;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import static it.algos.vbase.boot.BaseCost.VUOTA;

@Slf4j
@Route(value = "task", layout = MainLayout.class)
@IView(menuGroup = Gruppo.UTILITY, menuName = "Task", vaadin = VaadinIcon.CALENDAR)
public class TaskView extends AView {

    @Autowired
    private UtilityService utilityService;

    TaskView() {
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
        add(new H2("Task scheduled per questa applicazione"));

        List<Method> methods = annotationService.getAnnotatedMethods(Scheduled.class);

        String message;
        for (Method method : methods) {
            Optional<String> otpCronInfo = getCronInfo(method);
            message= otpCronInfo.orElse(VUOTA);
            add(ASpan.text(message).verde().bold());
        }
    }


    public Optional<String> getCronInfo(@NonNull Class<?> clazz, @NonNull String methodName) {
        Method method;
        try {
            method = clazz.getMethod(methodName);
            return getCronInfo(method);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<String> getCronInfo(@NonNull Method method) {
        String methodName = method.getName();

        String cron = utilityService.getCronText(method).orElse(VUOTA);
        Optional<String> optPrefCode = utilityService.getPrefCode(method);

        Object optPref = null;
        try {
//            optPref = optPrefCode.isPresent() ? IPref.valueOf(optPrefCode.get()) : Optional.empty();
        } catch (Exception exception) {
            log.warn(exception.getMessage());
            log.warn("No enum constant WPref.{} in WikiBoot.getCronInfo()", optPrefCode.isPresent() ? optPrefCode.get() : VUOTA);
            return Optional.empty();
        }

        String description = ((IPref) optPref).getDescrizione();
        String status = ((IPref) optPref).is() ? "acceso" : "spento";

        String message = String.format("%s (%s) - %s %s", methodName, status, description, cron);
        return Optional.of(message);
    }

    public void logCronInfo() {
        List<Method> methods = annotationService.getAnnotatedMethods(Scheduled.class);

        for (Method method : methods) {
            Optional<String> otpCronInfo = getCronInfo(method);
            log.info(otpCronInfo.orElse(VUOTA));
        }
    }

}
