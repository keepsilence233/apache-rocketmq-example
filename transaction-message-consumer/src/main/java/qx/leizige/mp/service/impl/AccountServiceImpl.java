package qx.leizige.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qx.leizige.mp.mapper.AccountMapper;
import qx.leizige.mp.po.Account;
import qx.leizige.mp.service.AccountService;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author leizige
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {
    @Override
    public void addBalanceByCardNo(String cardNo, BigDecimal balance) {
        Account account = lambdaQuery().eq(Account::getCartNo, cardNo).one();
        if (Objects.nonNull(account)) {
            balance = account.getBalance().add(balance);
            lambdaUpdate().set(Account::getBalance, balance).eq(Account::getCartNo, cardNo).update();
        }
    }
}
