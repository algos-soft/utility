package it.algos.utility.wamicona;

import it.algos.vbase.annotation.IList;
import it.algos.vbase.constant.Bottone;
import it.algos.vbase.list.AList;
import it.algos.vbase.ui.wrapper.ASpan;

@IList(columns = {"ordine", "vaadinIcon"},
        bottoni = {Bottone.RESET_DELETE, Bottone.CREATE_ITEM, Bottone.EDIT_ITEM, Bottone.DELETE_ITEM},
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



