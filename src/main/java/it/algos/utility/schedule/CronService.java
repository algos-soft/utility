package it.algos.utility.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static it.algos.vbase.boot.BaseCost.VUOTA;

@Slf4j
@Service
public class CronService {


    public String info(String expression) {
        try {
            return CronUtils.descriviCron(expression);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return VUOTA;
        }
    }


}

