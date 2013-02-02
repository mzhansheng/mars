package com.seekon.mars.osgi.bridge.internal;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AllPermission;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

public class FrameworkLauncher {

  private static final String REFERENCE_SCHEME = "reference:";

  private static final String CONFIG_INI = "config.ini";

  private static final String DOT_JAR = ".jar";

  private static final String WS_DELIM = " \t\n\r\f";

  protected static final String FILE_SCHEME = "file:";

  protected static final String FRAMEWORK_BUNDLE_NAME = "org.eclipse.osgi";

  protected static final String STARTER = "org.eclipse.core.runtime.adaptor.EclipseStarter";

  protected static final String FRAMEWORKPROPERTIES = "org.eclipse.osgi.framework.internal.core.FrameworkProperties";

  protected static final String NULL_IDENTIFIER = "@null";

  protected static final String OSGI_FRAMEWORK = "osgi.framework";

  protected static final String OSGI_FRAMEWORK_EXTENSIONS = "osgi.framework.extensions";

  protected static final String OSGI_INSTANCE_AREA = "osgi.instance.area";

  protected static final String OSGI_CONFIGURATION_AREA = "osgi.configuration.area";

  protected static final String OSGI_INSTALL_AREA = "osgi.install.area";

  protected static final String OSGI_FORCED_RESTART = "osgi.forcedRestart";

  protected static final String RESOURCE_BASE = "/WEB-INF/";

  protected static final String ECLIPSE = "eclipse/";

  protected static final String LAUNCH_INI = "launch.ini";

  private static final String EXTENSIONBUNDLE_DEFAULT_BSN = "org.eclipse.equinox.servletbridge.extensionbundle";

  private static final String EXTENSIONBUNDLE_DEFAULT_VERSION = "1.2.0";

  private static final String MANIFEST_VERSION = "Manifest-Version";

  private static final String BUNDLE_MANIFEST_VERSION = "Bundle-ManifestVersion";

  private static final String BUNDLE_NAME = "Bundle-Name";

  private static final String BUNDLE_SYMBOLIC_NAME = "Bundle-SymbolicName";

  private static final String BUNDLE_VERSION = "Bundle-Version";

  private static final String FRAGMENT_HOST = "Fragment-Host";

  private static final String EXPORT_PACKAGE = "Export-Package";

  private static final String CONFIG_COMMANDLINE = "commandline";

  private static final String CONFIG_EXTENDED_FRAMEWORK_EXPORTS = "extendedFrameworkExports";

  private static final String CONFIG_OVERRIDE_AND_REPLACE_EXTENSION_BUNDLE = "overrideAndReplaceExtensionBundle";

  static final PermissionCollection allPermissions = new PermissionCollection() {
    private static final long serialVersionUID = 482874725021998286L;

    Permission allPermission = new AllPermission();

    public void add(Permission permission) {
    }

    public boolean implies(Permission permission) {
      return true;
    }

    public Enumeration elements() {
      return new Enumeration() {
        int cur = 0;

        public boolean hasMoreElements() {
          return cur < 1;
        }

        public Object nextElement() {
          if (cur == 0) {
            cur = 1;
            return allPermission;
          }
          throw new NoSuchElementException();
        }
      };
    }
  };

  static {
    if (allPermissions.elements() == null)
      throw new IllegalStateException();
  }

  protected ServletConfig config;

  protected ServletContext context;

  protected String resourceBase;

  private File platformDirectory;

  private ClassLoader frameworkContextClassLoader;

  private CloseableURLClassLoader frameworkClassLoader;

  public void init(ServletConfig servletConfig) {
    config = servletConfig;
    context = servletConfig.getServletContext();
    initResourceBase();
    init();
  }

