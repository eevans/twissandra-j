package org.opennms.twissandra.webapp.internal;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opennms.twissandra.api.Tweet;
import org.opennms.twissandra.api.TweetRepository;

public class TweetsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		TweetRepository repository = JNDIHelper.getTweetRepository();
		
		List<Tweet> tweets = repository.getTweets(new Date(), 40);
		
		req.setAttribute("tweets", tweets);

		getServletConfig().getServletContext().getRequestDispatcher("/WEB-INF/jsps/tweets.jsp").forward(req, resp);
		
	}
	
	

}
