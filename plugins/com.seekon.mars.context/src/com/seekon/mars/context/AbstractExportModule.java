package com.seekon.mars.context;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;

import com.google.inject.AbstractModule;

public abstract class AbstractExportModule extends AbstractModule {

	@Override
	protected void configure() {
		initialize();
	}

	protected abstract void initialize();

	protected final void exportService(Class interfaceClazz, Class serviceClazz) {
	  bindInnerService(interfaceClazz, serviceClazz);
		bind(export(interfaceClazz)).toProvider(service(serviceClazz).export());
	}

	protected final void bindInnerService(Class interfaceClazz, Class serviceClazz){
	  bind(interfaceClazz).to(serviceClazz);
	}
}