  protected void initResourceBase() {
    try {
      if (context.getResource(RESOURCE_BASE + LAUNCH_INI) != null) {
        resourceBase = RESOURCE_BASE;
        return;
      }
      if (context.getResource(RESOURCE_BASE + ECLIPSE + LAUNCH_INI) != null) {
        resourceBase = RESOURCE_BASE + ECLIPSE;
        return;
      }
    } catch (MalformedURLException e) {
    }
    resourceBase = RESOURCE_BASE;
  }

  public void init() {
  }

  public void destroy() {
  }

  public synchronized void deploy() {
    if ("true".equalsIgnoreCase(System.getProperty("gmap.usePlatformCopy"))) {
      deployCurrentPlatformCopy();
    } else {
      deployCurrentPlatform();
    }
  }

  /**
   * 直接部署当前Platform
   */
  private void deployCurrentPlatform() {
    if (platformDirectory != null) {
      context.log("Framework is already deployed");
      return;
    }
    platformDirectory = new File(context.getRealPath(resourceBase));
    File plugins = new File(platformDirectory, "plugins");
    deployExtensionBundle(plugins);
  }

  /**
   * 部署当前Platform的副本到server临时文件夹
   */
  private void deployCurrentPlatformCopy() {
    if (platformDirectory != null) {
      context.log("Framework is already deployed");
      return;
    }

    File servletTemp = (File) context.getAttribute("javax.servlet.context.tempdir");
    platformDirectory = new File(servletTemp, "eclipse");
    if (!platformDirectory.exists()) {
      platformDirectory.mkdirs();
    }

    copyResource(resourceBase + "configuration/", new File(platformDirectory,
      "configuration"));
    copyResource(resourceBase + "features/", new File(platformDirectory, "features"));
    File plugins = new File(platformDirectory, "plugins");
    copyResource(resourceBase + "plugins/", plugins);
    copyResource(resourceBase + "p2/", new File(platformDirectory, "p2"));
    deployExtensionBundle(plugins);
    copyResource(resourceBase + ".eclipseproduct", new File(platformDirectory,
      ".eclipseproduct"));
  }

  private void deployExtensionBundle(File plugins) {
    final String extensionBundleBSN = EXTENSIONBUNDLE_DEFAULT_BSN;
    File extensionBundleFile = findExtensionBundleFile(plugins, extensionBundleBSN);

    if (extensionBundleFile == null)
      generateExtensionBundle(plugins, extensionBundleBSN,
        EXTENSIONBUNDLE_DEFAULT_VERSION);
    else if (Boolean.valueOf(
      config.getInitParameter(CONFIG_OVERRIDE_AND_REPLACE_EXTENSION_BUNDLE))
      .booleanValue()) {
      String extensionBundleVersion = findExtensionBundleVersion(
        extensionBundleFile, extensionBundleBSN);
      if (extensionBundleFile.isDirectory()) {
        deleteDirectory(extensionBundleFile);
      } else {
        extensionBundleFile.delete();
      }
      generateExtensionBundle(plugins, extensionBundleBSN, extensionBundleVersion);
    } else {
      processExtensionBundle(extensionBundleFile);
    }
  }

  private File findExtensionBundleFile(File plugins, final String extensionBundleBSN) {
    FileFilter extensionBundleFilter = new FileFilter() {
      public boolean accept(File candidate) {
        return candidate.getName().startsWith(extensionBundleBSN + "_");
      }
    };
    File[] extensionBundles = plugins.listFiles(extensionBundleFilter);
    if (extensionBundles.length == 0)
      return null;

    if (extensionBundles.length > 1) {
      for (int i = 1; i < extensionBundles.length; i++) {
        if (extensionBundles[i].isDirectory()) {
          deleteDirectory(extensionBundles[i]);
        } else {
          extensionBundles[i].delete();
        }
      }
    }
    return extensionBundles[0];
  }

  private String findExtensionBundleVersion(File extensionBundleFile,
    String extensionBundleBSN) {
    String fileName = extensionBundleFile.getName();
    if (fileName.endsWith(DOT_JAR)) {
      return fileName.substring(extensionBundleBSN.length() + 1, fileName.length()
        - DOT_JAR.length());
    }
    return fileName.substring(extensionBundleBSN.length() + 1);
  }

