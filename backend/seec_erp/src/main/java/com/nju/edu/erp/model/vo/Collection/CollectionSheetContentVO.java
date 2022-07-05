package com.nju.edu.erp.model.vo.Collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectionSheetContentVO {
    /**
     * 自增id, 新建单据时前端传null
     */
    private Integer id;
    /**
     * 进货单id, 新建单据时前端传null
     */
    private String collectionSheetId;
    /**
     * 商品id
     */
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
