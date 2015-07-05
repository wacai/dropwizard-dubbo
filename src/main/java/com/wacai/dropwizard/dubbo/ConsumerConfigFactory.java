package com.wacai.dropwizard.dubbo;

import com.alibaba.dubbo.config.ConsumerConfig;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;

public class ConsumerConfigFactory implements ConfigFactory<ConsumerConfig> {
    @JsonProperty("check")
    private boolean check = false;

    @JsonProperty("timeoutMillis")
    @Min(1000)
    private int timeoutMillis = 5000;

    @Override
    public ConsumerConfig create() {
        final ConsumerConfig conf = new ConsumerConfig();
        conf.setCheck(check);
        conf.setTimeout(timeoutMillis);
        return conf;
    }
}