  private void generateExtensionBundle(File plugins, String extensionBundleBSN,
    String extensionBundleVersion) {
    Manifest mf = new Manifest();
    Attributes attribs = mf.getMainAttributes();
    attribs.putValue(MANIFEST_VERSION, "1.0");
    attribs.putValue(BUNDLE_MANIFEST_VERSION, "2");
    attribs.putValue(BUNDLE_NAME, "Servletbridge Extension Bundle");
    attribs.putValue(BUNDLE_SYMBOLIC_NAME, extensionBundleBSN);
    attribs.putValue(BUNDLE_VERSION, extensionBundleVersion);
    attribs.putValue(FRAGMENT_HOST, "system.bundle; extension:=framework");

    String servletVersion = context.getMajorVersion() + "."
      + context.getMinorVersion();
    servletVersion = "2.5";
    String packageExports = "com.seekon.mars.osgi.bridge"
      + ", javax.servlet; version=" + servletVersion
      + ", javax.servlet.http; version=" + servletVersion
      + ", javax.servlet.resources; version=" + servletVersion;

    String extendedExports = config
      .getInitParameter(CONFIG_EXTENDED_FRAMEWORK_EXPORTS);
    if (extendedExports != null && extendedExports.trim().length() != 0)
      packageExports += ", " + extendedExports;

    StringBuffer buffer = new StringBuffer();
//    buffer.append(", org.w3c.dom");
//    
//    buffer.append(", javax.transaction; version=1.1.0");
//    buffer.append(", javax.transaction.xa; version=1.1.0");
//    //    buffer.append(", com.ufgov.gmap.domain");
//    buffer.append(", com.anyi.gp.sso");
//    buffer.append(", com.anyi.gp.sso.support");
//    buffer.append(", com.anyi.gp.domain");
//    buffer.append(", com.ufgov.app.manager.http");
//    buffer.append(", com.ufgov.app.manager.installer.bean");
    buffer.append(", com.mysql.jdbc.Driver");
    packageExports += buffer.toString();

    attribs.putValue(EXPORT_PACKAGE, packageExports);
    writeJarFile(new File(plugins, extensionBundleBSN + "_" + extensionBundleVersion
      + DOT_JAR), mf);
  }

  private void processExtensionBundle(File extensionBundleFile) {
    String fileName = extensionBundleFile.getName();
    if (fileName.endsWith(DOT_JAR)) {
      Manifest mf = readJarFile(extensionBundleFile);
      if (mf == null)
        return;
      Attributes attributes = mf.getMainAttributes();
      String exportPackage = (String) attributes.remove(new Name(
        "X-Deploy-Export-Package"));
      if (exportPackage != null) {
        attributes.putValue("Export-Package", exportPackage);
        writeJarFile(extensionBundleFile, mf);
      }
    }
  }

  private void writeJarFile(File jarFile, Manifest mf) {
    try {
      JarOutputStream jos = null;
      try {
        jos = new JarOutputStream(new FileOutputStream(jarFile), mf);
        jos.finish();
      } finally {
        if (jos != null)
          jos.close();
      }
    } catch (IOException e) {
      context.log("Error writing extension bundle", e);
    }
  }

  private Manifest readJarFile(File jarFile) {
    try {
      JarInputStream jis = null;
      try {
        jis = new JarInputStream(new FileInputStream(jarFile));
        return jis.getManifest();
      } finally {
        if (jis != null)
          jis.close();
      }
    } catch (IOException e) {
      context.log("Error reading extension bundle", e);
    }
    return null;
  }

