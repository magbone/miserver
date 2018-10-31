package demo;

import server.app.sql.mysql.MysqlConfig;
import server.app.sql.mysql.SQLConfig;

@MysqlConfig(host = "127.0.0.1",port = 3365,dbName = "page",user = "root",passwd = "123456")
public interface MysqlConfigDemo extends SQLConfig {
}
