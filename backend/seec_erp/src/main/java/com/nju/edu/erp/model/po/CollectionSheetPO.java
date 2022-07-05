package com.nju.edu.erp.model.po;
import com.nju.edu.erp.enums.sheetState.CollectionSheetState;
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
public class CollectionSheetPO {
    /**
     * 收款单单据编号（格式为：SKD-yyyyMMdd-xxxxx
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
     * 总额
     */
    private BigDecimal TotalAmount;
    /**
     * 单据状态
     */
    private CollectionSheetState state;
    /**
     * 创建时间
     */
    private Date createTime;
}
