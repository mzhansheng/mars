package com.seekon.mars.context;

import static org.ops4j.peaberry.Peaberry.service;

import com.google.inject.AbstractModule;

public abstract class AbstractImportModule extends AbstractModule{

	@Override
	protected void configure() {
		initialize();
	}

	protected abstract void initialize();
	
	protected <T> void importService(Class<T> serviceClazz){
		bind(serviceClazz).toProvider(service(serviceClazz).single());
	}
}
