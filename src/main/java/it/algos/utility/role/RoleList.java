package it.algos.utility.role;

import it.algos.vbase.annotation.IList;
import it.algos.vbase.list.AList;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(value = SCOPE_PROTOTYPE)
@IList()
public class RoleList extends AList {


    /**
     * @param parentCrudView che crea questa istanza
     */
    public RoleList(final RoleView parentCrudView) {
        super(parentCrudView);
    }






}// end of AList class
