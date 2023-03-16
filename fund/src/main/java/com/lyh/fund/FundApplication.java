package com.lyh.fund;

import com.lyh.fund.api.FundOperation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FundApplication {

    public static void main(String[] args) {
        // SpringApplication.run(FundApplication.class, args);
        FundOperation.writeBuyFundInfo();
    }

}
