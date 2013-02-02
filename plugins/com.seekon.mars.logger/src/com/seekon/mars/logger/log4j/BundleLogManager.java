package com.seekon.mars.logger.log4j;

import java.net.URL;

import org.apache.log4j.LogManager;
import org.apache.log4j.helpers.OptionConverter;
import org.osgi.framework.Bundle;

public class BundleLogManager {

  private BundleLogManager() {
    super();
  }

  public static void loadBundleLog4jConfigure(Bundle bundle ){
    URL url = bundle.getResource("log4j.xml");
    if(url == null){
      url = bundle.getResource("log4j.properties");
    }
    if(url != null){
      OptionConverter.selectAndConfigure(url, null, LogManager.getLoggerRepository());
    }
  }
}
