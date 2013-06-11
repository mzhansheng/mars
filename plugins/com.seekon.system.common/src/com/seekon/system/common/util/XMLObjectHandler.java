package com.seekon.system.common.util;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.seekon.mars.osgi.util.CompositeClassLoader;

public class XMLObjectHandler {

  public static <T> T readObject(Class<T> t, byte[] is) {
    XMLDecoder decoder = null;
    ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
    try {
      Thread.currentThread().setContextClassLoader(
        new CompositeClassLoader(new ClassLoader[] { t.getClassLoader(),
          XMLObjectHandler.class.getClassLoader(), originalClassLoader }));
      decoder = new XMLDecoder(new ByteArrayInputStream(is));
      return (T) decoder.readObject();
    } catch (Throwable e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } finally {
      if (decoder != null) {
        decoder.close();
      }
      Thread.currentThread().setContextClassLoader(originalClassLoader);
    }
  }

  public static byte[] writeObject(Object t) {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    XMLEncoder encoder = new XMLEncoder(os);

    ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
    try {
      Thread.currentThread().setContextClassLoader(
        new CompositeClassLoader(new ClassLoader[] { t.getClass().getClassLoader(),
          XMLObjectHandler.class.getClassLoader(), originalClassLoader }));
      encoder.writeObject(t);
      encoder.close();
    } finally {
      Thread.currentThread().setContextClassLoader(originalClassLoader);
    }
    return os.toByteArray();
  }
}
