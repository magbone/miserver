package server.app.sql.mysql;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MysqlConfig {
    String host();
    int port();
    String dbName();
    String user();
    String passwd();
}
