frost
=====

这是一个容器框架

每个目录都是一个单独的项目

每个项目都依赖core

## 已完成项目

### beans 

此项目提供IOC功能

具体用法如下：

```java
  Configuration configuration = new XMLConfigReader().read("application.xml");
  BeanFactory factory = new BeanFactory(configuration);
  new Assembler(configuration,factory).assemble();
```

`application.xml`配置如下：

```xml
<beans>
    <bean id="powerFilter" class="org.frost.DaoFilterSecurityInterceptor">
        <property name="accessDecisionManager" ref="accessDecisionManagerBean" />
        <property name="securityMetadataSource" ref="securityMetadataSourceBean" />
    </bean>
</beans>
```


