package com.wacai.dropwizard.dubbo;

import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceProvider {

    private final Map<Class<?>, ReferenceConfig<?>> cache = new HashMap<>();

    @JsonProperty("application")
    private final ApplicationConfigFactory applicationConfigFactory = new ApplicationConfigFactory();

    @JsonProperty("registries")
    private final List<RegistryConfigFactory> registryConfigFactories = new ArrayList<>();

    @JsonProperty("consumer")
    private final ConsumerConfigFactory consumerConfigFactory = new ConsumerConfigFactory();

    @SuppressWarnings("unchecked")
    public synchronized <T> T provide(Class<T> klass, Environment environment) {
        ReferenceConfig<T> config = (ReferenceConfig<T>) cache.get(klass);
        if (config == null) {
            config = referenceConfig(klass, environment);
            cache.put(klass, config);
        }
        return config.get();
    }

    private <T> ReferenceConfig<T> referenceConfig(Class<T> klass, Environment environment) {
        final ReferenceConfig<T> conf = new ReferenceConfig<>();
        conf.setInterface(klass);
        conf.setApplication(applicationConfigFactory.create());
        conf.setConsumer(consumerConfigFactory.create());
        conf.setRegistries(Lists.transform(registryConfigFactories, new MapToRegistryConfig()));
        environment.lifecycle().manage(new ManagedReferenceConfig(conf));
        return conf;
    }

    private static class ManagedReferenceConfig implements Managed {
        private final ReferenceConfig<?> conf;

        public ManagedReferenceConfig(ReferenceConfig<?> conf) {this.conf = conf;}

        @Override
        public void start() throws Exception { }

        @Override
        public void stop() throws Exception { conf.destroy(); }
    }

    private static class MapToRegistryConfig implements Function<RegistryConfigFactory, RegistryConfig> {
        @Nullable
        @Override
        public RegistryConfig apply(RegistryConfigFactory input) { return input.create(); }
    }
}
