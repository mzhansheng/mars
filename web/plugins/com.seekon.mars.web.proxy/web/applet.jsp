<%@page import="com.seekon.mars.web.proxy.util.WebParameterUtils"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashSet"%>
<%@page import="javax.servlet.ServletContext"%>
<%@page import="com.seekon.mars.web.proxy.util.FileUtils"%>

<%
	String scheme = request.getScheme();
	String ip = request.getServerName();
	int port = request.getServerPort();

	String webRoot = scheme + "://" + ip + ":" + port + "/mars";

	Set<String> archiveSet = new HashSet<String>();
	archiveSet.add("org.eclipse.osgi_3.6.2.R36x_v20110210.jar");
	archiveSet.add("com.seekon.mars.equinox.extend_6.0.2.jar");
	archiveSet.add("com.seekon.smartclient.launcher.web_1.0.0.jar");
	archiveSet.add("com.seekon.smartclient.loader_1.0.0.jar");

	String archives = archiveSet.toString();
	archives = archives.substring(1, archives.length() - 1);

	boolean packEnabled = false;
	String systemBundles = WebParameterUtils.getSystemBundleNames(
			packEnabled, request.getSession().getServletContext());
		
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>applet client</title>
<style type="text/css" media="screen">
html,body {
	height: 100%;
}

body {
	margin: 0;
	padding: 0;
	overflow: auto;
	text-align: center;
	background-color: #ffffff;
}
</style>

<script LANGUAGE="JavaScript">
		function findMainApplet(){
			if(document.all){
				return mainApplet;
			}else{
				return mainApplet[1];
			}
		}
		function removeMe(){
			findMainApplet().destroy();
		}
		function resize(){
 			var applet = findMainApplet();
			if(applet){
				var w = document.body.clientWidth;
				var h = document.body.clientHeight;
				applet.width = w;
				applet.height = h;
			}
		}
		function doload(){
			resize();
		}
	</script>
</head>
<body onload="doload()" onbeforeunload="removeMe()" onresize="resize()">
	<object classid="clsid:CAFEEFAC-0016-0000-0026-ABCDEFFEDCBA"
		codebase="<%=webRoot%>/download/java/jre6u26.CAB#Version=6,0,26,3"
		WIDTH="100%" HEIGHT="100%" name="mainApplet" id="mainApplet"
		style="top: 0; left: 0; position: absolute;">
		<param name="java_arguments"
			value="-XX:PermSize=48m -Xms128m -Xmx256m -Djnlp.packEnabled=<%=packEnabled%>">
		<PARAM NAME="java_code"
			VALUE="com.seekon.smartclient.launcher.web.internal.JOSGiApplet.class">
		<PARAM NAME=CODEBASE VALUE="<%=webRoot%>/client/plugins">
		<PARAM NAME="java_archive" VALUE="<%=archives%>">
		<param name="type"
			value="application/x-java-applet;jpi-version=1.6.0_12">
		<param name="scriptable" value="true">
		<param name="sessionId" value="<%=request.getSession().getId()%>">
		<param name="webRoot" value="<%=webRoot%>">
		<%
			StringBuffer parameterNames = new StringBuffer("sessionId,webRoot");
			{
				Enumeration enu = request.getParameterNames();
				while (enu.hasMoreElements()) {
					String name = (String) enu.nextElement();
					parameterNames.append("," + name);
		%>
		<param name="<%=name%>" value="<%=request.getParameter(name)%>">
		<%
			}
			}
		%>
		<param name="parameterNames" value="<%=parameterNames.toString()%>">
		<param name="systemBundles" value="<%=systemBundles.toString()%>">

		<comment> <embed
			type="application/x-java-applet;version=1.6" WIDTH="100%"
			HEIGHT="100%" name="mainApplet" id="mainApplet"
			style="top: 0; left: 0; position: absolute;"
			pluginspage="http://java.sun.com/products/plugin/"
			java_code="com.seekon.smartclient.launcher.web.internal.JOSGiApplet.class"
			codebase="<%=webRoot%>/client/plugins" java_archive="<%=archives%>"
			java_arguments="-XX:PermSize=48m -Xms128m -Xmx256m" scriptable="true"
			mayscript="true" sessionId="<%=request.getSession().getId()%>"
			webRoot="<%=webRoot%>"
			<%{
				Enumeration enu = request.getParameterNames();
				while (enu.hasMoreElements()) {
					String name = (String) enu.nextElement();
					parameterNames.append("," + name);
					out.println(name + "=\"" + request.getParameter(name) + "\"\n");
				}
			}%>
			parameterNames="<%=parameterNames.toString()%>"
			systemBundles="<%=systemBundles.toString()%>">
			<noembed> &lt;hr&gt; this explore not support plug in </noembed></comment>
	</object>
</body>
</html>