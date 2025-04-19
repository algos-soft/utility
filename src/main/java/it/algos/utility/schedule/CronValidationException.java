package it.algos.utility.schedule;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project utility
 * Created by Algos
 * User: gac
 * Date: sab, 19-apr-2025
 * Time: 07:57
 */
public class CronValidationException extends Exception {
    public CronValidationException(String message) {
        super(message);
    }
}
