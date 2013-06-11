package com.seekon.system.common.service;

import java.util.UUID;

import org.mybatis.guice.transactional.Transactional;

import com.google.inject.Inject;
import com.seekon.system.common.mapper.OptionMapper;
import com.seekon.system.common.model.Option;
import com.seekon.system.common.spi.OptionService;

public abstract class AbstractOptionService implements OptionService{
  
  @Inject
  OptionMapper optionMapper;
  
  @Override
  public Option getOptionByCode(String optCode) {
    return optionMapper.getOptionByCode(optCode);
  }
  
  @Override
  @Transactional
  public Option addOption(Option option) {
    option.setId(UUID.randomUUID().toString());
    optionMapper.addOption(option);
    return option;
  }
  
  @Override
  @Transactional
  public void updateOption(Option option) {
    optionMapper.updateOption(option);
  }
  
  @Override
  @Transactional
  public void deleteOption(Option option) {
    optionMapper.deleteOption(option);
  }
}
