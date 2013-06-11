package com.seekon.system.auth.service.internal;

import javax.inject.Inject;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Peaberry;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Injector;
import com.seekon.mars.context.AbstractImportModule;
import com.seekon.mars.context.AbstractMyBatisExportModule;
import com.seekon.mars.context.GuiceContext;
import com.seekon.system.auth.model.ResourceWrapper;
import com.seekon.system.auth.model.Role;
import com.seekon.system.auth.model.User;
import com.seekon.system.auth.service.impl.AuthElementServiceImpl;
import com.seekon.system.auth.service.impl.DataScopeServiceImpl;
import com.seekon.system.auth.service.impl.LoginServiceImpl;
import com.seekon.system.auth.service.impl.RoleServiceImpl;
import com.seekon.system.auth.service.impl.UserServiceImpl;
import com.seekon.system.auth.service.mapper.RoleMapper;
import com.seekon.system.auth.service.mapper.UserMapper;
import com.seekon.system.auth.service.type.ResourceTypeHandler;
import com.seekon.system.auth.spi.AuthElementService;
import com.seekon.system.auth.spi.DataScopeService;
import com.seekon.system.auth.spi.LoginService;
import com.seekon.system.auth.spi.RoleService;
import com.seekon.system.auth.spi.UserService;
import com.seekon.system.common.mapper.CategoryMapper;
import com.seekon.system.common.mapper.OptionMapper;
import com.seekon.system.common.model.Category;
import com.seekon.system.common.model.Option;
import com.seekon.system.common.type.OptionTypeHandler;
import com.seekon.system.org.model.AbstractOrgItem;
import com.seekon.system.org.spi.OrgService;

public class Activator implements BundleActivator {

  @Inject
  Export<RoleService> roleHandle;

  @Inject
  Export<UserService> userHandle;

  @Inject
  Export<LoginService> loginHandle;

  @Inject
  Export<DataScopeService> dataScopeHandle;

  @Inject
  Export<AuthElementService> authElementHandle;
  
  @Override
  public void start(BundleContext context) throws Exception {
    Injector injector = GuiceContext.createChildInjector(
      Peaberry.osgiModule(context), new AbstractMyBatisExportModule() {
        @Override
        protected void initialize() {
          this.addSimpleAlias(Role.class);
          this.addSimpleAlias(Category.class);
          this.addSimpleAlias(ResourceWrapper.class);
          this.addSimpleAlias(ResourceTypeHandler.class);
          this.addSimpleAlias(User.class);
          this.addSimpleAlias(AbstractOrgItem.class);
          this.addSimpleAlias(Option.class);
          this.addSimpleAlias(OptionTypeHandler.class);
          
          this.addTypeHandlerClass(ResourceTypeHandler.class);
          this.addTypeHandlerClass(OptionTypeHandler.class);
          
          this.addMapperClass(CategoryMapper.class);
          this.addMapperClass(RoleMapper.class);
          this.addMapperClass(UserMapper.class);
          this.addMapperClass(OptionMapper.class);
          
          this.addInterceptorClass(DatascopeMethodInterceptor.class);
          
          this.exportService(RoleService.class, RoleServiceImpl.class);
          this.exportService(UserService.class, UserServiceImpl.class);
          this.exportService(LoginService.class, LoginServiceImpl.class);
          this.exportService(DataScopeService.class, DataScopeServiceImpl.class);
          this.exportService(AuthElementService.class, AuthElementServiceImpl.class);
        }
      }, new AbstractImportModule() {
        @Override
        protected void initialize() {
          importService(OrgService.class);
        }
      });
    injector.injectMembers(this);
  }

  @Override
  public void stop(BundleContext context) throws Exception {
    roleHandle.unput();
    userHandle.unput();
    loginHandle.unput();
    dataScopeHandle.unput();
    authElementHandle.unput();
  }

}
