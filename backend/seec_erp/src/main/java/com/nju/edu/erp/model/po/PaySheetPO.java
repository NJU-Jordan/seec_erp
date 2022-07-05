package com.nju.edu.erp.model.po;

import java.math.BigDecimal;
import java.util.Date;

public class PaySheetPO {
    /**
     * 付款单单据编号（格式为：SKD-yyyyMMdd-xxxxx
     */
    private String id;
    /**
     * 客户/销售商/供应商id
     */
    private Integer supplier;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 备注
     */
    private String remark;
    /**
     * 银行账户
     */
    private String pid;
    /**
     * 总额
     */
    private BigDecimal TotalAmount;
    /**
     * 单据状态
     */
    private PaySheetState state;
    /**
     * 创建时间
     */
    private Date createTime;
}
