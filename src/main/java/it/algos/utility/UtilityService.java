package it.algos.utility;

import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vbase.logic.ModuloService;
import it.algos.vbase.service.AnnotationService;
import it.algos.vbase.service.ReflectionService;
import it.algos.wiki24.backend.annotation.ASchedule;
import it.algos.wiki24.backend.enumeration.WPref;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
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
    public AnnotationService annotationService;

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
            modulo.checkResetStartup();
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

    public Optional<String> getCron(@NonNull Method method) {
        Optional<Scheduled> annotation = getOptionalAnnotation(method, Scheduled.class);
        return annotation.map(Scheduled::cron).filter(StringUtils::hasText);
    }


    public String getCron(String cronExpression) {
        // Definisci il parser per il formato cron Unix
        CronParser parser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ));
        Cron cron = parser.parse(cronExpression);

        // Ottieni una descrizione in italiano
        CronDescriptor descriptor = CronDescriptor.instance();
        String description = descriptor.describe(cron);

        return description;
    }


    public Optional<String> getCronText(@NonNull Method method) {
        Optional<String> optCron = getCron(method);
        return optCron.isPresent() ? Optional.ofNullable(getCron(optCron.get())) : Optional.empty();
    }


    public Optional<String> getCronInfo(@NonNull Class<?> clazz, @NonNull String methodName) {
        String clazzName = clazz.getSimpleName();
        Method method;

        try {
            method = clazz.getMethod(methodName);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        String cron = getCronText(method).orElse(VUOTA);
        Optional<String> optPrefCode = getPrefCode(method);
        String description = optPrefCode.isPresent() ? WPref.valueOf(optPrefCode.get()).getDescrizione() : VUOTA;
        ;

        String message = String.format("Task: %s.%s() - %s %s", clazzName, methodName, description, cron);
        return Optional.of(message);
    }

    public Optional<String> getCronInfo(@NonNull Method method) {
        String clazzName = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();
        String cron = getCronText(method).orElse(VUOTA);
        Optional<String> optPrefCode = getPrefCode(method);
        String description = optPrefCode.isPresent() ? WPref.valueOf(optPrefCode.get()).getDescrizione() : VUOTA;

        String message = String.format("Task: %s.%s() - %s %s", clazzName, methodName, description, cron);
        return Optional.of(message);
    }

    public void logCronInfo() {
        List<Method> methods = annotationService.getAnnotatedMethods(Scheduled.class);

        for (Method method : methods) {
            Optional<String> otpCronInfo = getCronInfo(method);
            log.info(otpCronInfo.isPresent() ? otpCronInfo.get() : VUOTA);
        }
    }

}
