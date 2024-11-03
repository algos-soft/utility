package it.algos.utility.role;

import it.algos.vbase.annotation.IViewList;
import it.algos.vbase.list.AList;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(value = SCOPE_PROTOTYPE)
@IViewList()
public class RoleList extends AList {


    /**
     * @param parentCrudView che crea questa istanza
     */
    public RoleList(final RoleView parentCrudView) {
        super(parentCrudView);
    }


    protected void fixPreferenze() {
        super.fixPreferenze();

//        super.usaBottoneEdit = false;
//        super.usaBottoneShow = false;
//        super.usaBottoneExport = false;
    }




}// end of AList class
