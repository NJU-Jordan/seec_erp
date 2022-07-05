package com.nju.edu.erp.service;

import com.nju.edu.erp.enums.sheetState.CollectionSheetState;
import com.nju.edu.erp.enums.sheetState.SaleSheetState;
import com.nju.edu.erp.model.vo.Collection.CollectionSheetVO;
import com.nju.edu.erp.model.vo.Sale.SaleSheetVO;
import com.nju.edu.erp.model.vo.UserVO;

import java.util.Collection;
import java.util.List;

public interface CollectionService {
    /**
     * 指定销售单
     * @param userVO
     * @param collectionSheetVO
     */
    void makeCollectionSheet(UserVO userVO, CollectionSheetVO collectionSheetVO);

    /**
     * 根据单据状态获取销售单
     * @param state
     * @return
     */
    List<CollectionSheetVO> getCollectionSheetByState(CollectionSheetState state);
    /**
     * 审批单据
     * @param collectionSheetId
     * @param state
     */
    void approval(String collectionSheetId, CollectionSheetState state);
    /**
     * 根据销售单Id搜索销售单信息
     * @param collectionSheetId 销售单Id
     * @return 销售单全部信息
     */
    CollectionSheetVO getCollectionSheetById(String collectionSheetId);
}