  public synchronized void undeploy() {
    if (platformDirectory == null) {
      context.log("Undeploy unnecessary. - (not deployed)");
      return;
    }

    if (frameworkClassLoader != null) {
      throw new IllegalStateException("Could not undeploy Framework - (not stopped)");
    }

    deleteDirectory(new File(platformDirectory, "configuration"));
    deleteDirectory(new File(platformDirectory, "features"));
    deleteDirectory(new File(platformDirectory, "plugins"));
    deleteDirectory(new File(platformDirectory, "workspace"));
    deleteDirectory(new File(platformDirectory, "p2"));

    new File(platformDirectory, ".eclipseproduct").delete();
    platformDirectory = null;
  }

  public synchronized void start() {
    if (platformDirectory == null) {
      this.deployCurrentPlatform();
    }

    if (frameworkClassLoader != null) {
      context.log("Framework is already started");
      return;
    }

    Map initialPropertyMap = buildInitialPropertyMap();
    String[] args = buildCommandLineArguments();

    ClassLoader original = Thread.currentThread().getContextClassLoader();
    try {
      System.setProperty("osgi.framework.useSystemProperties", "false");

      URL[] frameworkURLs = findFrameworkURLs(initialPropertyMap);
      frameworkClassLoader = new ChildFirstURLClassLoader(frameworkURLs, this
        .getClass().getClassLoader());
      Class clazz = frameworkClassLoader.loadClass(STARTER);

      Method setInitialProperties = clazz.getMethod("setInitialProperties",
        new Class[] { Map.class });
      setInitialProperties.invoke(null, new Object[] { initialPropertyMap });

      registerRestartHandler(clazz);

      Method runMethod = clazz.getMethod("startup", new Class[] { String[].class,
        Runnable.class });
      runMethod.invoke(null, new Object[] { args, null });

      frameworkContextClassLoader = Thread.currentThread().getContextClassLoader();
    } catch (InvocationTargetException ite) {
      Throwable t = ite.getTargetException();
      if (t == null)
        t = ite;
      context.log("Error while starting Framework", t);
      throw new RuntimeException(t.getMessage());
    } catch (Exception e) {
      context.log("Error while starting Framework", e);
      throw new RuntimeException(e.getMessage());
    } finally {
      Thread.currentThread().setContextClassLoader(original);
    }
  }

  private URL[] findFrameworkURLs(Map initialPropertyMap) {
    List frameworkURLs = new ArrayList();
    String installArea = (String) initialPropertyMap.get(OSGI_INSTALL_AREA);
    if (installArea.startsWith(FILE_SCHEME)) {
      installArea = installArea.substring(FILE_SCHEME.length());
    }
    File installBase = new File(installArea);

    String osgiFramework = (String) initialPropertyMap.get(OSGI_FRAMEWORK);
    File osgiFrameworkFile = null;
    if (osgiFramework == null) {

      String path = new File(installBase, "plugins").toString();
      path = searchFor(FRAMEWORK_BUNDLE_NAME, path);
      if (path == null)
        throw new RuntimeException("Could not find framework");

      osgiFrameworkFile = new File(path);
    } else {
      if (osgiFramework.startsWith(FILE_SCHEME)) {
        osgiFramework = osgiFramework.substring(FILE_SCHEME.length());
      }
      osgiFrameworkFile = new File(osgiFramework);
      if (!osgiFrameworkFile.isAbsolute())
        osgiFrameworkFile = new File(installBase, osgiFramework);
    }

    try {
      URL frameworkURL = osgiFrameworkFile.toURL();
      frameworkURLs.add(frameworkURL);

      initialPropertyMap.put(OSGI_FRAMEWORK, frameworkURL.toExternalForm());
    } catch (MalformedURLException e) {
      throw new RuntimeException("Could not find framework -- " + e.getMessage());
    }

    String osgiFrameworkExtensions = (String) initialPropertyMap
      .get(OSGI_FRAMEWORK_EXTENSIONS);
    if (osgiFrameworkExtensions != null) {
      StringTokenizer tokenizer = new StringTokenizer(osgiFrameworkExtensions, ",");
      while (tokenizer.hasMoreTokens()) {
        String extension = tokenizer.nextToken().trim();
        if (extension.length() == 0)
          continue;

        URL extensionURL = findExtensionURL(extension, osgiFrameworkFile);
        if (extensionURL != null) {
          frameworkURLs.add(extensionURL);
        }
      }
    }
    return (URL[]) frameworkURLs.toArray(new URL[frameworkURLs.size()]);
  }

