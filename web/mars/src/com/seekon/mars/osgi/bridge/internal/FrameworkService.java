package com.seekon.mars.osgi.bridge.internal;


/**
 * 
 * @author Administrator
 * @deprecated
 */
public final class FrameworkService {

//	private static final String REFERENCE_SCHEME = "reference:";
//	protected static final String NULL_IDENTIFIER = "@null";
//	protected static final String LAUNCH_INI = "launch.ini";
//	private static final String DOT_JAR = ".jar";
//	protected static final String OSGI_INSTANCE_AREA = "osgi.instance.area";
//	protected static final String OSGI_INSTALL_AREA = "osgi.install.area";
//	protected static final String OSGI_CONFIGURATION_AREA = "osgi.configuration.area";
//	protected static final String OSGI_FRAMEWORK_EXTENSIONS = "osgi.framework.extensions";
//	protected static final String FILE_SCHEME = "file:";
//	private static final String CONFIG_INI = "config.ini";
//	protected static final String RESOURCE_BASE = "/WEB-INF/";
//	protected static final String ECLIPSE = "eclipse/";
//	private static final String EXTENSIONBUNDLE_DEFAULT_BSN = "org.eclipse.equinox.servletbridge.extensionbundle";
//	private static final String EXTENSIONBUNDLE_DEFAULT_VERSION = "1.2.0";
//	private static final String MANIFEST_VERSION = "Manifest-Version";
//	private static final String BUNDLE_MANIFEST_VERSION = "Bundle-ManifestVersion";
//	private static final String BUNDLE_NAME = "Bundle-Name";
//	private static final String BUNDLE_SYMBOLIC_NAME = "Bundle-SymbolicName";
//	private static final String BUNDLE_VERSION = "Bundle-Version";
//	private static final String FRAGMENT_HOST = "Fragment-Host";
//	private static final String EXPORT_PACKAGE = "Export-Package";
//	protected static final String OSGI_FRAMEWORK = "osgi.framework";
//	protected static final String FRAMEWORK_BUNDLE_NAME = "org.eclipse.osgi";
//
//	private final ServletContext servletContext;
//	protected String resourceBase;
//	private File platformDirectory;
//
//	public FrameworkService(ServletContext context) {
//		this.servletContext = context;
//		initResourceBase();
//		platformDirectory = new File(context.getRealPath(resourceBase));
//		deployExtensionBundle(new File(platformDirectory, "plugins"));
//	}
//
//	public void start() {
//		try {
//			doStart();
//		} catch (Exception e) {
//			servletContext.log(e, "osgi framework start error");
//			e.printStackTrace();
//		}
//	}
//
//	public void stop() {
//		try {
//			doStop();
//		} catch (Exception e) {
//			servletContext.log(e, "osgi framework stop error");
//			e.printStackTrace();
//		}
//	}
//
//	private void doStart() throws Exception {
//		Map<String, String> initialPropertyMap = buildInitialPropertyMap();
//		EclipseStarter.setInitialProperties(initialPropertyMap);
//		EclipseStarter.startup(new String[] { "-clean", "-console" }, null);
//		BundleContext bundleContext = EclipseStarter.getSystemBundleContext();
//		this.servletContext.setAttribute(BundleContext.class.getName(), bundleContext);
//		bundleContext.registerService(ServletContext.class.getName(), this.servletContext, null);
//	}
//
//	private void doStop() throws Exception {
//		EclipseStarter.shutdown();
//	}
//
//	protected void initResourceBase() {
//		try {
//			if (servletContext.getResource(RESOURCE_BASE + LAUNCH_INI) != null) {
//				resourceBase = RESOURCE_BASE;
//				return;
//			}
//			if (servletContext.getResource(RESOURCE_BASE + ECLIPSE + LAUNCH_INI) != null) {
//				resourceBase = RESOURCE_BASE + ECLIPSE;
//				return;
//			}
//		} catch (MalformedURLException e) {
//		}
//		resourceBase = RESOURCE_BASE;
//	}
//
//	protected Map<String, String> buildInitialPropertyMap() {
//		Map initialPropertyMap = new HashMap();
//		Properties launchProperties = loadProperties(resourceBase + LAUNCH_INI);
//		for (Iterator it = launchProperties.entrySet().iterator(); it.hasNext();) {
//			Map.Entry entry = (Map.Entry) it.next();
//			String key = (String) entry.getKey();
//			String value = (String) entry.getValue();
//			if (key.endsWith("*")) {
//				if (value.equals(NULL_IDENTIFIER)) {
//					clearPrefixedSystemProperties(key.substring(0, key.length() - 1),
//							initialPropertyMap);
//				}
//			} else if (value.equals(NULL_IDENTIFIER))
//				initialPropertyMap.put(key, null);
//			else
//				initialPropertyMap.put(entry.getKey(), entry.getValue());
//		}
//
//		try {
//
//			if (initialPropertyMap.get(OSGI_INSTALL_AREA) == null)
//				initialPropertyMap.put(OSGI_INSTALL_AREA, platformDirectory.toURL()
//						.toExternalForm());
//
//			if (initialPropertyMap.get(OSGI_CONFIGURATION_AREA) == null) {
//				File configurationDirectory = new File(platformDirectory,
//						"configuration");
//				if (!configurationDirectory.exists()) {
//					configurationDirectory.mkdirs();
//				}
//				initialPropertyMap.put(OSGI_CONFIGURATION_AREA, configurationDirectory
//						.toURL().toExternalForm());
//			}
//
//			if (initialPropertyMap.get(OSGI_INSTANCE_AREA) == null) {
//				File workspaceDirectory = new File(platformDirectory, "workspace");
//				if (!workspaceDirectory.exists()) {
//					workspaceDirectory.mkdirs();
//				}
//				initialPropertyMap.put(OSGI_INSTANCE_AREA, workspaceDirectory.toURL()
//						.toExternalForm());
//			}
//
//			Properties configurationProperties = loadConfigurationFile(initialPropertyMap);
//
//			if (initialPropertyMap.get(OSGI_FRAMEWORK_EXTENSIONS) == null) {
//				String osgiFrameworkExtensions = configurationProperties
//						.getProperty(OSGI_FRAMEWORK_EXTENSIONS);
//				if (osgiFrameworkExtensions != null)
//					initialPropertyMap.put(OSGI_FRAMEWORK_EXTENSIONS,
//							osgiFrameworkExtensions);
//			}
//			initialPropertyMap.put(EclipseStarter.PROP_SYSPATH, new File(
//					platformDirectory, "plugins").getAbsolutePath());
//
//			handleFrameworkExtensionProperty(initialPropertyMap);
//
//		} catch (MalformedURLException e) {
//			throw new RuntimeException("Error establishing location");
//		}
//
//		return initialPropertyMap;
//	}
//
//	protected Properties loadProperties(String resource) {
//		Properties result = new Properties();
//		InputStream in = null;
//		try {
//			URL location = servletContext.getResource(resource);
//			if (location != null) {
//				in = location.openStream();
//				result.load(in);
//			}
//		} catch (MalformedURLException e) {
//		} catch (IOException e) {
//		} finally {
//			if (in != null) {
//				try {
//					in.close();
//				} catch (IOException e) {
//				}
//			}
//		}
//		return result;
//	}
//
//	private static void clearPrefixedSystemProperties(String prefix,
//			Map targetPropertyMap) {
//		for (Iterator it = System.getProperties().keySet().iterator(); it.hasNext();) {
//			String propertyName = (String) it.next();
//			if (propertyName.startsWith(prefix)
//					&& !targetPropertyMap.containsKey(propertyName)) {
//				targetPropertyMap.put(propertyName, null);
//			}
//		}
//	}
//
//	private Properties loadConfigurationFile(Map initialPropertyMap) {
//		InputStream is = null;
//		try {
//			String installArea = (String) initialPropertyMap.get(OSGI_INSTALL_AREA);
//			if (installArea.startsWith(FILE_SCHEME)) {
//				installArea = installArea.substring(FILE_SCHEME.length());
//			}
//			File installBase = new File(installArea);
//
//			String configurationArea = (String) initialPropertyMap
//					.get(OSGI_CONFIGURATION_AREA);
//			if (configurationArea.startsWith(FILE_SCHEME)) {
//				configurationArea = configurationArea.substring(FILE_SCHEME.length());
//			}
//			File configurationBase = new File(configurationArea);
//			if (!configurationBase.isAbsolute())
//				configurationBase = new File(installBase, configurationArea);
//
//			File configurationFile = new File(configurationBase, CONFIG_INI);
//			if (!configurationFile.exists())
//				return null;
//
//			Properties configProperties = new Properties();
//			is = new BufferedInputStream(new FileInputStream(configurationFile));
//			configProperties.load(is);
//			return configProperties;
//		} catch (Throwable t) {
//			servletContext.log("Error reading configuration file -- " + t.toString());
//			return null;
//		} finally {
//			if (is != null)
//				try {
//					is.close();
//				} catch (IOException e) {
//
//					e.printStackTrace();
//				}
//		}
//	}
//
//	private void deployExtensionBundle(File plugins) {
//		final String extensionBundleBSN = EXTENSIONBUNDLE_DEFAULT_BSN;
//		File extensionBundleFile = findExtensionBundleFile(plugins,
//				extensionBundleBSN);
//
//		if (extensionBundleFile == null) {
//			generateExtensionBundle(plugins, extensionBundleBSN,
//					EXTENSIONBUNDLE_DEFAULT_VERSION);
//		} else {
//			processExtensionBundle(extensionBundleFile);
//		}
//	}
//
//	private void generateExtensionBundle(File plugins, String extensionBundleBSN,
//			String extensionBundleVersion) {
//		Manifest mf = new Manifest();
//		Attributes attribs = mf.getMainAttributes();
//		attribs.putValue(MANIFEST_VERSION, "1.0");
//		attribs.putValue(BUNDLE_MANIFEST_VERSION, "2");
//		attribs.putValue(BUNDLE_NAME, "Servletbridge Extension Bundle");
//		attribs.putValue(BUNDLE_SYMBOLIC_NAME, extensionBundleBSN);
//		attribs.putValue(BUNDLE_VERSION, extensionBundleVersion);
//		attribs.putValue(FRAGMENT_HOST, "system.bundle; extension:=framework");
//
//		String servletVersion = servletContext.getMajorVersion() + "."
//				+ servletContext.getMinorVersion();
//		servletVersion = "2.5";
//		String packageExports = "javax.servlet; version=" + servletVersion
//				+ ", javax.servlet.http; version=" + servletVersion
//				+ ", javax.servlet.resources; version=" + servletVersion
//				+ ", com.seekon.mars.osgi.bridge";
//		
//		attribs.putValue(EXPORT_PACKAGE, packageExports);
//		writeJarFile(new File(plugins, extensionBundleBSN + "_"
//				+ extensionBundleVersion + DOT_JAR), mf);
//	}
//
//	private File findExtensionBundleFile(File plugins,
//			final String extensionBundleBSN) {
//		FileFilter extensionBundleFilter = new FileFilter() {
//			public boolean accept(File candidate) {
//				return candidate.getName().startsWith(extensionBundleBSN + "_");
//			}
//		};
//		File[] extensionBundles = plugins.listFiles(extensionBundleFilter);
//		if (extensionBundles.length == 0)
//			return null;
//
//		if (extensionBundles.length > 1) {
//			for (int i = 1; i < extensionBundles.length; i++) {
//				if (extensionBundles[i].isDirectory()) {
//					deleteDirectory(extensionBundles[i]);
//				} else {
//					extensionBundles[i].delete();
//				}
//			}
//		}
//		return extensionBundles[0];
//	}
//
//	protected static boolean deleteDirectory(File directory) {
//		if (directory.isDirectory()) {
//			File[] files = directory.listFiles();
//			for (int i = 0; i < files.length; i++) {
//				if (files[i].isDirectory()) {
//					deleteDirectory(files[i]);
//				} else {
//					files[i].delete();
//				}
//			}
//		}
//		return directory.delete();
//	}
//
//	private void processExtensionBundle(File extensionBundleFile) {
//		String fileName = extensionBundleFile.getName();
//		if (fileName.endsWith(DOT_JAR)) {
//			Manifest mf = readJarFile(extensionBundleFile);
//			if (mf == null)
//				return;
//			Attributes attributes = mf.getMainAttributes();
//			String exportPackage = (String) attributes.remove(new Name(
//					"X-Deploy-Export-Package"));
//			if (exportPackage != null) {
//				attributes.putValue("Export-Package", exportPackage);
//				writeJarFile(extensionBundleFile, mf);
//			}
//		}
//	}
//
//	private void writeJarFile(File jarFile, Manifest mf) {
//		try {
//			JarOutputStream jos = null;
//			try {
//				jos = new JarOutputStream(new FileOutputStream(jarFile), mf);
//				jos.finish();
//			} finally {
//				if (jos != null)
//					jos.close();
//			}
//		} catch (IOException e) {
//			servletContext.log("Error writing extension bundle", e);
//		}
//	}
//
//	private Manifest readJarFile(File jarFile) {
//		try {
//			JarInputStream jis = null;
//			try {
//				jis = new JarInputStream(new FileInputStream(jarFile));
//				return jis.getManifest();
//			} finally {
//				if (jis != null)
//					jis.close();
//			}
//		} catch (IOException e) {
//			servletContext.log("Error reading extension bundle", e);
//		}
//		return null;
//	}
//
//	private void handleFrameworkExtensionProperty(Map initialPropertyMap) {
//		String osgiFrameworkExtensions = (String) initialPropertyMap
//				.get(OSGI_FRAMEWORK_EXTENSIONS);
//		if (osgiFrameworkExtensions != null) {
//			// List frameworkExtensionBundles = new ArrayList();
//			StringBuffer frameworkExtensionPath = new StringBuffer();
//
//			String installArea = (String) initialPropertyMap.get(OSGI_INSTALL_AREA);
//			if (installArea.startsWith(FILE_SCHEME)) {
//				installArea = installArea.substring(FILE_SCHEME.length());
//			}
//			File installBase = new File(installArea);
//			String osgiFramework = (String) initialPropertyMap.get(OSGI_FRAMEWORK);
//			File osgiFrameworkFile = null;
//			if (osgiFramework == null) {
//				String path = new File(installBase.getParent(), "lib").toString();
//				path = searchFor(FRAMEWORK_BUNDLE_NAME, path);
//				if (path == null) {
//					path = new File(installBase, "plugins").toString();
//					path = searchFor(FRAMEWORK_BUNDLE_NAME, path);
//					if (path == null) {
//						throw new RuntimeException("Could not find framework");
//					}
//				}
//				osgiFrameworkFile = new File(path);
//			} else {
//				if (osgiFramework.startsWith(FILE_SCHEME)) {
//					osgiFramework = osgiFramework.substring(FILE_SCHEME.length());
//				}
//				osgiFrameworkFile = new File(osgiFramework);
//				if (!osgiFrameworkFile.isAbsolute())
//					osgiFrameworkFile = new File(installBase, osgiFramework);
//			}
//
//			StringTokenizer tokenizer = new StringTokenizer(osgiFrameworkExtensions,
//					",");
//			while (tokenizer.hasMoreTokens()) {
//				String extension = tokenizer.nextToken().trim();
//				if (extension.length() == 0)
//					continue;
//
//				URL extensionURL = findExtensionURL(extension, osgiFrameworkFile);
//				if (extensionURL != null) {
//					frameworkExtensionPath.append("," + extensionURL.toExternalForm());
//				}
//			}
//			if (frameworkExtensionPath.length() > 0) {
//				initialPropertyMap.put(OSGI_FRAMEWORK_EXTENSIONS,
//						frameworkExtensionPath.substring(1));
//			}
//		}
//	}
//
//	private URL findExtensionURL(String extension, File osgiFrameworkFile) {
//		File extensionFile = null;
//		if (extension.startsWith(REFERENCE_SCHEME)) {
//			extension = extension.substring(REFERENCE_SCHEME.length());
//			if (!extension.startsWith(FILE_SCHEME))
//				throw new RuntimeException(
//						"Non-file scheme for framework extension URL -- " + extension);
//			extension = extension.substring(FILE_SCHEME.length());
//			extensionFile = new File(extension);
//			if (!extensionFile.isAbsolute())
//				extensionFile = new File(osgiFrameworkFile.getParentFile(), extension);
//		} else {
//			String fullExtensionPath = searchFor(extension,
//					osgiFrameworkFile.getParent());
//			if (fullExtensionPath == null)
//				return null;
//			extensionFile = new File(fullExtensionPath);
//		}
//
//		try {
//			return extensionFile.toURL();
//		} catch (MalformedURLException e) {
//			throw new RuntimeException("Could not find framework extension -- "
//					+ extensionFile.getAbsolutePath() + " : " + e.getMessage());
//		}
//	}
//
//	protected String searchFor(final String target, String start) {
//		FileFilter filter = new FileFilter() {
//			public boolean accept(File candidate) {
//				return candidate.getName().equals(target)
//						|| candidate.getName().startsWith(target + "_");
//			}
//		};
//		File[] candidates = new File(start).listFiles(filter);
//		if (candidates == null)
//			return null;
//		String[] arrays = new String[candidates.length];
//		for (int i = 0; i < arrays.length; i++) {
//			arrays[i] = candidates[i].getName();
//		}
//		int result = findMax(arrays);
//		if (result == -1)
//			return null;
//		return candidates[result].getAbsolutePath()
//				.replace(File.separatorChar, '/')
//				+ (candidates[result].isDirectory() ? "/" : ""); //$NON-NLS-2$
//	}
//
//	protected int findMax(String[] candidates) {
//		int result = -1;
//		Object maxVersion = null;
//		for (int i = 0; i < candidates.length; i++) {
//			String name = candidates[i];
//			String version = ""; // Note: directory with version suffix is always >
//														// than directory without version suffix
//			int index = name.indexOf('_');
//			if (index != -1)
//				version = name.substring(index + 1);
//			Object currentVersion = getVersionElements(version);
//			if (maxVersion == null) {
//				result = i;
//				maxVersion = currentVersion;
//			} else {
//				if (compareVersion((Object[]) maxVersion, (Object[]) currentVersion) < 0) {
//					result = i;
//					maxVersion = currentVersion;
//				}
//			}
//		}
//		return result;
//	}
//
//	private int compareVersion(Object[] left, Object[] right) {
//
//		int result = ((Integer) left[0]).compareTo((Integer) right[0]); // compare
//																																		// major
//		if (result != 0)
//			return result;
//
//		result = ((Integer) left[1]).compareTo((Integer) right[1]); // compare minor
//		if (result != 0)
//			return result;
//
//		result = ((Integer) left[2]).compareTo((Integer) right[2]); // compare
//																																// service
//		if (result != 0)
//			return result;
//
//		return ((String) left[3]).compareTo((String) right[3]); // compare qualifier
//	}
//
//	private Object[] getVersionElements(String version) {
//		if (version.endsWith(DOT_JAR))
//			version = version.substring(0, version.length() - 4);
//		Object[] result = { new Integer(0), new Integer(0), new Integer(0), "" };
//		StringTokenizer t = new StringTokenizer(version, ".");
//		String token;
//		int i = 0;
//		while (t.hasMoreTokens() && i < 4) {
//			token = t.nextToken();
//			if (i < 3) {
//				try {
//					result[i++] = new Integer(token);
//				} catch (Exception e) {
//					break;
//				}
//			} else {
//				result[i++] = token;
//			}
//		}
//		return result;
//	}
}
