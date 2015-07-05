## Installation

```xml
<dependency>
  <groupId>com.wacai</groupId>
  <artifactId>dropwizard-dubbo</artifactId>
  <version>0.1.0-SNAPSHOT</version>
</dependency>

<!-- Optional -->
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>2.1.0</version>
</dependency>

```



## Usage

```java

public class YourConfiguration extends Configuration {
  @JsonProperty("dubbo")
  private ServiceProvider serviceProvider = new ServiceProvider();

  public ServiceProvider getServiceProvider() { return serviceProvider; }

}

public class YourApplication extends Application {

  @Override
  public void run(ManagementConfiguration conf, Environment environment) throws Exception {
    YourService ys = conf.getServiceProvider.provide(YourService.class, environment);
  }

}
```

## Configuration

```yaml
dubbo:
  application:
    name: your_application
  registries:
    - address: dubbo.registry:12306
      protocol: redis
      check: false
      timeoutMillis: 5000
  consumer:
    check: false
    timeoutMillis: 5000
```

