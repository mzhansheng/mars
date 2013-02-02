package com.seekon.mars.context;

import com.google.inject.Module;

public interface ISharedModuleProvider {

	public final static String EXTENSION_POINT_ID = "com.seekon.mars.context";

	Module get();

}
