package org.opennms.twissandra.webapp.internal;

import java.security.Principal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class TweetsController {
	
	TweetRepository m_tweetRepository;
	
	public void setTweetRepository(TweetRepository tweetRepository) {
		m_tweetRepository = tweetRepository;
	}
	
	@RequestMapping("/index.htm")
	public String allTweets(Model model, Principal principal, @RequestParam(value="start", required=false)Date start, @RequestParam(value="limit",defaultValue="50")int limit) {
		if (start == null) {
			start = new Date();
		}
		if (principal == null) {
			List<Tweet> tweets = m_tweetRepository.getTweets(start, limit);
			// TODO: Add code that sets 'next' to the timestamp of the last one
			model.addAttribute("tweets", tweets);
			return "publicLine";
		} else {
			List<Tweet> tweets = m_tweetRepository.getTimeline(principal.getName(), start, 50);
			model.addAttribute("tweets", tweets);
			// TODO: Add code that sets 'next' to the timestamp of the last one
			return "timeLine";
		}
		
	}
	
	@RequestMapping("/login.htm")
	public String showRegistrationUser() {
		return "loginForm";
	}
	
	@RequestMapping("/{username}/index.htm")
	public String userTweets(@PathVariable String username, Model model) {
		
		List<Tweet> tweets = m_tweetRepository.getTimeline(username, new Date(), 50);

		model.addAttribute("username", username);
		model.addAttribute("tweets", tweets);
		
		return "tweets";
		
	}

}