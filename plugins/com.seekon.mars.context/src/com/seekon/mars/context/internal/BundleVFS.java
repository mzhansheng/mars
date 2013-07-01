/*
 *    Copyright 2009-2012 The MyBatis Team
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.seekon.mars.context.internal;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.ibatis.io.DefaultVFS;
import org.apache.ibatis.io.VFS;

/**
 * A default implementation of {@link VFS} that works for most application servers.
 * 
 * @author Ben Gunter
 */
public class BundleVFS extends DefaultVFS {

  @Override
  public List<String> list(URL url, String path) throws IOException {

      // First, try to find the URL of a JAR file containing the requested resource. If a JAR
      // file is found, then we'll list child resources by reading the JAR.
      String protocol = url.getProtocol();
      if ("bundleresource".equals(protocol)
        || "bundle".equals(protocol)) {////TODO 2012-05-12
        try {
          //        jarUrl = ((org.eclipse.osgi.framework.internal.core.BundleURLConnection)jarUrl.openConnection())
          //          .getLocalURL();
          URLConnection connection = url.openConnection();
          Method method = connection.getClass().getDeclaredMethod("getLocalURL", null);
          if(method != null){
            method.setAccessible(true);
            url = (URL)method.invoke(connection, null);
          }
          protocol = url.getProtocol();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      return super.list(url, path);
  }
}
