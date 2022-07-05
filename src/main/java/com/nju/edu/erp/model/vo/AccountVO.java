package com.nju.edu.erp.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountVO {
    /**
     * 账户名称
     */
    private String id;
    /**
     * 账户名称
     */
    private String name;
    /**
     *  总额
     */
    private BigDecimal accountBalance;
}
