package qx.leizige.mp.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import qx.leizige.mp.JsonTypeHandler;

import java.io.Serializable;

/**
 * @author leizige
 */
@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_mq_transaction_log")
public class MQTransactionLog extends Model<MQTransactionLog> implements Serializable {

    private Long id;
    private String transactionId;
    @TableField(typeHandler = JsonTypeHandler.class)
    private String extInfo;

}
