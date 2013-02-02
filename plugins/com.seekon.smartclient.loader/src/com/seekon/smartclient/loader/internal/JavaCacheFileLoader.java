package com.seekon.smartclient.loader.internal;

import java.net.URL;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.packageadmin.PackageAdmin;

import com.seekon.smartclient.loader.JarFile;
import com.sun.deploy.net.DownloadEngine;

/**
 * 
 * 基于java的cache机制进行jar插件的检查更新和加载
 *
 */
public class JavaCacheFileLoader extends AbstractJarFileLoader {

	public JavaCacheFileLoader(BundleContext bundleContext, String serverRootUrl) {
		super(bundleContext, serverRootUrl);
	}

	@Override
	protected List<JarFile> checkUpdatedJarFiles(List<JarFile> jarFileList) {		
		for (JarFile jarFile : jarFileList) {
			try {
				URL fileUrl = new URL(serverRootUrl + jarFile.getName());
				long fileLastModified = jarFile.getLastModifiedTime();
								
				java.util.jar.JarFile cachedJarFile = DownloadEngine.getCachedJarFile(fileUrl, null);
				if(cachedJarFile != null &&
						(DownloadEngine.getCachedFile(fileUrl).lastModified() >= fileLastModified)){
					System.out.println("插件没更新，从本地加载:" + jarFile.getName());
				}else{
					cachedJarFile =  DownloadEngine.getUpdatedJarFile(fileUrl, null);
					System.out.println("插件存在更新，从远程下载:" + jarFile.getName());
				}
				if(cachedJarFile != null){
					jarFile.setLocation(cachedJarFile.getName());
					jarFile.setManifest(cachedJarFile.getManifest().getMainAttributes());		
				}else{
					System.out.println("$$$$ entry 为空。");
				}
			} catch (Throwable e) {
				System.err.println(jarFile.getName() + "下载失败");
				e.printStackTrace();
			}
		}
		return jarFileList;
	}
}
