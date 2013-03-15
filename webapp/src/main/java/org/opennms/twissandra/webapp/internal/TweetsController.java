package org.opennms.twissandra.webapp.internal;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.opennms.twissandra.api.Tweet;
import org.opennms.twissandra.api.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
public class TweetsController {
	
	TweetRepository m_tweetRepository;
	
	public void setTweetRepository(TweetRepository tweetRepository) {
		m_tweetRepository = tweetRepository;
	}
	
	@RequestMapping("/index.htm")
	public String allTweets(Model model) {
		List<Tweet> tweets = m_tweetRepository.getTweets(new Date(), 50);
		
		model.addAttribute("username", "The Public");
		model.addAttribute("tweets", tweets);
		
		return "tweets";
		
	}
	
	@RequestMapping("/{username}/index.htm")
	public String userTweets(@PathVariable String username, Model model) {
		
		List<Tweet> tweets = m_tweetRepository.getTimeline(username, new Date(), 50);

		model.addAttribute("username", username);
		model.addAttribute("tweets", tweets);
		
		return "tweets";
		
	}

}