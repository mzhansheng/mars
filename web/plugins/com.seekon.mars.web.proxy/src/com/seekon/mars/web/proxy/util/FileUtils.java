package com.seekon.mars.web.proxy.util;

import java.io.File;

import javax.servlet.ServletContext;

public class FileUtils {

	public static long getFileLastModifiedTime(String fileName,
			ServletContext context) {
		String filePath = context.getRealPath("/client/plugins/" + fileName);
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			filePath = context.getRealPath("/WEB-INF/eclipse/plugins/" + fileName);
			file = new File(filePath);
		}
		if (!file.exists() || !file.isFile()) {
			return 0;
		}
		return file.lastModified();
	}
}
