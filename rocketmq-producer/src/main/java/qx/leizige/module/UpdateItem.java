package qx.leizige.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateItem implements
        Serializable {

    private Integer id;
    private String name;
    private BigDecimal price;
}
