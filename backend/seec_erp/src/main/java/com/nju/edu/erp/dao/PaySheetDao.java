package com.nju.edu.erp.dao;

import com.nju.edu.erp.enums.sheetState.CollectionSheetState;
import com.nju.edu.erp.enums.sheetState.PaySheetState;
import com.nju.edu.erp.model.po.CollectionSheetContentPO;
import com.nju.edu.erp.model.po.CollectionSheetPO;
import com.nju.edu.erp.model.po.PaySheetContentPO;
import com.nju.edu.erp.model.po.PaySheetPO;

import java.util.List;

public interface PaySheetDao {
    /**
     * 把付款单上的具体内容存入数据库
     * @param paySheetContent 入付款单上的具体内容
     */
    int saveBatchSheetContent(List<PaySheetContentPO> paySheetContent);
    /**
     * 获取最近一条付款单
     * @return
     */
    PaySheetPO getLatestSheet();
    /**
     * 存入一条付款单记录
     * @param toSave 一条付款单记录
     * @return 影响的行数
     */
    int saveSheet(PaySheetPO toSave);
    /**
     * 查找所有付款单
     */
    List<PaySheetPO> findAllSheet();
    /**
     * 查找指定状态的付款单
     * @param
     * @return
     */
    List<PaySheetPO> findAllByState(PaySheetState state);
    /**
     * 查找指定id的付款单
     * @param
     * @return
     */
    PaySheetPO findSheetById(String id);
    /**
     * 查找指定付款单下具体的转账内容
     * @param sheetId
     */
    List<PaySheetContentPO> findContentBySheetId(String sheetId);
    /**
     * 更新指定付款单的状态
     * @param sheetId
     * @param state
     * @return
     */
    int updateSheetState(String sheetId, PaySheetState state);
    /**
     * 根据当前状态更新付款单状态
     * @param sheetId
     * @param prev
     * @param state
     * @return
     */
    int updateSheetStateOnPrev(String sheetId, PaySheetState prev, PaySheetState state);
}