  private URL findExtensionURL(String extension, File osgiFrameworkFile) {
    File extensionFile = null;
    if (extension.startsWith(REFERENCE_SCHEME)) {
      extension = extension.substring(REFERENCE_SCHEME.length());
      if (!extension.startsWith(FILE_SCHEME))
        throw new RuntimeException("Non-file scheme for framework extension URL -- "
          + extension);
      extension = extension.substring(FILE_SCHEME.length());
      extensionFile = new File(extension);
      if (!extensionFile.isAbsolute())
        extensionFile = new File(osgiFrameworkFile.getParentFile(), extension);
    } else {
      String fullExtensionPath = searchFor(extension, osgiFrameworkFile.getParent());
      if (fullExtensionPath == null)
        return null;
      extensionFile = new File(fullExtensionPath);
    }

    try {
      return extensionFile.toURL();
    } catch (MalformedURLException e) {
      throw new RuntimeException("Could not find framework extension -- "
        + extensionFile.getAbsolutePath() + " : " + e.getMessage());
    }
  }

  private void registerRestartHandler(Class starterClazz)
    throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException,
    InvocationTargetException {
    Method registerFrameworkShutdownHandler = null;
    try {
      registerFrameworkShutdownHandler = starterClazz.getDeclaredMethod(
        "internalAddFrameworkShutdownHandler", new Class[] { Runnable.class });
    } catch (NoSuchMethodException e) {

      context
        .log(starterClazz.getName()
          + " does not support setting a shutdown handler. Restart handling is disabled.");
      return;
    }
    if (!registerFrameworkShutdownHandler.isAccessible())
      registerFrameworkShutdownHandler.setAccessible(true);
    Runnable restartHandler = createRestartHandler();
    registerFrameworkShutdownHandler.invoke(null, new Object[] { restartHandler });
  }

  private Runnable createRestartHandler() throws ClassNotFoundException,
    NoSuchMethodException {
    Class frameworkPropertiesClazz = frameworkClassLoader
      .loadClass(FRAMEWORKPROPERTIES);
    final Method getProperty = frameworkPropertiesClazz.getMethod("getProperty",
      new Class[] { String.class });
    Runnable restartHandler = new Runnable() {
      public void run() {
        try {
          String forcedRestart = (String) getProperty.invoke(null,
            new Object[] { OSGI_FORCED_RESTART });
          if (Boolean.valueOf(forcedRestart).booleanValue()) {
            stop();
            start();
          }
        } catch (InvocationTargetException ite) {
          Throwable t = ite.getTargetException();
          if (t == null)
            t = ite;
          throw new RuntimeException(t.getMessage());
        } catch (Exception e) {
          throw new RuntimeException(e.getMessage());
        }
      }
    };
    return restartHandler;
  }

