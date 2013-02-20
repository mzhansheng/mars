
1、下载web和plugins目录，其中web目录下的mars是web工程也是插件工程，plugins目录下存放所有的插件（含客户端、公共和服务器端插件），
   web/plugins目录上仅是作为web服务才会用到的插件。
2、eclipse设置编码为utf-8，jdk统一设置为1.6以上（服务器端也是1.6），配置server为tomcat7
3、导入mars工程
4、打开mars下的mars-all.target，点击工作区的右上角set as target platform按钮，设置其为当前workspace的target-platform
5、导入plugins下的工程
6、确认导入的工程都没有报错

A）单机运行模式
   1、打开com.seekon.smartclient.launcher.desktop的desktop.product文件，并运行
   2、启动了智能客户端并弹出登录窗口，用户名：admin，没有密码，登录
   3、若登录成功说明正常启动运行。

B）c/s/s运行模式
   注意：需安装默认数据库：mysql，并将sql备份脚本导入到mysql数据库中
   1、右击mares工程，在tomcat中运行
   2、打开com.seekon.smartclient.launcher.desktop的smartclient.product文件，并运行
   3、启动了智能客户端并弹出登录窗口，用户名：admin，没有密码，登录
   4、若登录成功说明正常启动运行。

C）b/s/s运行模式
   注意：需安装默认数据库：mysql，并将sql备份脚本导入到mysql数据库中
	使用的方式是浏览器中内嵌applet的方式
