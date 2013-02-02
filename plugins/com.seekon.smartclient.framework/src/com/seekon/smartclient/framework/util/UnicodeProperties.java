package com.seekon.smartclient.framework.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Properties;

public class UnicodeProperties extends Properties {
	private static final long serialVersionUID = -3638738318148578013L;

	/**
	 * 转换 abc\u4f60\u662f\u8c01 形式的字符串为正常字符串，
	 * 如：toAscii("\\u54c8\\u54c8\\uff0c\\u6211\\u5728\\u6d4b\\u8bd5\\u5440!!!"
	 * );结果为 哈哈，我在测试呀!!!
	 * 
	 * @param s
	 *          String
	 * @return String
	 */
	private String toNative(String s) {
		StringBuffer buff = new StringBuffer();
		String[] strings = s.split("\\\\");
		for (String str : strings) {
			if (str.startsWith("u")) {
				if (str.length() == 5) {
					buff.append((char) Long.decode(str.replaceFirst("u", "0x"))
							.intValue());
				} else {
					buff.append((char) Long.decode("0x" + str.substring(1, 5)).intValue());
					buff.append(str.substring(5));
				}
			} else {
				buff.append(str);
			}
		}
		return buff.toString();
	}

	/**
	 * 将字符串转为 abc\u4f60\u662f\u8c01 形式返回，如：toNative("哈哈，我在测试呀!!!")，结果为
	 * \\u54c8\\u54c8\\uff0c\\u6211\\u5728\\u6d4b\\u8bd5\\u5440!!!
	 * 
	 * @param str
	 *          String
	 * @return String
	 */
	private String toAscii1(String str) {
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < str.length();) {
			char a = str.charAt(i++);
			if ((int) a > 0xFF) {
				buff.append("\\u" + Integer.toHexString((int) a));
			} else {
				buff.append(a);
			}
		}
		return buff.toString();
	}

	public UnicodeProperties() {
		super();
	}

	public UnicodeProperties(Properties defaults) {
		super(defaults);
	}

	/**
	 * 从fileName指定的文件加载属性
	 * 
	 * @param fileName
	 *          String
	 * @throws IOException
	 */
	public synchronized void load(String fileName) throws IOException {
		load(new File(fileName));
	}

	/**
	 * 从file加载属性
	 * 
	 * @param file
	 *          File
	 * @throws IOException
	 */
	public synchronized void load(File file) throws IOException {
		FileInputStream stream = new FileInputStream(file);
		load(stream);
		stream.close();
	}

	/**
	 * 覆盖load，支持Unicode编码
	 * 
	 * @param inStream
	 *          InputStream
	 * @throws IOException
	 */
	public synchronized void load(InputStream inStream) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(inStream));
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintWriter out = new PrintWriter(bos);
		for (String line = null; (line = in.readLine()) != null;) {
			out.println(toAscii1(line));
		}
		out.flush();
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		super.load(bis);
		out.close();
	}

	/**
	 * 覆盖store，支持Unicode编码
	 * 
	 * @param out
	 *          OutputStream
	 * @param comments
	 *          String
	 * @throws IOException
	 */
	public synchronized void store(OutputStream out, String comments)
			throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		super.store(bos, toAscii1(comments));
		bos.flush();
		PrintWriter pw = new PrintWriter(out);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				new ByteArrayInputStream(bos.toByteArray())));
		for (String line = null; (line = in.readLine()) != null;) {
			pw.println(toNative(line));
		}
		pw.flush();
		bos.close();
		in.close();
	}

	/**
	 * 将前属性写入到File
	 * 
	 * @param file
	 *          File
	 * @param comments
	 *          String
	 * @throws IOException
	 */
	public synchronized void store(File file, String comments) throws IOException {
		FileOutputStream stream = new FileOutputStream(file);
		store(stream, comments);
		stream.close();
	}

	/**
	 * 将属性写入到fileName指定的文件
	 * 
	 * @param fileName
	 *          String
	 * @param comments
	 *          String
	 * @throws IOException
	 */
	public synchronized void store(String fileName, String comments)
			throws IOException {
		store(new File(fileName), comments);
	}

	/**
	 * 覆盖Properties.getProperty返回带变量的属性值，如：${project.name}/src
	 * 
	 * @param key
	 *          String
	 * @return String
	 */
	public String getProperty(String key) {
		String result = super.getProperty(key);
		if (result == null)
			return null;
		// 值中是否存在变量
		int pos = result.indexOf("${");
		while (pos > -1) {
			String k = result.substring(pos + 2, result.indexOf("}"));
			String v = getProperty(k);
			if (v == null)
				return null;
			result = result.replace("${" + k + "}", v);
			pos = result.indexOf("${");
		}
		return result;
	}

	public static void main(String[] args) throws IOException {
		UnicodeProperties unicodeproperties = new UnicodeProperties();
		// 以Unicode写入文件
		unicodeproperties.setProperty("name", "曼灵格(manlge)");
		unicodeproperties.store("C:/test.conf", "哈哈，我在测试呀!!!");
		Runtime.getRuntime().exec("notepad C:\\test.conf");

		// 从文件读入
		unicodeproperties.load("c:/test.conf");

		// 读取name
		System.out.println(unicodeproperties.getProperty("name"));

		// 测试带变量引用的属性值
		unicodeproperties.put("project", "A6");
		unicodeproperties.put("project.src", "${project}/src");
		System.out.println(unicodeproperties.getProperty("project.src"));

		System.out.println(unicodeproperties.getProperty("project.src", "abc"));
	}

}
