package com.nju.edu.erp.dao;

import com.nju.edu.erp.enums.sheetState.CollectionSheetState;
import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import com.nju.edu.erp.model.po.CollectionSheetContentPO;
import com.nju.edu.erp.model.po.CollectionSheetPO;
import com.nju.edu.erp.model.po.SaleSheetContentPO;
import com.nju.edu.erp.model.po.SaleSheetPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
@Mapper
public interface CollectionSheetDao {
    /**
     * 把销售单上的具体内容存入数据库
     * @param collectionSheetContent 入销售单上的具体内容
     */
    int saveBatchSheetContent(List<CollectionSheetContentPO> collectionSheetContent);
    /**
     * 获取最近一条收款单
     * @return
     */
    CollectionSheetPO getLatestSheet();
    /**
     * 存入一条收款单记录
     * @param toSave 一条销售单记录
     * @return 影响的行数
     */
    int saveSheet(CollectionSheetPO toSave);
    /**
     * 查找所有收款单
     */
    List<CollectionSheetPO> findAllSheet();
    /**
     * 查找指定状态的收款单
     * @param
     * @return
     */
    List<CollectionSheetPO> findAllByState(CollectionSheetState state);
    /**
     * 查找指定id的销售单
     * @param
     * @return
     */
    CollectionSheetPO findSheetById(String id);
    /**
     * 查找指定收款单下具体的转账内容
     * @param sheetId
     */
    List<CollectionSheetContentPO> findContentBySheetId(String sheetId);
    /**
     * 更新指定收款单的状态
     * @param sheetId
     * @param state
     * @return
     */
    int updateSheetState(String sheetId, CollectionSheetState state);
    /**
     * 根据当前状态更新销售单状态
     * @param sheetId
     * @param prev
     * @param state
     * @return
     */
    int updateSheetStateOnPrev(String sheetId, CollectionSheetState prev, CollectionSheetState state);

}
