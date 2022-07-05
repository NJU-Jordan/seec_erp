package com.nju.edu.erp.service;


import com.nju.edu.erp.enums.sheetState.PaySheetState;
import com.nju.edu.erp.model.vo.Collection.CollectionSheetVO;
import com.nju.edu.erp.model.vo.Pay.PaySheetVO;
import com.nju.edu.erp.model.vo.UserVO;

import java.util.List;

public interface PayService {
    /**
     * 指定付款单
     * @param userVO
     * @param paySheetVO
     */
    void makePaySheet(UserVO userVO, PaySheetVO paySheetVO);

    /**
     * 根据单据状态获取销售单
     * @param state
     * @return
     */
    List<CollectionSheetVO> getPaySheetByState(PaySheetState state);
    /**
     * 审批单据
     * @param paySheetId
     * @param state
     */
    void approval(String paySheetId, PaySheetState state);
    /**
     * 根据付款单Id搜索付款单信息
     * @param paySheetId 付款单Id
     * @return 付款单全部信息
     */
    CollectionSheetVO getPaySheetById(String paySheetId);
}
