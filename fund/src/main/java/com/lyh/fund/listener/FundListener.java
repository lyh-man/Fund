package com.lyh.fund.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

public class FundListener<T> extends AnalysisEventListener<T> {

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }
}
