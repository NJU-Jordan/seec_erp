package com.nju.edu.erp.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectionSheetContentPO {
    /**
     * 自增id
     */
    private Integer id;
    /**
     * 收款单id
     */
    private String collectionSheetId;
    /**
     * 银行账户
     */
    private String pid;
    /**
     * 金额
     */
    private BigDecimal totalPrice;
    /**
     * 备注
     */
    private String remark;

    public BigDecimal getTotalPrice() {return totalPrice;
    }
}
