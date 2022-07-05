package com.nju.edu.erp.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleScheduleDetailPO {
    /**
     * 销售操作类型，销售或销售退货
     */
    private String type;
    /**
     * 商品名
     */
    private String productName;
    /**
     * 商品型号
     */
    private String productType;
    /**
     * 商品数量
     */
    private Integer quantity;
    /**
     * 商品单价
     */
    private BigDecimal unitPrice;
    /**
     * 商品总价
     */
    private BigDecimal totalPrice;
    /**
     * 出库单/入库单创建时间
     */
    private Date createTime;
}
