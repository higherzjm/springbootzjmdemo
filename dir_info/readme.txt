Redis:
 用指定配置文件启动  redis-server.exe redis.windows.conf
 注册为本地服务 redis-server.exe --service-install redis.windows.conf
 本地密码:123456
mysql:
   本地密码:zjmcat
github：
  账号/密码：353263668@qq.com/Lyh@123456

swagger 打开地址：
http://localhost:8081/swagger-ui.html

本机maven参考:http://localhost:8081/#browse/welcome  admin/admin123

druid:
http://localhost:8081/druid/login.html  admin/123456
#nacos:
http://127.0.0.1:9002/nacos  nacos/nacos
xxljob:http://localhost:8331/xxl-job-admin/toLogin  admin/123456

maven plugins

   <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-source-plugin</artifactId>
                <version>1.0</version>
                <executions>
                    <execution>
                        <id>attach-sources</id>
                        <goals>
                            <goal>jar</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>