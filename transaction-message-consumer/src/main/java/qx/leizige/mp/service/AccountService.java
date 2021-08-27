package qx.leizige.mp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import qx.leizige.mp.po.Account;

import java.math.BigDecimal;

/**
 * @author leizige
 */
public interface AccountService extends IService<Account> {


    void addBalanceByCardNo(String cardNo, BigDecimal balance );

}