  protected Map buildInitialPropertyMap() {
    Map initialPropertyMap = new HashMap();
    Properties launchProperties = loadProperties(resourceBase + LAUNCH_INI);
    for (Iterator it = launchProperties.entrySet().iterator(); it.hasNext();) {
      Map.Entry entry = (Map.Entry) it.next();
      String key = (String) entry.getKey();
      String value = (String) entry.getValue();
      if (key.endsWith("*")) {
        if (value.equals(NULL_IDENTIFIER)) {
          clearPrefixedSystemProperties(key.substring(0, key.length() - 1),
            initialPropertyMap);
        }
      } else if (value.equals(NULL_IDENTIFIER))
        initialPropertyMap.put(key, null);
      else
        initialPropertyMap.put(entry.getKey(), entry.getValue());
    }

    try {

      if (initialPropertyMap.get(OSGI_INSTALL_AREA) == null)
        initialPropertyMap.put(OSGI_INSTALL_AREA, platformDirectory.toURL()
          .toExternalForm());

      if (initialPropertyMap.get(OSGI_CONFIGURATION_AREA) == null) {
        File configurationDirectory = new File(platformDirectory, "configuration");
        if (!configurationDirectory.exists()) {
          configurationDirectory.mkdirs();
        }
        initialPropertyMap.put(OSGI_CONFIGURATION_AREA, configurationDirectory
          .toURL().toExternalForm());
      }

      if (initialPropertyMap.get(OSGI_INSTANCE_AREA) == null) {
        File workspaceDirectory = new File(platformDirectory, "workspace");
        if (!workspaceDirectory.exists()) {
          workspaceDirectory.mkdirs();
        }
        initialPropertyMap.put(OSGI_INSTANCE_AREA, workspaceDirectory.toURL()
          .toExternalForm());
      }

      Properties configurationProperties = loadConfigurationFile(initialPropertyMap);

      if (initialPropertyMap.get(OSGI_FRAMEWORK) == null) {
        String osgiFramework = configurationProperties.getProperty(OSGI_FRAMEWORK);
        if (osgiFramework != null)
          initialPropertyMap.put(OSGI_FRAMEWORK, osgiFramework);
      }

      if (initialPropertyMap.get(OSGI_FRAMEWORK_EXTENSIONS) == null) {
        String osgiFrameworkExtensions = configurationProperties
          .getProperty(OSGI_FRAMEWORK_EXTENSIONS);
        if (osgiFrameworkExtensions != null)
          initialPropertyMap.put(OSGI_FRAMEWORK_EXTENSIONS, osgiFrameworkExtensions);
      }

    } catch (MalformedURLException e) {
      throw new RuntimeException("Error establishing location");
    }

    return initialPropertyMap;
  }

  private Properties loadConfigurationFile(Map initialPropertyMap) {
    InputStream is = null;
    try {
      String installArea = (String) initialPropertyMap.get(OSGI_INSTALL_AREA);
      if (installArea.startsWith(FILE_SCHEME)) {
        installArea = installArea.substring(FILE_SCHEME.length());
      }
      File installBase = new File(installArea);

      String configurationArea = (String) initialPropertyMap
        .get(OSGI_CONFIGURATION_AREA);
      if (configurationArea.startsWith(FILE_SCHEME)) {
        configurationArea = configurationArea.substring(FILE_SCHEME.length());
      }
      File configurationBase = new File(configurationArea);
      if (!configurationBase.isAbsolute())
        configurationBase = new File(installBase, configurationArea);

      File configurationFile = new File(configurationBase, CONFIG_INI);
      if (!configurationFile.exists())
        return null;

      Properties configProperties = new Properties();
      is = new BufferedInputStream(new FileInputStream(configurationFile));
      configProperties.load(is);
      return configProperties;
    } catch (Throwable t) {
      context.log("Error reading configuration file -- " + t.toString());
      return null;
    } finally {
      if (is != null)
        try {
          is.close();
        } catch (IOException e) {

          e.printStackTrace();
        }
    }
  }

  private static void clearPrefixedSystemProperties(String prefix,
    Map targetPropertyMap) {
    for (Iterator it = System.getProperties().keySet().iterator(); it.hasNext();) {
      String propertyName = (String) it.next();
      if (propertyName.startsWith(prefix)
        && !targetPropertyMap.containsKey(propertyName)) {
        targetPropertyMap.put(propertyName, null);
      }
    }
  }

