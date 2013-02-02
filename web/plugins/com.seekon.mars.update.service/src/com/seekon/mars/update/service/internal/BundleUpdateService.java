package com.seekon.mars.update.service.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.seekon.mars.update.service.UpdateService;
import com.seekon.mars.update.service.UpdatedBundle;

public class BundleUpdateService implements UpdateService {

	@Override
	public List<UpdatedBundle> getUpdatedBundleList() {
		BundleContext bundleContext = Activator.getContext();
		ServiceReference sr = bundleContext
				.getServiceReference(ServletContext.class.getName());
		ServletContext sc = (ServletContext) bundleContext.getService(sr);

		List<UpdatedBundle> updatedBundleList = getSharedBundleList(sc);
		
		String clientPath = sc.getRealPath("/client/plugins");
		File dir = new File(clientPath);
		if (!dir.exists() || !dir.isDirectory()) {
			return updatedBundleList;
		}
		for (File file : dir.listFiles()) {
			UpdatedBundle bundle = new UpdatedBundle(file.getName(), "0",
					file.lastModified());
			if (!updatedBundleList.contains(bundle)) {
				updatedBundleList.add(bundle);
			}
		}

		return updatedBundleList;
	}

	private List<UpdatedBundle> getSharedBundleList(ServletContext sc) {
		BundleContext bundleContext = Activator.getContext();
		List<UpdatedBundle> sharedBundleList = new ArrayList<UpdatedBundle>();
		for (Bundle bundle : bundleContext.getBundles()) {
			String bundleType = (String) bundle.getHeaders().get("Bundle-Type");
			if (bundleType != null
					&& (/* "client".equalsIgnoreCase(bundleType) || */"share"
							.equalsIgnoreCase(bundleType))) {

				String location = bundle.getLocation();
				int pos = location.lastIndexOf("/");
				if (pos > 0) {
					location = location.substring(pos + 1, location.length());
				}
				
				String filePath = sc.getRealPath("/WEB-INF/eclipse/plugins/" + location);
				File file = new File(filePath);
				if(!file.exists() || !file.isFile()){
					continue;
				}
				
				//System.out.println(bundle.getSymbolicName() + ":" + location);
				sharedBundleList.add(new UpdatedBundle(location, bundle.getVersion()
						.toString(), file.lastModified()));
			}
		}
		return sharedBundleList;
	}
}
