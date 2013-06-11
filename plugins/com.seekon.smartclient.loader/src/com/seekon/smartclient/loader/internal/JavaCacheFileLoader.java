package com.seekon.smartclient.loader.internal;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.List;

import org.osgi.framework.BundleContext;

import com.seekon.smartclient.loader.JarFile;
import com.sun.deploy.cache.Cache;
import com.sun.deploy.cache.CacheEntry;
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

	/**
	 * @deprecated
	 * @param jarFileList
	 * @return
	 */
	protected List<JarFile> _checkUpdatedJarFiles(List<JarFile> jarFileList) {		
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
	
	 @Override
	  protected List<JarFile> checkUpdatedJarFiles(List<JarFile> jarFileList) {   
	    for (JarFile jarFile : jarFileList) {
	      try {
	        URL fileUrl = new URL(serverRootUrl + jarFile.getName());
	        long fileLastModified = jarFile.getLastModifiedTime();
	        
	        java.util.jar.JarFile cachedJarFile = null;
	        CacheEntry entry = null;
	        Method method = null;
	        try{
	          entry = Cache.getCacheEntry(fileUrl, null, null);
	        }catch(Throwable ee){
	          method = Cache.class.getMethod("getCacheEntry", new Class[]{URL.class, String.class});
	          entry = (CacheEntry) method.invoke(Cache.class, new Object[]{fileUrl, null});
	        }
	        
	        if (entry != null
	          && entry.getLastModified() >= fileLastModified) {
	          cachedJarFile = entry.getJarFile();
	          System.out.println("插件没更新，从本地加载:" + jarFile.getName());
	        } else {
	          URL jarFileURL = new URL("jar:" + fileUrl + "!/" + java.util.jar.JarFile.MANIFEST_NAME);
	          JarURLConnection jarURLConnection = (JarURLConnection) jarFileURL
	            .openConnection();
	          InputStream is = null;
	          try {
	            is = jarURLConnection.getInputStream();
	          } finally {
	            is.close();
	          }
	          System.out.println("插件存在更新，从远程下载:" + jarFile.getName());
	          
	          if(method != null){
	            entry = (CacheEntry) method.invoke(Cache.class, new Object[]{fileUrl, null});
	          }else{
	            entry = Cache.getCacheEntry(fileUrl, null, null);
	          }
	          if (entry != null) {
	            cachedJarFile = entry.getJarFile();
	          }	          
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
