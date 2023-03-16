package com.lyh.fund.bean;

import java.math.BigDecimal;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

@Data
public class BuyFund {
    @ExcelProperty("基金 ID")
    private String id;
    @ExcelProperty("基金名")
    private String name;
    @ExcelProperty("买入金额")
    private BigDecimal money;
    @ExcelProperty("买入基金净值")
    private BigDecimal netValue;
    @ExcelProperty("买入时间")
    private String date;
    @ExcelProperty("当前基金净值")
    private BigDecimal todayNetValue;
    @ExcelProperty("当前时间")
    private String todayDate;
    @ExcelProperty("当前收益")
    private BigDecimal revenue;
    @ExcelProperty("当前收益百分比")
    private BigDecimal revenuePercent;
}
