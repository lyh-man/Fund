package com.lyh.fund.api;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.lyh.fund.bean.BuyFund;
import com.lyh.fund.bean.Fund;
import com.lyh.fund.listener.FundListener;
import com.lyh.fund.util.HttpClientUtil;

public class FundOperation {
    // http://fundsuggest.eastmoney.com/FundSearch/api/FundSearchAPI.ashx?m=1&key=011513
    private static final String GET_FUND_FROM_ID = "http://fundsuggest.eastmoney.com/FundSearch/api/FundSearchAPI.ashx";

    // sourceFundFilePath
    private static String sourceFundFilePath = System.getenv("sourceFundFilePath") != null ? System.getenv("sourceFundFilePath") : "fund.xlsx";

    // descFundFilePath
    private static String descFundFilePath = System.getenv("descFundFilePath") != null ? System.getenv("descFundFilePath") : "fund-new.xlsx";

    private static List readSheet(String path, Class tClass, String sheetName) {
        return EasyExcel.read(path, tClass, new FundListener()).sheet(sheetName).doReadSync();
    }

    private static void writeSheet(String path, Class tClass, String sheetName, List fundList) {
        EasyExcel.write(path, tClass).sheet(sheetName).doWrite(fundList);
    }

    public static List<Fund> getFundInfo() {
        // 获取基金列表
        List<Fund> fundList = readSheet(sourceFundFilePath, Fund.class, "fund");
        System.out.println(fundList);

        // 根据基金 ID，更新 基金净值
        for (Fund fund: fundList) {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new NameValuePair("m", "1"));
            params.add(new NameValuePair("key", fund.getId()));
            String response = HttpClientUtil.doPost(GET_FUND_FROM_ID, params.toArray(new NameValuePair[params.size()]));

            JSONObject result = JSONObject.parseObject(response);
            JSONObject datas = result.getJSONArray("Datas").getJSONObject(0);
            JSONObject fundBaseInfo = datas.getJSONObject("FundBaseInfo");

            String todayDate = fundBaseInfo.getString("FSRQ");
            String todayNetValue = fundBaseInfo.getString("DWJZ");

            fund.setDate(todayDate);
            fund.setNetValue(new BigDecimal(todayNetValue));
        }

        // 返回基金信息
        return fundList;
    }

    public static List getBuyFundInfo() {
        // 获取买入基金信息
        List<BuyFund> buyFundList = readSheet(sourceFundFilePath, BuyFund.class, "buy-fund");
        System.out.println(buyFundList);

        // 获取基金信息
        List<Fund> fundList = getFundInfo();
        Map<String, Fund> map = new HashMap<>();
        for (Fund fund : fundList) {
            map.put(fund.getId(), fund);
        }
        System.out.println(fundList);

        // 更新买入基金收益
        for (BuyFund buyFund : buyFundList) {
            Fund fund = map.get(buyFund.getId());
            buyFund.setTodayDate(fund.getDate());
            buyFund.setTodayNetValue(fund.getNetValue());

            BigDecimal revenue = fund.getNetValue().subtract(buyFund.getNetValue()).divide(buyFund.getNetValue(), 4, RoundingMode.CEILING);
            buyFund.setRevenue(revenue.multiply(buyFund.getMoney()));
            buyFund.setRevenuePercent(revenue.multiply(new BigDecimal(100)));
        }
        System.out.println(buyFundList);

        // 返回基金信息
        return buyFundList;
    }

    public static void writeFundInfo() {
        writeSheet(descFundFilePath, Fund.class, "fund-net-value", getFundInfo());
    }

    public static void writeBuyFundInfo() {
        writeSheet(descFundFilePath, BuyFund.class, "buy-fund-income", getBuyFundInfo());
    }
}
