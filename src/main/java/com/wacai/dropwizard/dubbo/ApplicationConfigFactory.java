package com.wacai.dropwizard.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class ApplicationConfigFactory implements ConfigFactory<ApplicationConfig> {

    @NotEmpty
    @JsonProperty("name")
    private String name;

    @Override
    public ApplicationConfig create() {
        final ApplicationConfig conf = new ApplicationConfig();
        conf.setName(name);
        return conf;
    }
}
