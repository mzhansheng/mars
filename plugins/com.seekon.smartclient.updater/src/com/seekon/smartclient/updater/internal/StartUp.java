package com.seekon.smartclient.updater.internal;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.seekon.mars.update.service.UpdateService;
import com.seekon.mars.update.service.UpdatedBundle;
import com.seekon.smartclient.loader.JarFile;
import com.seekon.smartclient.loader.JarFileLoader;
import com.seekon.smartclient.splash.Splash;
import com.seekon.smartclient.splash.SplashPlugin;

public class StartUp implements com.seekon.smartclient.launcher.Startup {

	@Override
	public void run(BundleContext context) {
		Splash splash = SplashPlugin.getDefault().getSplash();
		try {
			splash.displayMessage("正在检查更新");
			List<UpdatedBundle> updatedBundleList = Activator.injector.getInstance(UpdateService.class)
					.getUpdatedBundleList();

			List<JarFile> jarFileList = new ArrayList<JarFile>();
			for (UpdatedBundle bundle : updatedBundleList) {
				jarFileList.add(new JarFile(bundle.getBundleName(), bundle
						.getLastModifiedTime()));
			}
			
			splash.displayMessage("正在加载更新的插件");
			
			ServiceReference sr = context.getServiceReference(JarFileLoader.class.getName());
			if(sr != null){
				((JarFileLoader)context.getService(sr)).load(jarFileList);
			}
			//Activator.injector.getInstance(JarFileLoader.class).load(jarFileList);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			splash.displayMessage("插件更新完成");
		}
	}

}
