package com.nju.edu.erp.model.po;

import java.math.BigDecimal;

public class SaleReturnsSheetContentPO {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSaleReturnsSheetId() {
        return saleReturnsSheetId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setSaleReturnsSheetId(String saleReturnsSheetId) {
        this.saleReturnsSheetId = saleReturnsSheetId;
    }

    /**
     * 自增id
     */
    private Integer id;
    /**
     * 销售单id
     */
    private String saleReturnsSheetId;
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
     * 总金额
     */
    private BigDecimal totalPrice;
    /**
     * 备注
     */
    private String remark;
}
