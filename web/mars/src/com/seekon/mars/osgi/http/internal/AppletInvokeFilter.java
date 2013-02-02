package com.seekon.mars.osgi.http.internal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.seekon.mars.osgi.bridge.GlobalWebContextHelper;

/**
 * 
 * 访问applet目录下的客户端jar时的过滤器
 * 
 * @author Administrator
 * 
 */
public class AppletInvokeFilter implements Filter {

	private static final String LAST_MODIFIED = "Last-Modified"; //$NON-NLS-1$
	private static final String IF_MODIFIED_SINCE = "If-Modified-Since"; //$NON-NLS-1$
	private static final String IF_NONE_MATCH = "If-None-Match"; //$NON-NLS-1$
	private static final String ETAG = "ETag"; //$NON-NLS-1$

	public void init(FilterConfig config) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {
			HttpServletRequest requestObj = (HttpServletRequest) request;
			String uri = requestObj.getPathInfo();
			if (uri != null && uri.trim().length() > 0) {
				ServletContext sc = GlobalWebContextHelper.getGlobalServletContext();
				InputStream is = null;
				try {
					String resource = uri;
					is = sc.getResourceAsStream(uri);
					if (is == null) {
						resource = getResource(uri, "/WEB-INF/eclipse/plugins");// 公共的服务接口及model从plugins下访问
						is = sc.getResourceAsStream(resource);
						if (is == null) {
							resource = getResource(uri, "/WEB-INF/lib");
							is = sc.getResourceAsStream(resource);
						}
					}
					if (is != null) {
						this.writeResource(requestObj, (HttpServletResponse) response,
								resource, sc.getResource(resource));
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (is != null) {
						is.close();
						response.getOutputStream().close();
					}
				}
			}
		}
		chain.doFilter(request, response);
	}

	private String getResource(String uri, String baseResource) {
		String resource = baseResource;
		int pos = uri.lastIndexOf("/");
		if (pos > 0) {
			resource += uri.substring(pos, uri.length());
		} else {
			resource += uri;
		}
		return resource;
	}

	private void writeResource(final HttpServletRequest req,
			final HttpServletResponse resp, final String resourcePath,
			final URL resourceURL) throws Exception {
		try {
			URLConnection connection = resourceURL.openConnection();
			long lastModified = connection.getLastModified();
			int contentLength = connection.getContentLength();

			String etag = null;
			if (lastModified != -1 && contentLength != -1)
				etag = "W/\"" + contentLength + "-" + lastModified + "\""; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$

			// Check for cache revalidation.
			// We should prefer ETag validation as the guarantees are stronger and all
			// HTTP 1.1 clients should be using it
			String ifNoneMatch = req.getHeader(IF_NONE_MATCH);
			if (ifNoneMatch != null && etag != null
					&& ifNoneMatch.indexOf(etag) != -1) {
				resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				return;
			}

			long ifModifiedSince = -1;
			try {
				ifModifiedSince = req.getDateHeader(IF_MODIFIED_SINCE);
			} catch (Exception e) {
				try {
					ifModifiedSince = new Date(req.getHeader(IF_MODIFIED_SINCE))
							.getTime();
				} catch (Exception e2) {

				}
			}
			// for purposes of comparison we add 999 to ifModifiedSince since the
			// fidelity
			// of the IMS header generally doesn't include milli-seconds
			if (ifModifiedSince > -1 && lastModified > 0
					&& lastModified <= (ifModifiedSince + 999)) {
				resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				return;
			}

			// return the full contents regularly
			if (contentLength != -1)
				resp.setContentLength(contentLength);

			String contentType = GlobalWebContextHelper.getGlobalServletContext()
					.getMimeType(resourcePath);
			if (contentType != null)
				resp.setContentType(contentType);

			if (lastModified > 0)
				resp.setDateHeader(LAST_MODIFIED, lastModified);

			if (etag != null)
				resp.setHeader(ETAG, etag);

			if (contentLength != 0) {
				// open the input stream
				InputStream is = null;
				try {
					is = connection.getInputStream();
					// write the resource
					try {
						OutputStream os = resp.getOutputStream();
						int writtenContentLength = writeResourceToOutputStream(is, os);
						if (contentLength == -1 || contentLength != writtenContentLength)
							resp.setContentLength(writtenContentLength);
					} catch (IllegalStateException e) { // can occur if the response
																							// output is already open as a
																							// Writer
						Writer writer = resp.getWriter();
						writeResourceToWriter(is, writer);
						// Since ContentLength is a measure of the number of bytes contained
						// in the body
						// of a message when we use a Writer we lose control of the exact
						// byte count and
						// defer the problem to the Servlet Engine's Writer implementation.
					}
				} catch (FileNotFoundException e) {
					// FileNotFoundException may indicate the following scenarios
					// - url is a directory
					// - url is not accessible
					sendError(resp, HttpServletResponse.SC_FORBIDDEN);
				} catch (SecurityException e) {
					// SecurityException may indicate the following scenarios
					// - url is not accessible
					sendError(resp, HttpServletResponse.SC_FORBIDDEN);
				} finally {
					if (is != null)
						try {
							is.close();
						} catch (IOException e) {
							// ignore
						}
				}
			}

		} catch (Exception e) {
			throw e;
		}
	}

	void sendError(final HttpServletResponse resp, int sc) throws IOException {

		try {
			// we need to reset headers for 302 and 403
			resp.reset();
			resp.sendError(sc);
		} catch (IllegalStateException e) {
			// this could happen if the response has already been committed
		}
	}

	int writeResourceToOutputStream(InputStream is, OutputStream os)
			throws IOException {
		byte[] buffer = new byte[8192];
		int bytesRead = is.read(buffer);
		int writtenContentLength = 0;
		while (bytesRead != -1) {
			os.write(buffer, 0, bytesRead);
			writtenContentLength += bytesRead;
			bytesRead = is.read(buffer);
		}
		return writtenContentLength;
	}

	void writeResourceToWriter(InputStream is, Writer writer) throws IOException {
		Reader reader = new InputStreamReader(is);
		try {
			char[] buffer = new char[8192];
			int charsRead = reader.read(buffer);
			while (charsRead != -1) {
				writer.write(buffer, 0, charsRead);
				charsRead = reader.read(buffer);
			}
		} finally {
			if (reader != null) {
				reader.close(); // will also close input stream
			}
		}
	}

}
