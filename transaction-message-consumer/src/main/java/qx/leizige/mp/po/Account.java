package qx.leizige.mp.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author leizige
 */
@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_account")
public class Account extends Model<Account> implements Serializable {

    private Long id;
    /* 银行卡号 */
    private String cartNo;
    private String name;
    /* 余额 */
    private BigDecimal balance;
    private LocalDateTime createTime;
}
