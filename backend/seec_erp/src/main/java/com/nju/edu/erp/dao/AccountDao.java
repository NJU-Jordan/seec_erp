package com.nju.edu.erp.dao;

import com.nju.edu.erp.model.po.AccountPO;
import com.nju.edu.erp.model.po.ProductPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface AccountDao {
    int createAccount(AccountPO accountPO);

    int updateById(AccountPO accountPO);

    AccountPO findById(String id);

    List<AccountPO> findAll();

    int deleteById(String id);
}
