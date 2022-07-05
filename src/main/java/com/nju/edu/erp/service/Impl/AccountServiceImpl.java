package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.dao.AccountDao;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.AccountPO;
import com.nju.edu.erp.model.po.ProductPO;
import com.nju.edu.erp.model.po.SaleSheetContentPO;
import com.nju.edu.erp.model.vo.AccountVO;
import com.nju.edu.erp.model.vo.ProductInfoVO;
import com.nju.edu.erp.model.vo.Sale.SaleSheetContentVO;
import com.nju.edu.erp.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao;

    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void createAccount(AccountVO inputVO) {
        AccountPO savePO = new AccountPO();
        BeanUtils.copyProperties(inputVO, savePO);
        accountDao.createAccount(savePO);

    }

    @Override
    public void updateAccount(AccountVO accountVO) {
        AccountPO savePO = new AccountPO();
        BeanUtils.copyProperties(accountVO, savePO);
        int ans = accountDao.updateById(savePO);
        if (ans == 0) {
            throw new MyServiceException("B0003", "更新失败！");
        }
    }

    @Override
    public List<AccountVO> queryAllAccount() {
        List<AccountPO> queryAns = accountDao.findAll();
        List<AccountVO> responseVO = new ArrayList<>();
        for(AccountPO content : queryAns){
            AccountVO accountVO = new AccountVO();
            BeanUtils.copyProperties(content, accountVO);
            responseVO.add(accountVO);
        }
        return responseVO;
    }

    @Override
    public void deleteById(String id) {
        AccountPO accountPO = accountDao.findById(id);
        if (accountPO == null) {
            throw new MyServiceException("B0004", "删除失败！");
        }
        int ans = accountDao.deleteById(id);
        if (ans == 0) {
            throw new MyServiceException("B0004", "删除失败！");
        }
    }

    @Override
    public AccountVO getOneAccountByPid(String id) {
        AccountPO accountPO = accountDao.findById(id);
        AccountVO accountVO = new AccountVO();
        BeanUtils.copyProperties(accountPO, accountVO);
        return accountVO;
    }
}
