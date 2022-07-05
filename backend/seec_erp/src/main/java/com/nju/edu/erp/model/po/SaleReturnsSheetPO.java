package com.nju.edu.erp.model.po;

import com.nju.edu.erp.enums.sheetState.SaleReturnsSheetState;
import com.nju.edu.erp.enums.sheetState.SaleSheetState;

import java.math.BigDecimal;
import java.util.Date;

public class SaleReturnsSheetPO {
    public SaleReturnsSheetState getState() {
        return state;
    }

    public String getSalesman() {
        return salesman;
    }

    public String getSaleSheetId() {
        return saleSheetId;
    }

    /**
     * 销售单单据编号（格式为：XSD-yyyyMMdd-xxxxx
     */
    private String id;
    /**
     * 关联的进货单id
     */
    private String saleSheetId;
    /**
     * 客户/销售商id
     */
    private Integer supplier;
    /**
     * 业务员
     */
    private String salesman;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 备注
     */
    private String remark;
    /**
     * 折让前总额
     */
    private BigDecimal rawTotalAmount;
    /**
     * 折让
     */
    private BigDecimal discount;
    /**
     * 使用代金券总额
     */
    private BigDecimal voucherAmount;
    /**
     * 折让后总额
     */
    private BigDecimal finalAmount;
    /**
     * 单据状态
     */
    private SaleReturnsSheetState state;
    /**
     * 创建时间
     */
    private Date createTime;

    public void setId(String id) {
        this.id = id;
    }

    public void setSupplier(Integer supplier) {
        this.supplier = supplier;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setRawTotalAmount(BigDecimal rawTotalAmount) {
        this.rawTotalAmount = rawTotalAmount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public void setVoucherAmount(BigDecimal voucherAmount) {
        this.voucherAmount = voucherAmount;
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

    public void setState(SaleReturnsSheetState state) {
        this.state = state;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }
}
