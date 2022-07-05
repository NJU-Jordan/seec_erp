package com.nju.edu.erp.service;

import com.nju.edu.erp.model.vo.AccountVO;
import com.nju.edu.erp.model.vo.CreateProductVO;
import com.nju.edu.erp.model.vo.ProductInfoVO;

import java.util.List;

public interface AccountService {
    /**
     * 新增一个账户
     * @param inputVO
     * @return accountVO
     */
    void createAccount(AccountVO inputVO);

    /**
     * 修改商品信息
     * @param accountVO
     * @return accountVO
     */
   void updateAccount(AccountVO accountVO);

    /**
     * 获取全部商品信息
     */
    List<AccountVO> queryAllAccount();

    /**
     * 删除某商品
     * @param id
     */
    void deleteById(String id);

    /**
     * 通过id获取商品详情
     * @param id 商品id
     * @return 商品详情
     */
    AccountVO getOneAccountByPid(String id);
}
