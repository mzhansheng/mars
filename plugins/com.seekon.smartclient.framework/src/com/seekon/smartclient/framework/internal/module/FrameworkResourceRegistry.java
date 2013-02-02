package com.seekon.smartclient.framework.internal.module;

import java.awt.Component;

import com.seekon.smartclient.module.AbstractResource;
import com.seekon.smartclient.module.Resource;
import com.seekon.smartclient.module.ResourceRegistry;

public class FrameworkResourceRegistry implements ResourceRegistry {

	@Override
	public Component getComponent(AbstractResource item) {
		return null;
	}

	@Override
	public AbstractResource getRootResouce() {
		Resource root = new Resource("framework.manager", "框架管理");
		root.addChild(new Resource("framework.plugins.info", "插件信息"));
		root.addChild(new Resource("framework.layout.manager", "布局管理"));
		Resource top = new Resource("top.platform.manager", "应用平台");
		top.addChild(root);
		return top;
	}

}
