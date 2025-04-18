package it.algos.utility.boot;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vbase.annotation.IMenu;
import it.algos.vbase.boot.BaseBoot;
import it.algos.vbase.constant.Gruppo;
import it.algos.vbase.security.SecurityService;
import it.algos.vbase.service.LayoutService;
import it.algos.vbase.tree.TreeNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class UtilityBoot extends BaseBoot {


    public void inizia() {
    }












}
