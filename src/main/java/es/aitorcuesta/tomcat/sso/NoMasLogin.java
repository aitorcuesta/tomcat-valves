package es.aitorcuesta.tomcat.sso;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.realm.GenericPrincipal;
import org.apache.catalina.valves.ValveBase;

public class NoMasLogin extends ValveBase {

	private static final String FAKE_USER_STRING = "ssoUser";

	private static final Map<String, Principal> ssoMap = new HashMap<String, Principal>();

	@Override
	public void invoke(Request request, Response response) throws IOException, ServletException {
		String fakeUser = request.getParameter(FAKE_USER_STRING);
		String contextApp = request.getRequestURI().split("/")[1];
		if (null != fakeUser && fakeUser.length() > 0) {
			final Principal principal = new GenericPrincipal(fakeUser, "", new ArrayList<String>());			
			ssoMap.put(contextApp, principal);
		}
		request.setUserPrincipal(ssoMap.get(contextApp));
		getNext().invoke(request, response);
	}
	
}
