package org.opennms.twissandra.webapp.internal;

import java.util.Date;
import java.util.List;

import org.opennms.twissandra.api.Tweet;
import org.opennms.twissandra.api.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
public class TweetsController {
	
	TweetRepository m_tweetRepository;
	
	public void setTweetRepository(TweetRepository tweetRepository) {
		m_tweetRepository = tweetRepository;
	}

	@RequestMapping("/")
	public String allTweets(Model model) {
		List<Tweet> tweets = m_tweetRepository.getTweets(new Date(), 50);
		
		model.addAttribute("tweets", tweets);
		
		return "tweets";
		
	}

	@RequestMapping("/helloWorld")
	public String helloWorld(Model model) {
		model.addAttribute("message", "Hello World!");
		return "helloWorld";
	}
}