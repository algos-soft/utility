package it.algos.utility.nota;

import com.vaadin.flow.data.provider.SortDirection;
import it.algos.vbase.backend.annotation.AViewList;
import it.algos.vbase.backend.list.AList;
import it.algos.vbase.ui.wrapper.ASpan;

@AViewList(columns = {
        "*colore",
        "typeLog",
        "typeLevel",
        "inizio",
        "descrizione",
        "fatto",
        "fine"},
        sortProperty = "inizio", sortDirection = SortDirection.DESCENDING)
public class NotaList extends AList {


    /**
     * @param parentView che crea questa istanza
     */
    public NotaList(final NotaView parentView) {
        super(parentView);
    }


    @Override
    public void fixHeader() {
        super.infoScopo = "Appunti liberi";
        message = "Data iniziale proposta quella attuale ma modificabile. Data finale inserita automaticamente col flag fatto=true.";
        headerPlaceHolder.add(ASpan.text(message).rosso().small());
        message = "Filtri selezione per typeLog e typeLevel. Ordinamento decrescente per data iniziale. Descrizione libera.";
        headerPlaceHolder.add(ASpan.text(message).rosso().small());

        super.fixHeader();
    }


}// end of AList class
