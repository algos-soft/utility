package it.algos.utility.nota;

import com.vaadin.flow.data.provider.SortDirection;
import it.algos.vbase.annotation.IList;
import it.algos.vbase.constant.Bottone;
import it.algos.vbase.list.AList;
import it.algos.vbase.ui.wrapper.ASpan;

@IList(bottoni = {
        Bottone.CREATE_ITEM,
        Bottone.EDIT_ITEM,
        Bottone.DELETE_ITEM},
        columns = {"typeLog",
                "typeLevel",
                "inizio",
                "descrizione",
                "fatto",
                "fine"},
        sortProperty = "inizio", sortDirection = SortDirection.DESCENDING)
public class NotaList extends AList<NotaEntity> {


    /**
     * @param parentView che crea questa istanza
     */
    public NotaList(final NotaView parentView) {
        super(parentView);
    }


    @Override
    public void fixHeader() {
        super.infoScopo = "Appunti liberi";

        super.fixHeader();
        message = "Data iniziale proposta quella attuale, ma modificabile. Data finale inserita automaticamente col flag fatto=true.";
        headerPlaceHolder.add(ASpan.text(message).rosso().small());
        message = "Filtri selezione per typeLog e typeLevel. Ordinamento decrescente per data iniziale. Descrizione libera.";
        headerPlaceHolder.add(ASpan.text(message).rosso().small());
    }


}// end of AList class
