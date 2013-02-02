package com.seekon.mars.web.proxy.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.caucho.hessian.io.AbstractHessianInput;
import com.caucho.hessian.io.AbstractHessianOutput;
import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.caucho.hessian.io.SerializerFactory;
import com.caucho.hessian.server.HessianSkeleton;
import com.google.inject.Singleton;
import com.seekon.mars.osgi.util.CompositeClassLoader;

@Singleton
public class HessianInvokeServlet extends AbstractHttpServlet {

	private static final long serialVersionUID = 7559312894090408081L;

	public HessianInvokeServlet() {

	}

	public void init() throws ServletException {
		super.init();
	}

	public SerializerFactory getSerializerFactory(ClassLoader cl) {
		return new SerializerFactory(cl);
	}

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	  initApplicationContextSession(request);//TODO:
	  
	  String serviceId = this.getServiceId(request, ".hessian");

		HttpServletRequest req = request;
		HttpServletResponse res = response;

		if (!req.getMethod().equals("POST")) {
			res.setStatus(500, "Hessian Requires POST");
			PrintWriter out = res.getWriter();

			res.setContentType("text/html");
			out.println("<h1>Hessian Requires POST</h1>");
			return;
		}
		try {
			InputStream is = request.getInputStream();
			OutputStream os = response.getOutputStream();
			response.setContentType("application/x-hessian");
			int code = is.read();
			int major;
			int minor;

			AbstractHessianInput in;
			AbstractHessianOutput out;

			if (code == 'H') {
				major = is.read();
				minor = is.read();

				if (major != 0x02 || minor != 0x00)
					throw new IOException("Version " + major + "." + minor
							+ " is not understood");

				in = createHessian2Input(is);
				out = new Hessian2Output(os);

				in.readCall();
			} else if (code == 'c') {
				major = is.read();
				minor = is.read();

				in = new HessianInput(is);

				if (major >= 2)
					out = new Hessian2Output(os);
				else
					out = new HessianOutput(os);
			} else {
				throw new IOException(
						"expected 'H' (Hessian 2.0) or 'c' (Hessian 1.0) in hessian input at "
								+ code);
			}

			Object service = this.getExportedService(serviceId);
			SerializerFactory serializerFactory = getSerializerFactory(new CompositeClassLoader(
					new ClassLoader[] { service.getClass().getClassLoader(),
							Thread.currentThread().getContextClassLoader() }));
			in.setSerializerFactory(serializerFactory);
			out.setSerializerFactory(serializerFactory);

			new HessianSkeleton(service, service.getClass()).invoke(in, out);
			if(serviceId.equals("com.seekon.system.auth.spi.LoginService")){
			  initHttpSession(request);
			}
		} catch (Throwable e) {
			throw new ServletException(e);
		}
	}

	protected Hessian2Input createHessian2Input(InputStream is) {
		return new Hessian2Input(is);
	}
	
}
