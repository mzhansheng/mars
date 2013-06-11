package com.seekon.system.common.spi;

import com.seekon.system.common.model.Option;

public interface OptionService {

  Option getOptionByCode(String optCode);

  Option addOption(Option option);

  void updateOption(Option option);

  void deleteOption(Option option);
}
