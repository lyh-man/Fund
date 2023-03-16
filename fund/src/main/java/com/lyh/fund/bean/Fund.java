package com.lyh.fund.bean;

import java.math.BigDecimal;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

@Data
public class Fund {
    @ExcelProperty("基金 ID")
    private String id;
    @ExcelProperty("基金名")
    private String name;
    @ExcelProperty("当前基金净值")
    private BigDecimal netValue;
    @ExcelProperty("当前时间")
    private String date;
}
