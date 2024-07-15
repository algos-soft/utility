package it.algos.utility.role;

import it.algos.vbase.backend.annotation.AList;
import it.algos.vbase.backend.list.CrudList;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(value = SCOPE_PROTOTYPE)
@AList()
public class RoleList extends CrudList {


    //--non utilizzato. Serve SOLO per evitare un bug di IntelliJIDEA che segnala errore.
    public RoleList() {
        super();
    }

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


    @Override
    public void fixHeader() {
//        super.infoScopo = String.format(typeList.getInfoScopo(), "Role", "Role"); ;
        super.fixHeaderPost();
    }


}// end of CrudList class
