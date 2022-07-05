package com.nju.edu.erp.model.vo.SaleReturns;

import java.math.BigDecimal;

public class SaleReturnsSheetContentVO {
    /**
     * 商品id
     */
    private String pid;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 金额
     */
    private BigDecimal totalPrice;
    /**
     * 备注
     */
    private String remark;
}
