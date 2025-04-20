package it.algos.utility.schedule;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import it.algos.utility.UtilityService;
import it.algos.vbase.annotation.IView;
import it.algos.vbase.boot.BaseBoot;
import it.algos.vbase.boot.BaseCost;
import it.algos.vbase.components.SimpleVerticalLayout;
import it.algos.vbase.constant.Gruppo;
import it.algos.vbase.service.MainLayoutService;
import it.algos.vbase.ui.view.AView;
import it.algos.vbase.ui.view.MainLayout;
import it.algos.vbase.ui.wrapper.ASpan;
import it.algos.wiki24.backend.boot.WikiBoot;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.List;

import static it.algos.vbase.boot.BaseCost.SPAZIO;

@Slf4j
@PageTitle("Elenco task")
@Route(value = "task", layout = MainLayout.class)
@IView(menuGroup = Gruppo.NESSUNO, menuName = "Task elenco", vaadin = VaadinIcon.LINES_LIST)
public class TaskView extends AView {

    @Autowired
    private UtilityService utilityService;

    @Autowired
    ApplicationContext applicationContext;

    private Checkbox deleteAllBefore;

    private Checkbox mainProjectOnly;


    private SimpleVerticalLayout logPanel;

    @Autowired
    private MainLayoutService mainLayoutService;

    @Value("${algos.project.boot.qualifier}")
    private String bootClazzQualifier;

    private UI ui;

    private BaseBoot projectBoot;

    TaskView() {
        super();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        String title = "Elenco task";
        Component img = VaadinIcon.LINES_LIST.create();
        img.getElement().getStyle().set("color", "blue");
        mainLayoutService.setTopBarTitle(title, img);
    }

    @PostConstruct
    protected void init() {

        ui = UI.getCurrent();
        this.setSpacing(true);
        this.setMargin(true);
        this.setPadding(true);
        add(new H2("Task scheduled per questa applicazione"));

        projectBoot = (BaseBoot) applicationContext.getBean(bootClazzQualifier);
        List<Method> orderedMethods = ((WikiBoot) projectBoot).getOrderedScheduledMethods();

        String message;
        int pos = 1;
        for (Method method : orderedMethods) {
            message = pos++ + BaseCost.PARENTESI_TONDA_END + SPAZIO;
            message += utilityService.infoCron(method);
            add(ASpan.text(message).blue().bold());
        }
    }

}
