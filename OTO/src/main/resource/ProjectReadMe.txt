工程说明文件：
1、src/main/resource下面的对应说明:
app-context-cache.xml        ==> 框架缓存配置文件
app-context-core.xml         ==> 框架核心配置文件
app-context-cxf.xml          ==> 框架webService配置文件
app-context-exception.xml    ==> 框架异常配置文件
app-context-invoker.xml      ==> 框架SpringHttpInvoker使用配置文件（客户端使用）
app-context-mq.xml           ==> 框架异常发送MQ配置文件
app-context-mvc.xml          ==> 框架MVC-web前端配置文件
app-context-persistent.xml   ==> 框架持久层配置文件
app-context-quartz.xml       ==> 框架定时器配置文件
app-context-security-cas.xml ==> 框架的SSO-cas配置文件
app-context-security.xml     ==> 框架的安全配置文件
cfg.properties               ==> 框架的基础属性配置文件
ehcache.xml                  ==> 框架缓存的设置文件
ehcache.xsd                  ==> ehcache.xml的描述文件
ErrorReportMail.vm           ==> 异常发邮件的模板文件
log4j.properties             ==> 日志配置文件
quartz.properties            ==> 定时器配置文件
2、WebRoot下面的目录和结构说明
WebRoot/core/                             ==>web前端的核心目录
WebRoot/core/base.jspf                    ==>.jsp页面的常用taglib、js、css自动引入
WebRoot/core/js/                          ==>web前端工具js包
WebRoot/core/js/fillform.js               ==>自动回填js
WebRoot/core/js/form2json.js              ==>表单转json
WebRoot/core/js/util.js                   ==>常用接口js
WebRoot/core/js/My97DatePicker            ==>日期的js工具
WebRoot/core/js/ref/                      ==>js工具引入
WebRoot/core/js/ref/FusionCharts          ==>图形报表的js
WebRoot/core/js/ref/operamasks-ui-2.1     ==>前端UI工具
WebRoot/core/js/ref/jquery-1.6.3.js       ==>jquery完整版
WebRoot/core/js/ref/jquery-1.6.3.min.js   ==>jQuerymini版
WebRoot/core/js/ref/json2.js              ==>前端json工具
WebRoot/core/js/ref/json2.min.js          ==>前端json工具mini版
WebRoot/core/screencapture/               ==>IE截图工具包
WebRoot/core/screencapture/captrue.js     ==>核心
WebRoot/core/screencapture/jquery.min.js  ==>截图使用的jquery工具包 
WebRoot/core/screencapture/WebCapture.ocx ==>IE控件
WebRoot/uploadDir                         ==>文件上传目录
WebRoot/view                              ==>业务页面存放地址
WebRoot/WEB-INF/applicationContext.xml    ==>spring配置文件
WebRoot/WEB-INF/web.xml                   ==>web配置文件

