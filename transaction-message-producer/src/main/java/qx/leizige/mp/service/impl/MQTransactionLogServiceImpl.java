package qx.leizige.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qx.leizige.mp.mapper.MQTransactionLogMapper;
import qx.leizige.mp.po.MQTransactionLog;
import qx.leizige.mp.service.MQTransactionLogService;

/**
 * @author leizige
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MQTransactionLogServiceImpl extends ServiceImpl<MQTransactionLogMapper, MQTransactionLog> implements MQTransactionLogService {

    @Override
    public boolean existsMQTransactionLog(String transactionId) {
        return lambdaQuery().eq(MQTransactionLog::getTransactionId, transactionId).count() > 0 ?
                Boolean.TRUE : Boolean.FALSE;
    }


}
