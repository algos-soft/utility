package it.algos.utility.service;

import it.algos.vbase.entity.AbstractEntity;
import it.algos.vbase.service.ReflectionService;
import it.algos.vbase.service.TextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static it.algos.vbase.boot.BaseCost.SUFFIX_ENTITY;
import static org.junit.Assert.assertNotNull;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: gio, 07-nov-2024
 * Time: 09:42
 */
@Slf4j
@Service
public class TestService {

    @Autowired
    private ReflectionService reflectionService;

    @Autowired
    private TextService textService;


    public List<String> getSubClazzEntityString() {
        List<Class<? extends AbstractEntity>> listaEntityClazz = reflectionService.getSubClazzEntity();
        assertNotNull(listaEntityClazz);

        List<String> stringArray = listaEntityClazz
                .stream()
                .map(clazz -> textService.levaCoda(clazz.getSimpleName(), SUFFIX_ENTITY))
                .collect(Collectors.toCollection(ArrayList::new));
        assertNotNull(stringArray);
        Collections.sort(stringArray);

        return stringArray;
    }

}


