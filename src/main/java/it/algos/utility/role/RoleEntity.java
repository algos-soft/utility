package it.algos.utility.role;

import it.algos.vbase.annotation.IEntity;
import it.algos.vbase.annotation.IFieldList;
import it.algos.vbase.annotation.IReset;
import it.algos.vbase.entity.AbstractEntity;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "role")
@IReset
@IEntity()
public class RoleEntity extends AbstractEntity {

    @IFieldList(headerText = "#")
    private int ordine;


    @Indexed(unique = true)
    @IFieldList(width = 12)
    private String code;


    @Override
    public String toString() {
        return code;
    }


}// end of CrudModel class
