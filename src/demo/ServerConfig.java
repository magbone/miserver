package demo;

import server.config.BaseConfig;
import server.config.Config;


@Config(templateDir="demo/templates/" ,port=9078)
public interface ServerConfig extends BaseConfig {
}
