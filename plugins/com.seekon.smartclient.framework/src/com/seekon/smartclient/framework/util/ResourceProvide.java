package com.seekon.smartclient.framework.util;

import java.awt.Image;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;


final public class ResourceProvide {
	private static ResourceBundle bundle;

	static {
		bundle = ResourceBundle
				.getBundle("resources.A6", Locale.SIMPLIFIED_CHINESE);
	}

	private ResourceProvide() {
		// 不能实例化
	}

	/**
	 * 根据key返回资源字符串(用来处理国际化问题)
	 * 
	 * @param key
	 * @return String
	 */

	public static String getResourceString(String key) {
		return bundle.getString(key);
	}

	/**
	 * ResourceBundle中读取Mnemonic
	 * 
	 * @param key
	 *          String
	 * @return char
	 */
	public static char getMnemonic(String key) {
		String s = getResourceString(key);
		if (s.length() > 0)
			return s.charAt(0);
		return 0;
	}

	/**
	 * 根据指定的url返回icon对象
	 * 
	 * @param url
	 *          String
	 * @return Icon
	 */
	public static Icon getIcon(String url) {
		return new ImageIcon(ResourceProvide.class.getResource(url));
	}

	/**
	 * 根据url返回Image对象，
	 * 
	 * @param url
	 *          String
	 * @return Image
	 */
	public static Image getImage(String url) {
		try {
			return ImageIO.read(ResourceProvide.class.getResource(url));
		} catch (IOException ex) {
			return null;
		}
	}

}
