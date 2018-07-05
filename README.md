## GOLO Service Monitoring Tool
### Build and run the service

* clone the project
```$xslt
git clone https://github.com/eshen89/server_monitoring.git
```
* navigate to the project folder then build with maven
```$xslt
mvn clean install
mvn spring-boot:run
```
* Following message indicates that server is running properly
```$xslt
[INFO] Scanning for projects...
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] Building server-monitoring 1.0-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO]
[INFO] >>> spring-boot-maven-plugin:2.0.3.RELEASE:run (default-cli) > test-compile @ server-monitoring >>>
[INFO]
[INFO] --- maven-resources-plugin:3.0.1:resources (default-resources) @ server-monitoring ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 1 resource
[INFO]
[INFO] --- maven-compiler-plugin:3.7.0:compile (default-compile) @ server-monitoring ---
[INFO] Nothing to compile - all classes are up to date
[INFO]
[INFO] --- maven-resources-plugin:3.0.1:testResources (default-testResources) @ server-monitoring ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory E:\workspace\server_monitoring\src\test\resources
[INFO]
[INFO] --- maven-compiler-plugin:3.7.0:testCompile (default-testCompile) @ server-monitoring ---
[INFO] Nothing to compile - all classes are up to date
[INFO]
[INFO] <<< spring-boot-maven-plugin:2.0.3.RELEASE:run (default-cli) < test-compile @ server-monitoring <<<
[INFO]
[INFO]
[INFO] --- spring-boot-maven-plugin:2.0.3.RELEASE:run (default-cli) @ server-monitoring ---

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.0.3.RELEASE)
```
Using the services
------
##### Start the service
`GET /monitor/start?interval=&url=`

##### Fields
Name | Description | Type
--- | --- | ---
*interval* | The time interval that require monitor service to call remote API. The time unit is in minute. | **int**
*url* | The remote host that service should monitor. Currently support: `production` `debugging_proxy` `mock_server` | **string**

##### Http Error code
Status_code | Description
--- | ---
*200* | Service is processed, everyone is happy. 
*403* | A monitoring job is currently running, new `start` request will not process.
*400* | Bad request, check the request parameter's field and type.

##### Example
```$xslt
PS C:\Users\sy818> curl "http://localhost:8080/monitor/start?interval=1&url=production"


StatusCode        : 200
StatusDescription :
Content           : Service is running now.
RawContent        : HTTP/1.1 200
                    Content-Length: 23
                    Content-Type: text/plain;charset=UTF-8
                    Date: Thu, 05 Jul 2018 00:52:09 GMT

                    Service is running now.
Forms             : {}
Headers           : {[Content-Length, 23], [Content-Type, text/plain;charset=UTF-8], [Date, Thu, 05 Jul 2018 00:52:09
                    GMT]}
Images            : {}
InputFields       : {}
Links             : {}
ParsedHtml        : mshtml.HTMLDocumentClass
RawContentLength  : 23
```
##### Stop the service
`GET /monitor/stop`
##### Example
```$xslt
PS C:\Users\sy818> curl "http://localhost:8080/monitor/stop"


StatusCode        : 200
StatusDescription :
Content           : Service is stopped.
RawContent        : HTTP/1.1 200
                    Content-Length: 19
                    Content-Type: text/plain;charset=UTF-8
                    Date: Thu, 05 Jul 2018 00:55:05 GMT

                    Service is stopped.
Forms             : {}
Headers           : {[Content-Length, 19], [Content-Type, text/plain;charset=UTF-8], [Date, Thu, 05 Jul 2018 00:55:05 GMT]}
Images            : {}
InputFields       : {}
Links             : {}
ParsedHtml        : mshtml.HTMLDocumentClass
RawContentLength  : 19
```
##### Display an overview of the monitoring history
`GET /monitor/overview`
##### Example
```$xslt
PS C:\Users\sy818> curl "http://localhost:8080/monitor/overview"


StatusCode        : 200
StatusDescription :
Content           : [{"id":1,"host_url":"https://api.test.paysafe.com/accountmanagement/monitor","server_status":"{\"st
                    atus\":\"READY\"}","last_checked":"2018-07-05T00:52:10.166+0000"}]
RawContent        : HTTP/1.1 200
                    Transfer-Encoding: chunked
                    Content-Type: application/json;charset=UTF-8
                    Date: Thu, 05 Jul 2018 00:52:26 GMT

                    [{"id":1,"host_url":"https://api.test.paysafe.com/accountmanagement/moni...
Forms             : {}
Headers           : {[Transfer-Encoding, chunked], [Content-Type, application/json;charset=UTF-8], [Date, Thu, 05 Jul
                    2018 00:52:26 GMT]}
Images            : {}
InputFields       : {}
Links             : {}
ParsedHtml        : mshtml.HTMLDocumentClass
RawContentLength  : 165
```
```$xslt
[
    {
        "id": 1,
        "host_url": "https://api.test.paysafe.com/accountmanagement/monitor",
        "server_status": "{\"status\":\"READY\"}",
        "last_checked": "2018-07-05T00:52:10.166+0000"
    },
    {
        "id": 2,
        "host_url": "https://api.test.paysafe.com/accountmanagement/monitor",
        "server_status": "{\"status\":\"READY\"}",
        "last_checked": "2018-07-05T00:53:09.415+0000"
    },
    {
        "id": 3,
        "host_url": "https://api.test.paysafe.com/accountmanagement/monitor",
        "server_status": "{\"status\":\"READY\"}",
        "last_checked": "2018-07-05T00:54:09.330+0000"
    },
    {
        "id": 4,
        "host_url": "https://api.test.paysafe.com/accountmanagement/monitor",
        "server_status": "{\"status\":\"READY\"}",
        "last_checked": "2018-07-05T01:33:29.374+0000"
    }
]
```