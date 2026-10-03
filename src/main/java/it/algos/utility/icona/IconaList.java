package it.algos.utility.icona;

import it.algos.vbase.annotation.clazz.IList;
import it.algos.vbase.button.ABottoni;
import it.algos.vbase.list.AList;
import it.algos.vbase.ui.wrapper.ASpan;

@IList(columns = {"ordine", "vaadinIcon"},
        bottoni = {ABottoni.RESET_DELETE, ABottoni.CREATE_ITEM, ABottoni.EDIT_ITEM, ABottoni.DELETE_ITEM},
        sortProperty = "ordine")
public class IconaList extends AList<IconaEntity> {


    public IconaList(final IconaView parentView) {
        super(parentView);
    }


    @Override
    protected void fixHeader() {
        headerPlaceHolder.add(ASpan.text("Tavola di servizio.").verde().small().bold());
        headerPlaceHolder.add(ASpan.text("Selezione di VaadinIcon da presentare per la creazione delle Funzioni.").blue().small().bold());
    }


}// end of List class