  protected String[] buildCommandLineArguments() {
    List args = new ArrayList();

    String commandLine = config.getInitParameter(CONFIG_COMMANDLINE);
    if (commandLine != null) {
      StringTokenizer tokenizer = new StringTokenizer(commandLine, WS_DELIM);
      while (tokenizer.hasMoreTokens()) {
        String arg = tokenizer.nextToken();
        if (arg.startsWith("\"")) {
          if (arg.endsWith("\"")) {
            if (arg.length() >= 2) {

              arg = arg.substring(1, arg.length() - 1);
            }
          } else {
            String remainingArg = tokenizer.nextToken("\"");
            arg = arg.substring(1) + remainingArg;

            tokenizer.nextToken(WS_DELIM);
          }
        } else if (arg.startsWith("'")) {
          if (arg.endsWith("'")) {
            if (arg.length() >= 2) {

              arg = arg.substring(1, arg.length() - 1);
            }
          } else {
            String remainingArg = tokenizer.nextToken("'");
            arg = arg.substring(1) + remainingArg;

            tokenizer.nextToken(WS_DELIM);
          }
        }
        args.add(arg);
      }
    }
    return (String[]) args.toArray(new String[] {});
  }

  public synchronized void stop() {
    if (platformDirectory == null) {
      context.log("Shutdown unnecessary. (not deployed)");
      return;
    }

    if (frameworkClassLoader == null) {
      context.log("Framework is already shutdown");
      return;
    }

    ClassLoader original = Thread.currentThread().getContextClassLoader();
    try {
      Class clazz = frameworkClassLoader.loadClass(STARTER);
      Method method = clazz.getDeclaredMethod("shutdown", (Class[]) null);
      Thread.currentThread().setContextClassLoader(frameworkContextClassLoader);
      method.invoke(clazz, (Object[]) null);
      try {
        clazz = this.getClass().getClassLoader()
          .loadClass("org.apache.commons.logging.LogFactory");
        method = clazz.getDeclaredMethod("release",
          new Class[] { ClassLoader.class });
        method.invoke(clazz, new Object[] { frameworkContextClassLoader });
      } catch (ClassNotFoundException e) {
      }

    } catch (Exception e) {
      context.log("Error while stopping Framework", e);
      return;
    } finally {
      frameworkClassLoader.close();
      frameworkClassLoader = null;
      frameworkContextClassLoader = null;
      Thread.currentThread().setContextClassLoader(original);
    }
  }

  protected void copyResource(String resourcePath, File target) {
    if (resourcePath.endsWith("/")) {
      target.mkdir();
      Set paths = context.getResourcePaths(resourcePath);
      if (paths == null)
        return;
      for (Iterator it = paths.iterator(); it.hasNext();) {
        String path = (String) it.next();
        File newFile = new File(target, path.substring(resourcePath.length()));
        copyResource(path, newFile);
      }
    } else {
      try {
        if (target.createNewFile()) {
          InputStream is = null;
          OutputStream os = null;
          try {
            is = context.getResourceAsStream(resourcePath);
            if (is == null)
              return;
            os = new FileOutputStream(target);
            byte[] buffer = new byte[8192];
            int bytesRead = is.read(buffer);
            while (bytesRead != -1) {
              os.write(buffer, 0, bytesRead);
              bytesRead = is.read(buffer);
            }
          } finally {
            if (is != null)
              is.close();

            if (os != null)
              os.close();
          }
        }
      } catch (IOException e) {
        context.log("Error copying resources", e);
      }
    }
  }

  protected static boolean deleteDirectory(File directory) {
    if (directory.isDirectory()) {
      File[] files = directory.listFiles();
      for (int i = 0; i < files.length; i++) {
        if (files[i].isDirectory()) {
          deleteDirectory(files[i]);
        } else {
          files[i].delete();
        }
      }
    }
    return directory.delete();
  }

  public synchronized ClassLoader getFrameworkContextClassLoader() {
    return frameworkContextClassLoader;
  }

  protected synchronized File getPlatformDirectory() {
    return platformDirectory;
  }

