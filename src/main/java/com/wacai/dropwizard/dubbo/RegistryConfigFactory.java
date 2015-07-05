package com.wacai.dropwizard.dubbo;

import com.alibaba.dubbo.config.RegistryConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;

public class RegistryConfigFactory implements ConfigFactory<RegistryConfig> {

    @NotEmpty
    @JsonProperty("address")
    private String address;

    @NotEmpty
    @JsonProperty("protocol")
    private String protocol;

    @JsonProperty("group")
    private String group;

    @JsonProperty("check")
    private boolean check = false;

    @Min(1000)
    @JsonProperty("timeoutMillis")
    private int timeoutMillis = 5000;

    @Override
    public RegistryConfig create() {
        final RegistryConfig conf = new RegistryConfig();
        conf.setAddress(address);
        conf.setProtocol(protocol);
        conf.setCheck(check);
        conf.setTimeout(timeoutMillis);
        if (group != null) conf.setGroup(group);
        return conf;
    }
}
