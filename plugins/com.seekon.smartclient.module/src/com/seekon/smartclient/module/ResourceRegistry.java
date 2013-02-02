package com.seekon.smartclient.module;

import java.awt.Component;

public interface ResourceRegistry {

	public abstract AbstractResource getRootResouce();

	public abstract Component getComponent(AbstractResource item);

}
