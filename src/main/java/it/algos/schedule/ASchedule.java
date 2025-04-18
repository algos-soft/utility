package it.algos.schedule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static it.algos.vbase.boot.BaseCost.VUOTA;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: gio, 30-mag-2024
 * Time: 17:38
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface ASchedule {

    String prefCode() default VUOTA;

    int durataMinuti() default 0;


}// end of interface