  protected Properties loadProperties(String resource) {
    Properties result = new Properties();
    InputStream in = null;
    try {
      URL location = context.getResource(resource);
      if (location != null) {
        in = location.openStream();
        result.load(in);
      }
    } catch (MalformedURLException e) {
    } catch (IOException e) {
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
        }
      }
    }
    return result;
  }

  protected String searchFor(final String target, String start) {
    FileFilter filter = new FileFilter() {
      public boolean accept(File candidate) {
        return candidate.getName().equals(target)
          || candidate.getName().startsWith(target + "_");
      }
    };
    File[] candidates = new File(start).listFiles(filter);
    if (candidates == null)
      return null;
    String[] arrays = new String[candidates.length];
    for (int i = 0; i < arrays.length; i++) {
      arrays[i] = candidates[i].getName();
    }
    int result = findMax(arrays);
    if (result == -1)
      return null;
    return candidates[result].getAbsolutePath().replace(File.separatorChar, '/')
      + (candidates[result].isDirectory() ? "/" : ""); //$NON-NLS-2$
  }

  protected int findMax(String[] candidates) {
    int result = -1;
    Object maxVersion = null;
    for (int i = 0; i < candidates.length; i++) {
      String name = candidates[i];
      String version = ""; // Note: directory with version suffix is always > than directory without version suffix
      int index = name.indexOf('_');
      if (index != -1)
        version = name.substring(index + 1);
      Object currentVersion = getVersionElements(version);
      if (maxVersion == null) {
        result = i;
        maxVersion = currentVersion;
      } else {
        if (compareVersion((Object[]) maxVersion, (Object[]) currentVersion) < 0) {
          result = i;
          maxVersion = currentVersion;
        }
      }
    }
    return result;
  }

  private int compareVersion(Object[] left, Object[] right) {

    int result = ((Integer) left[0]).compareTo((Integer) right[0]); // compare major
    if (result != 0)
      return result;

    result = ((Integer) left[1]).compareTo((Integer) right[1]); // compare minor
    if (result != 0)
      return result;

    result = ((Integer) left[2]).compareTo((Integer) right[2]); // compare service
    if (result != 0)
      return result;

    return ((String) left[3]).compareTo((String) right[3]); // compare qualifier
  }

  private Object[] getVersionElements(String version) {
    if (version.endsWith(DOT_JAR))
      version = version.substring(0, version.length() - 4);
    Object[] result = { new Integer(0), new Integer(0), new Integer(0), "" };
    StringTokenizer t = new StringTokenizer(version, ".");
    String token;
    int i = 0;
    while (t.hasMoreTokens() && i < 4) {
      token = t.nextToken();
      if (i < 3) {
        try {
          result[i++] = new Integer(token);
        } catch (Exception e) {
          break;
        }
      } else {
        result[i++] = token;
      }
    }
    return result;
  }

  protected class ChildFirstURLClassLoader extends CloseableURLClassLoader {

    public ChildFirstURLClassLoader(URL[] urls, ClassLoader parent) {
      super(urls, parent, false);
    }

    public URL getResource(String name) {
      URL resource = findResource(name);
      if (resource == null) {
        ClassLoader parent = getParent();
        if (parent != null)
          resource = parent.getResource(name);
      }
      return resource;
    }

    protected synchronized Class loadClass(String name, boolean resolve)
      throws ClassNotFoundException {
      Class clazz = findLoadedClass(name);
      if (clazz == null) {
        try {
          clazz = findClass(name);
        } catch (ClassNotFoundException e) {
          ClassLoader parent = getParent();
          if (parent != null)
            clazz = parent.loadClass(name);
          else
            clazz = getSystemClassLoader().loadClass(name);
        }
      }

      if (resolve)
        resolveClass(clazz);

      return clazz;
    }

    protected PermissionCollection getPermissions(CodeSource codesource) {
      return allPermissions;
    }
  }

}
