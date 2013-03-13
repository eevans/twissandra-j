package org.opennms.twissandra.webapp.internal;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opennms.twissandra.api.TweetRepository;

public class TweetsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		TweetRepository repository = JNDIHelper.getTweetRepository();
		
		
		resp.getOutputStream().println("I made it here at least!" + repository);
	}
	
	

}
