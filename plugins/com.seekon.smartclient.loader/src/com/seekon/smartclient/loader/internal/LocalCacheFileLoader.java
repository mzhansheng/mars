package com.seekon.smartclient.loader.internal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.osgi.framework.BundleContext;

import com.seekon.smartclient.loader.JarFile;

public class LocalCacheFileLoader extends AbstractJarFileLoader{

  public LocalCacheFileLoader(BundleContext bundleContext, String serverRootUrl) {
    super(bundleContext, serverRootUrl);
  }

  @Override
  protected List<JarFile> checkUpdatedJarFiles(List<JarFile> jarFileList) {
    String installArea = (String) System.getProperties().get(
      "osgi.install.area");
    if(installArea == null || installArea.trim().length() == 0){
      return jarFileList;
    }
    String updateDirPath = installArea + "/updates/";
    if(updateDirPath.startsWith("file:/")){
      updateDirPath = updateDirPath.substring("file:/".length());
    }
    checkAndCreateUpdateDir(updateDirPath);
    
    for (JarFile jarFile : jarFileList) {
      try {
        long fileLastModified = jarFile.getLastModifiedTime();
        
        File file = new File(updateDirPath + jarFile.getName());
        if(file.exists() && file.lastModified() > fileLastModified){
          System.out.println("插件没更新，从本地加载:" + jarFile.getName());
        }else{
          System.out.println("插件存在更新，从远程下载:" + jarFile.getName());
          URL fileUrl = new URL(serverRootUrl + jarFile.getName());
          writeFile(file, fileUrl);
        }
        
        if(file.exists()){
          String filePath = file.getAbsolutePath();
          jarFile.setLocation(filePath);
          java.util.jar.JarFile localJarFile = new java.util.jar.JarFile(filePath);
          jarFile.setManifest(localJarFile.getManifest().getMainAttributes());
        }else{
          System.out.println(file.getAbsolutePath() + "不存在。");
        }
//        java.util.jar.JarFile cachedJarFile = DownloadEngine.getCachedJarFile(fileUrl, null);
//        if(cachedJarFile != null &&
//            (DownloadEngine.getCachedFile(fileUrl).lastModified() >= fileLastModified)){
//          System.out.println("插件没更新，从本地加载:" + jarFile.getName());
//        }else{
//          cachedJarFile =  DownloadEngine.getUpdatedJarFile(fileUrl, null);
//          System.out.println("插件存在更新，从远程下载:" + jarFile.getName());
//        }
//        if(cachedJarFile != null){
//          jarFile.setLocation(cachedJarFile.getName());
//          jarFile.setManifest(cachedJarFile.getManifest().getMainAttributes());   
//        }else{
//          System.out.println("$$$$ entry 为空。");
//        }
      } catch (Throwable e) {
        System.err.println(jarFile.getName() + "下载失败");
        e.printStackTrace();
      }
    }
    return jarFileList;
  }

  private void checkAndCreateUpdateDir(String dirPath){
    File dir = new File(dirPath);
    if(!dir.exists()){
      dir.mkdir();
    }
  }
  
  private void writeFile(File file, URL fileUrl) throws IOException{
    if(!file.exists()){
      file.createNewFile();
    }
    
    InputStream is = fileUrl.openStream();
    FileOutputStream os = new FileOutputStream(file);
    byte[] buffer = new byte[1024];
    int size = 0;
    while((size = is.read(buffer)) > 0){
      os.write(buffer, 0, size);
    }
    os.flush();
    os.close();
  }
}
