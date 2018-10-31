package server.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Config {
    int port() default 8080;
    int maxConnectCount() default 100;
    String templateDir() default "";
    String page404() default "../response/templates/404html";
}
