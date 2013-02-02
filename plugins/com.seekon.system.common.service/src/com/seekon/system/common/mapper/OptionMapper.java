package com.seekon.system.common.mapper;

import com.seekon.system.common.model.Option;

public interface OptionMapper {

  public void addOption(Option option);
  
  public void updateOption(Option option);
  
  public void deleteOption(Option option);
  
  public Option getOptionByCode(String optCode);
}
