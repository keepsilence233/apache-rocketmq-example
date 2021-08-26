package qx.leizige.mp.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author leizige
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_mq_transaction_log")
public class MQTransactionLog extends Model<MQTransactionLog> implements Serializable {

    private Long id;
    private String transactionId;
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private String extInfo;

}
