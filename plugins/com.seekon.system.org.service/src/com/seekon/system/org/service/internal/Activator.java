package com.seekon.system.org.service.internal;

import static org.ops4j.peaberry.Peaberry.osgiModule;

import javax.inject.Inject;

import org.ops4j.peaberry.Export;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Injector;
import com.seekon.mars.context.AbstractMyBatisExportModule;
import com.seekon.mars.context.GuiceContext;
import com.seekon.system.org.model.Department;
import com.seekon.system.org.model.Enterprise;
import com.seekon.system.org.service.mapper.DepartmentMapper;
import com.seekon.system.org.service.mapper.EnterpriseMapper;
import com.seekon.system.org.spi.OrgService;

public class Activator implements BundleActivator {

	@Inject
	Export<OrgService> handle;

	@Override
	public void start(BundleContext context) throws Exception {
		Injector injector = GuiceContext.createChildInjector(osgiModule(context),
				new AbstractMyBatisExportModule() {
					@Override
					protected void initialize() {
						this.addSimpleAlias(Enterprise.class);
						this.addSimpleAlias(Department.class);
						
						this.addMapperClass(EnterpriseMapper.class);
						this.addMapperClass(DepartmentMapper.class);
						
						this.exportService(OrgService.class, OrgServiceImpl.class);
					}
				});
		injector.injectMembers(this);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		handle.unput();
	}

}
