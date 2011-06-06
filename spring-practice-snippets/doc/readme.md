# Spring Framework practice - snipptes

## Spring – Usage of @Primary annotation or attribut for inject default bean instances
For using dependency injection with Spring 3.0, several ways exists. You can use the annotation based way with the annotations @Service, @Repository, @Component or the JSR 330 @Named annotation or define the beans in xml with autowiring attributes or last but not least define the beans in a Java configuration. To wire up the injection targets you can use the @Autowired or the JSR 330 @Inject annotation or wire up the target in the XML bean definition.  
This example contains a workaround for SPR-7854 (@Primary and primary attribute of <bean> element are not considered for calls to getBean(Class))

Test class
ch.nydi.spring.inject.primary.PrimarySupportTest

More Information
http://develop.nydi.ch/2010/12/spring-primary-bean-injection/
