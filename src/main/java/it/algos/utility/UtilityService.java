package it.algos.utility;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.utility.schedule.ASchedule;
import it.algos.utility.schedule.CronService;
import it.algos.utility.schedule.WrapTask;
import it.algos.utility.schedule.WrapTaskFactory;
import it.algos.vbase.enumeration.TypeColor;
import it.algos.vbase.modules.preferenza.PreferenzaService;
import it.algos.vbase.mongo.MongoTemplateProvider;
import it.algos.vbase.pref.IPref;
import it.algos.vbase.service.AnnotationService;
import it.algos.vbase.service.ModuloService;
import it.algos.vbase.service.ReflectionService;
import it.algos.vbase.service.TextService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static it.algos.vbase.boot.BaseCost.VUOTA;

/**
 * Project crono
 * Created by Algos
 * User: gac
 * Date: mer, 13-nov-2024
 * Time: 07:34
 */
@Slf4j
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class UtilityService {

    @Autowired
    public ApplicationContext appContext;

    @Autowired
    public ReflectionService reflectionService;

    @Autowired
    public TextService textService;

    @Autowired
    public AnnotationService annotationService;

    @Autowired
    private MongoTemplateProvider mongoTemplateProvider;

    @Autowired
    public PreferenzaService preferenzaService;

    @Autowired
    private WrapTaskFactory wrapTaskFactory;

    @Autowired
    private CronService cronService;

    @Value("${algos.project.modulo}")
    protected String projectName;


    public void resetStartup(boolean deleteAllBefore, boolean mainProjectOnly) {
        List<Class<? extends ModuloService>> listClazzService;
        List<ModuloService> moduliSelezionati = new ArrayList<>();
        ModuloService unModulo = null;

        if (mainProjectOnly) {
            listClazzService = reflectionService.getSubClazzService(projectName);
        } else {
            listClazzService = reflectionService.getSubClazzService();
        }

        if (listClazzService == null) {
            log.warn("Nessun modulo trovato");
            return;
        }

        for (Class<? extends ModuloService> moduloServiceClazz : listClazzService) {
            try {
                unModulo = appContext.getBean(moduloServiceClazz);
                Class<?> targetClass = AopProxyUtils.ultimateTargetClass(unModulo);
                if (annotationService.usaResetStartup(unModulo.getEntityClass())) {
                    moduliSelezionati.add(unModulo);
                } else {
                    log.info("Il modulo {} non usa {}", targetClass.getSimpleName(), "resetStartup");
                }
            } catch (Exception unErrore) {
                log.warn(unErrore.getMessage(), unErrore);
            }
        }

        if (deleteAllBefore) {
            for (ModuloService modulo : moduliSelezionati) {
                modulo.deleteAll();
            }
        }

        for (ModuloService modulo : moduliSelezionati) {
            modulo.checkResetStartup(getMongoTemplate());
        }
    }


    /**
     * @return all the methods annotated with a given annotation from a given class
     */
    public List<Method> getAnnotatedMethods(@NonNull Class<?> clazz, @NonNull Class<? extends Annotation> annotation) {
        List<Method> methods = annotationService.getAnnotatedMethods(annotation);
        return methods
                .stream()
                .filter(method -> method.getDeclaringClass().getSimpleName().equals(clazz.getSimpleName()))
                .toList();
    }

    /**
     * @return all the name of methods annotated with a given annotation from a given class
     */
    public List<String> getAnnotatedMethodsName(@NonNull Class<?> clazz, @NonNull Class<? extends Annotation> annotation) {
        return getAnnotatedMethods(clazz, annotation)
                .stream()
                .map(Method::getName)
                .toList();
    }

    public boolean isAnnotationPresent(@NonNull Method method, @NonNull Class<? extends Annotation> annotation) {
        return method.isAnnotationPresent(annotation);
    }

    public <T extends Annotation> Optional<T> getOptionalAnnotation(@NonNull Method method, @NonNull Class<T> annotation) {
        return Optional.ofNullable(method.getAnnotation(annotation));
    }

    public Optional<String> getPrefCode(@NonNull Method method) {
        Optional<ASchedule> annotation = getOptionalAnnotation(method, ASchedule.class);
        return annotation.map(ASchedule::prefCode).filter(StringUtils::hasText);
    }

    public int getDurata(@NonNull Method method) {
        Optional<ASchedule> annotation = getOptionalAnnotation(method, ASchedule.class);
        return annotation.isPresent() ? annotation.get().durataMinuti() : 0;
    }

    public Optional<String> getCron(@NonNull Method method) {
        Optional<Scheduled> annotation = getOptionalAnnotation(method, Scheduled.class);
        return annotation.map(Scheduled::cron).filter(StringUtils::hasText);
    }


    public String getCronSpring(@NonNull Method method) {
        String cronText = "not scheduled";
        Optional<String> optCron = this.getCron(method);

        if (optCron.isPresent()) {
            cronText = cronService.info(optCron.get());
        }

        return cronText;
    }


    public Optional<String> getCronInfoSpring(@NonNull Method method) {
        final String methodName = method.getName();  // Made final

        String cronText = this.getCronSpring(method);
        cronText = textService.isEmpty(cronText) ? "not scheduled" : cronText;

        Optional<IPref> optPref = getPref(method);

        // Use ifPresent to avoid casting and ensure variables are effectively final
        String finalCron = cronText;
        return optPref.map(pref -> {
            final String description = pref.getDescrizione();  // Made final
            final String status = pref.is() ? "acceso" : "spento";  // Made final
            final int durata = getDurata(method);  // Made final
            String message = String.format("%s (%s) - %s [%s] (in %s)", methodName, status, description, finalCron, durata);
            return message;
        });
    }

    public Optional<String> getCronInfoDesc(@NonNull Method method) {
        final String methodName = method.getName();  // Made final

        String cronText = this.getCronSpring(method);
        cronText = textService.isEmpty(cronText) ? "not scheduled" : cronText;

        Optional<IPref> optPref = getPref(method);

        // Use ifPresent to avoid casting and ensure variables are effectively final
        String finalCron = cronText;
        return optPref.map(pref -> {
            final String description = pref.getDescrizione();  // Made final
            final String status = pref.is() ? "acceso" : "spento";  // Made final
            final int durata = getDurata(method);  // Made final
            String message = String.format("%s (%s) - %s [%s] (in %s)", methodName, status, description, finalCron, durata);
            return message;
        });
    }


    public Optional<IPref> getPref(@NonNull Method method) {
        Optional<IPref> optPref = Optional.empty();
        Optional<String> optPrefCode = this.getPrefCode(method);

        try {
            if (optPrefCode.isPresent()) {
                optPref = Optional.ofNullable(preferenzaService.getPref(optPrefCode.get()));
            }
        } catch (Exception exception) {
            log.warn(exception.getMessage());
            log.warn("No enum constant WPref.{} in WikiBoot.getCronInfo()", optPrefCode.orElse(VUOTA));
        }

        return optPref;
    }


    public Optional<WrapTask> getWrapTask(@NonNull Method method) {
        Optional<String> optCron = this.getCron(method);
        boolean scheduled = optCron.isPresent();
        int durata = getDurata(method);
        Optional<IPref> optPref = getPref(method);

        if (optPref.isPresent()) {
            IPref pref = optPref.get();  // Estrai l'oggetto da Optional
            String sigla = pref.getKeyCode();  // Made final
            boolean status = pref.is();
            String description = pref.getDescrizione();
            WrapTask wrap = wrapTaskFactory.builder()
                    .sigla(sigla)
                    .status(status)
                    .description(description)
                    .scheduled(scheduled)
                    .cron(optCron.isPresent() ? optCron.get() : VUOTA)
                    .durata(durata)
                    .build();

            return Optional.of(wrap);
        }

        return Optional.empty();
    }


    public List<WrapTask> getListWrapTask(@NonNull List<Method> methods) {
        List<WrapTask> tasks = new ArrayList<>();
        Optional<WrapTask> optTask;
        WrapTask task;
        int pos = 3; // esclude i primi colori bianco e nero
        String[] colors;

        for (Method method : methods) {
            optTask = getWrapTask(method);
            if (optTask.isPresent()) {
                task = optTask.get();
                colors = getColore(pos++);
                task.setBackgroundColor(colors[0]);
                task.setTextColor(colors[1]);
                tasks.add(task);
            }
        }

        return tasks;
    }


    public List<WrapTask> getScheduledListWrapTask(@NonNull List<Method> methods) {
        List<WrapTask> tasks = new ArrayList<>();
        Optional<WrapTask> task;

        for (Method method : methods) {
            task = getWrapTask(method);
            if (task.isPresent() && task.get().isScheduled()) {
                tasks.add(task.get());
            }
        }

        return tasks;
    }


    public String infoCron(@NonNull Method method) {
        String info = VUOTA;
        Optional<WrapTask> optWrapTask;

        optWrapTask = getWrapTask(method);
        if (optWrapTask.isPresent()) {
            info = optWrapTask.get().infoText();
        } else {
            log.warn("Non sono riuscito a creare un oggetto WrapTask per il metodo " + method.getName());
        }

        return info;
    }

    private String[] getColore(int pos) {
        TypeColor color = TypeColor.values()[pos];
        String textColor = isColorDark(color.getEsa()) ? "white" : "black";

        return new String[]{color.getHtml(), textColor};
    }


    private boolean isColorDark(String hexColor) {
        // Rimuovi il simbolo "#" se presente
        hexColor = hexColor.replace("#", "");

        // Estrai i componenti R, G, B dal colore esadecimale
        int r = Integer.parseInt(hexColor.substring(0, 2), 16);
        int g = Integer.parseInt(hexColor.substring(2, 4), 16);
        int b = Integer.parseInt(hexColor.substring(4, 6), 16);

        // Calcola la luminanza con la formula della luminanza percepita
        double luminanza = 0.299 * r + 0.587 * g + 0.114 * b;

        return luminanza < 140; // Se la luminanza è inferiore a 140, il colore è scuro
    }


    protected MongoTemplate getMongoTemplate() {
        return mongoTemplateProvider.getMongoTemplate();
    }

    // forse può andare in AnnotationService o in ReflectionService
    public List<Method> getAnnotatedClazzMethods(Class<?> clazz, Class<? extends Annotation> annotation) {
        List<Method> annotatedMethods = new ArrayList<>();

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                annotatedMethods.add(method);
            }
        }

        return annotatedMethods;
    }


}
