package com.lyh.fund.bean;

import java.math.BigDecimal;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

@Data
public class SellFund {
    @ExcelProperty("基金 ID")
    private String id;
    @ExcelProperty("基金名")
    private String name;
    @ExcelProperty("卖出基金净值")
    private BigDecimal money;
    @ExcelProperty("卖出基金净值")
    private BigDecimal netValue;
    @ExcelProperty("卖出时间")
    private String date;
}
