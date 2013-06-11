package com.seekon.mars.web.deployer.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleException;
import org.osgi.framework.BundleListener;
import org.osgi.service.http.HttpService;

import com.seekon.mars.web.deployer.LoadException;
import com.seekon.mars.web.deployer.ModuleLoader;
import com.seekon.mars.web.deployer.def.WebAppLoader;

public class Activator implements BundleActivator, BundleListener {

	static Activator instance = null;

	BundleContext bundleContext = null;

	private HttpServiceTracker httpServiceTracker = null;

	private static Logger log = Logger.getLogger(Activator.class);

	private List<ModuleLoader> moduleLoaders = new ArrayList<ModuleLoader>();

	private List<Bundle> registeredBundles = new ArrayList<Bundle>();

	@Override
	public void start(BundleContext context) throws Exception {
		instance = this;
		instance.bundleContext = context;

		context.addBundleListener(this);

		httpServiceTracker = new HttpServiceTracker(context);
		httpServiceTracker.open();

		moduleLoaders.add(new WebAppLoader());
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		httpServiceTracker.close();
		context.removeBundleListener(this);
	}

	@Override
	public void bundleChanged(BundleEvent event) {
		HttpService httpService = httpServiceTracker.getHttpService();
		if (httpService == null) {
			return;
		}

		Bundle bundle = event.getBundle();
		switch (event.getType()) {
		case BundleEvent.RESOLVED: {
			if (bundle.getHeaders().get("Bundle-Activator") != null) {
				try {
					bundle.start();
				} catch (BundleException e) {
					log.error(e);
				}
			}
			break;
		}
		case BundleEvent.STARTED: {
			if (bundle.getHeaders().get("Context-Path") != null) {
				try {
					registerApplication(bundle);
				} catch (LoadException e) {
					log.error(e);
				}
			}
			break;
		}
		case BundleEvent.STOPPED: {//TODO: do nothing
			if (bundle.getHeaders().get("Context-Path") != null) {
				unregisterApplication(bundle);
			}
			break;
		}
		default:
			break;
		}
	}

	private void registerApplication(Bundle bundle) throws LoadException {
		if (registeredBundles.contains(bundle)) {
			return;
		}

		List succLoaders = new ArrayList();
		for (Iterator it = moduleLoaders.iterator(); it.hasNext();) {
			ModuleLoader loader = (ModuleLoader) it.next();

			try {
				loader.load(bundle);
				succLoaders.add(loader);
			} catch (LoadException e) {
				for (Iterator it1 = succLoaders.iterator(); it1.hasNext();) {
					ModuleLoader l = (ModuleLoader) it1.next();
					try {
						l.unload(bundle);
					} catch (LoadException e1) {
						log.error(e.getMessage(), e);
					}
				}
				log.error(e.getMessage(), e);
				throw e;
			}
		}
		registeredBundles.add(bundle);
	}

	private void unregisterApplication(Bundle bundle) {
		if (!registeredBundles.contains(bundle)) {
			return;
		}

		for (Iterator it = moduleLoaders.iterator(); it.hasNext();) {
			ModuleLoader loader = (ModuleLoader) it.next();
			try {
				loader.unload(bundle);
			} catch (LoadException e) {
				log.error(e.getMessage(), e);
			}
		}
		registeredBundles.remove(bundle);
	}
}
