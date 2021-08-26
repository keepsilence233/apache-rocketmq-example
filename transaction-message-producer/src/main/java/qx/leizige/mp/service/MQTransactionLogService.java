package qx.leizige.mp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import qx.leizige.mp.po.MQTransactionLog;

/**
 * @author leizige
 */
public interface MQTransactionLogService extends IService<MQTransactionLog> {


    boolean existsMQTransactionLog(String transactionId);

}
