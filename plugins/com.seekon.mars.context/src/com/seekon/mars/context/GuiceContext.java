package com.seekon.mars.context;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class GuiceContext {

	private static Injector globalSharedInjector;

	private GuiceContext() {
	}

	protected static Injector getGlobalSharedInjector() {
		return globalSharedInjector;
	}

	public static Injector createChildInjector(Iterable<? extends Module> modules) {
		if (globalSharedInjector == null) {
			return Guice.createInjector(modules);
		}
		return globalSharedInjector.createChildInjector(modules);
	}

	public static Injector createChildInjector(Module... modules) {
		if (globalSharedInjector == null) {
			return Guice.createInjector(modules);
		}
		return globalSharedInjector.createChildInjector(modules);
	}
}
