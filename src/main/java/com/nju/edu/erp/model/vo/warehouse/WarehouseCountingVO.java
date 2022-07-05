package com.nju.edu.erp.model.vo.warehouse;

import com.nju.edu.erp.model.vo.ProductInfoVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WarehouseCountingVO {
    public Integer getId() {
        return id;
    }

    public ProductInfoVO getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    /**
     * 库存id
     */
    private Integer id;

    /**
     * 商品编号
     */
    private ProductInfoVO product;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 进价
     */
    private BigDecimal purchasePrice;

    /**
     * 批次
     */
    private Integer batchId;

    /**
     * 出厂日期
     */
    private Date productionDate;
}
