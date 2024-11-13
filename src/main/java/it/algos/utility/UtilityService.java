package it.algos.utility;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vbase.logic.ModuloService;
import it.algos.vbase.service.AnnotationService;
import it.algos.vbase.service.ReflectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;

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
                moduliSelezionati.add(unModulo);
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
            if (annotationService.usaResetStartup(modulo.getEntityClass())) {
                modulo.checkResetStartup();
            } else {
                log.info("Il modulo {} non usa {}", modulo.getClass().getSimpleName(), "resetStartup");
            }
        }
    }

}
