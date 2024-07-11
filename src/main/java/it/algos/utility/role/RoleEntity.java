package it.algos.utility.role;

import it.algos.vbase.backend.annotation.AEntity;
import it.algos.vbase.backend.annotation.AFieldList;
import it.algos.vbase.backend.annotation.AReset;
import it.algos.vbase.backend.entity.AbstractEntity;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "role")
@AReset
@AEntity()
public class RoleEntity extends AbstractEntity {

    @AFieldList(headerText = "#")
//    @AField(type = TypeField.ordine)
    private int ordine;


    @Indexed(unique = true)
    @AFieldList(width = 12)
//    @AField(type = TypeField.text)
    private String code;


    @Override
    public String toString() {
        return code;
    }


}// end of CrudModel class
