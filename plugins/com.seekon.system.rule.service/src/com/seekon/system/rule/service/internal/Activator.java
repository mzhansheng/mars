package com.seekon.system.rule.service.internal;

import javax.inject.Inject;
import javax.sound.midi.MetaMessage;

import org.ops4j.peaberry.Export;
import org.ops4j.peaberry.Peaberry;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Injector;
import com.seekon.mars.context.AbstractMyBatisExportModule;
import com.seekon.mars.context.GuiceContext;
import com.seekon.mars.dictionary.FieldMeta;
import com.seekon.mars.dictionary.TableMeta;
import com.seekon.mars.dictionary.service.MetaMapper;
import com.seekon.mars.rule.engine.specification.IRuleSpecification;
import com.seekon.system.common.mapper.CategoryMapper;
import com.seekon.system.common.mapper.ElementMapper;
import com.seekon.system.common.model.Category;
import com.seekon.system.common.model.Element;
import com.seekon.system.rule.model.Rule;
import com.seekon.system.rule.model.RuleElement;
import com.seekon.system.rule.service.impl.RuleServiceImpl;
import com.seekon.system.rule.service.mapper.RuleElementMapper;
import com.seekon.system.rule.service.mapper.RuleMapper;
import com.seekon.system.rule.service.type.RuleSpecificationTypeHandler;
import com.seekon.system.rule.spi.RuleService;

public class Activator implements BundleActivator {

  @Inject
  Export<RuleService> handle;

  @Override
  public void start(BundleContext context) throws Exception {
    Injector injector = GuiceContext.createChildInjector(
      Peaberry.osgiModule(context), new AbstractMyBatisExportModule() {
        @Override
        protected void initialize() {
          this.addSimpleAlias(Category.class);
          this.addSimpleAlias(Rule.class);
          this.addSimpleAlias(RuleElement.class);
          this.addSimpleAlias(IRuleSpecification.class);
          this.addSimpleAlias(RuleSpecificationTypeHandler.class);
          this.addSimpleAlias(Element.class);
          this.addSimpleAlias(TableMeta.class);
          this.addSimpleAlias(FieldMeta.class);
          
          this.addTypeHandlerClass(RuleSpecificationTypeHandler.class);

          this.addMapperClass(CategoryMapper.class);
          this.addMapperClass(RuleMapper.class);
          this.addMapperClass(RuleElementMapper.class);
          this.addMapperClass(ElementMapper.class);
          this.addMapperClass(MetaMapper.class);
          
          this.exportService(RuleService.class, RuleServiceImpl.class);
        }
      });
    injector.injectMembers(this);
  }

  @Override
  public void stop(BundleContext context) throws Exception {
    handle.unput();
  }

}
