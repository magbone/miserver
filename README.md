# miserver 

## 1. Introduction.

<strong>Miserver</strong> is a tiny web application, developed by Java. It supports the request methods of GET, POST. Moreover, it is built in the template render [Velocity](http://velocity.apache.org/), which can render behind fastly. Off course, I develop the tool packages including <strong><del>oauth</del> </strong> and <strong> websocket </strong>.

## 2. Documentation.

### 2.1 Before started.

We need some depentdent jars. Please make sure they are in your projects.

> commons-beanutils.jar >= 1.7.0 </br>
  commons-chain.jar >= 1.1 </br>
  commons-collections.jar >=3.2 </br>
  commons-digester.jar >=1.8 </br>
  commons-lang3.jar >= 3.8.1 </br>
  commons-logging.jar >= 1.1 </br>
  commons-validator.jar >=1.3.1 </br>
  dom4j.jar >= 1.1 </br>
  dvsl.jar >= 1.0 </br>
  mysql-connector-java.jar >= 8.0.12 </br>
  oro.jar >= 2.0.8 </br>
  servletapi.jar >= 2.3 </br>
  slf4j-api.jar >= 3.23.1 </br>
  sslext.jar >= 1.2 </br>
  struts-core.jar >= 1.3.8 </br>
  struts-taglib.jar >= 1.3.8 </br>
  struts-tiles.jar >=1.3.8 </br>
  velocity.jar >= 1.5 </br>
  velocity-engine-core.jar >= 2.0 </br>
  velocity-engine-scripting.jar >= 2.0 </br>
  velocity-tools.jar >= 2.0 </br>
  velocity-tools-generic.jar >= 2.0 </br>
  velocity-tools-view.jar >= 1.5 </br>
  
### 2.2 Quick start.

#### 2.2.1 Install.

  Download the <code>.jar</code> package(<strong>Not released</strong>), add it in your projects.

#### 2.2.2 Start new project.

 Create a new project with above jar packages. The <code> Demo.java </code> as follows.
 
 ```java
 
  public class Demo extends AbstractServer {

  @Override
  @RequestUrl (url = "/")
  public void doGet(Request request, Response response)throws IOException {
      response.writeHead("Content-Type:text/html\n");
      response.write("\r\n");
      response.write("hello",maps);
      response.flush();
      response.close();
  }

  @Override
  public void doPost(Request request, Response response) {

  }
}
 
 ```

Create a <code>tempates</code> directory in your project root directory. It is used to store template files.
Create a config file likes <code>ServerConfig.java</code>. The annotation class <code>Config</code> has six params, which one of them <code>templateDir</code> set the template path(<strong>Note</strong>: the path is relative.) 

```java

@Config(templateDir="templates/")
 public interface ServerConfig extends BaseConfig {
}

```

  Create the <code>main.java</code> as the whole application entrance.
  
```java

public class Main {

  public static void main(String args[]){
      MainApplication mainApplication = new  MainApplication();
      Class[] classes = new Class[]{Demo.class};
      mainApplication.setServerClasses(classes);
      mainApplication.setConfig(ServerConfig.class);
      mainApplication.run();
  }
}

```

#### 2.2.3 Run the application

Type the http://localhost:8080 in your browser address input, and then you will see the <code>hello</code> printed in screen.
  
## 3. License.
  [MIT License](LICENSE)
