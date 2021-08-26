package qx.leizige.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import qx.leizige.mp.mapper.AccountMapper;
import qx.leizige.mp.po.Account;
import qx.leizige.mp.service.AccountService;

/**
 * @author leizige
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {
}
