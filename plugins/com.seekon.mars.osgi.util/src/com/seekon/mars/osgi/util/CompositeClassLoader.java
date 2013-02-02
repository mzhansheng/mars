package com.seekon.mars.osgi.util;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

public class CompositeClassLoader extends ClassLoader{
	
	private ClassLoader[] classLoaders;
	
	public CompositeClassLoader(ClassLoader[] classLoaders) {
		super(ClassLoader.getSystemClassLoader());
		this.classLoaders = classLoaders;
	}

	@Override
	protected synchronized Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {
    try {
      ClassLoader parent = getParent();
      if (parent != null) {
        return parent.loadClass(name);
      }

    } catch (Throwable e) {
    }
    for (ClassLoader cl : classLoaders) {
      if(cl == null){
        continue;
      }
      
      try {
        return cl.loadClass(name);
      } catch (Throwable e) {
      }
    }
    throw new ClassNotFoundException(name);
	}
	
	@Override
	public URL getResource(String name) {
		URL resource = super.getResource(name);
    if (resource != null)
      return resource;
    for(ClassLoader cl : classLoaders){
      if(cl == null){
        continue;
      }
      
    	resource = cl.getResource(name);
    	if (resource != null) {
        return resource;
      }
    }
    return null;
	}
	
	@Override
	protected Enumeration<URL> findResources(String name) throws IOException {
		Set<URL>resourceSet = new LinkedHashSet<URL>();
    Enumeration<URL>resources;

    for (ClassLoader cl : classLoaders) {
      if(cl == null){
        continue;
      }
      
      resources = cl.getResources(name);
      if (resources != null) {
        while (resources.hasMoreElements()) {
          resourceSet.add(resources.nextElement());
        }
      }
    }
    return Collections.enumeration(resourceSet);
	}
	
}
