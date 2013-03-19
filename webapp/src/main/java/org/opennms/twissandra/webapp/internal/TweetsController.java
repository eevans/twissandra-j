package org.opennms.twissandra.webapp.internal;

import java.security.Principal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.opennms.twissandra.api.Tweet;
import org.opennms.twissandra.api.TweetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class TweetsController {
	
	public static final Logger LOG = LoggerFactory.getLogger(TweetsController.class);
	
	public static final String DEFAULT_LIMIT = "25";
	
	@Autowired
	private TweetRepository m_tweetRepository;
	
	@Autowired
	private UserDetailsService m_userManager;
	
	private Date check(Date start) {
		return start == null ? new Date() : start;
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String allTweets(Model model, Principal principal, @RequestParam(value="start", required=false)Date start, @RequestParam(value="limit",defaultValue=DEFAULT_LIMIT)int limit) {
		start = check(start);
		if (principal == null) {
			List<Tweet> tweets = m_tweetRepository.getTweets(start, limit);
			// TODO: Add code that sets 'next' to the timestamp of the last one
			model.addAttribute("tweets", tweets);
			return "publicLine";
		} else {
			List<Tweet> tweets = m_tweetRepository.getTimeline(principal.getName(), start, limit);
			model.addAttribute("username", principal.getName());
			model.addAttribute("tweets", tweets);
			// TODO: Add code that sets 'next' to the timestamp of the last one
			return "timeLine";
		}
		
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public String postTweet(Principal principal, @RequestParam("body")String body) {
		if (principal != null) {
			LOG.info("saving tweet by {}: {}", principal.getName(), body);
			m_tweetRepository.saveTweet(principal.getName(), body);
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="/public", method=RequestMethod.GET)
	public String publicTweets(Model model, @RequestParam(value="start", required=false)Date start, @RequestParam(value="limit",defaultValue=DEFAULT_LIMIT)int limit) {
		start = check(start);
		List<Tweet> tweets = m_tweetRepository.getTweets(start, limit);
		// TODO: Add code that sets 'next' to the timestamp of the last one
		model.addAttribute("tweets", tweets);
		return "publicLine";		
	}
	
	@RequestMapping(value="/{username}", method=RequestMethod.GET)
	public String userTweets(@PathVariable String username, Principal principal, Model model, @RequestParam(value="start", required=false)Date start, @RequestParam(value="limit",defaultValue=DEFAULT_LIMIT)int limit) {
		start = check(start);
		
		List<Tweet> tweets = m_tweetRepository.getUserline(username, start, limit);
		model.addAttribute("principal", principal);
		model.addAttribute("username", username);
		model.addAttribute("tweets", tweets);
		if (principal != null) {
			List<String> friends = m_tweetRepository.getFriends(principal.getName());
			model.addAttribute("isFriend", friends.contains(username));
		}
		
		return "userLine";
		
	}

	@RequestMapping(value= {"/login"}, method=RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping("/loginfailed")
	public String loginFailed() {
		return "login";
	}
	
	private String registrationError(String msg, Model model) {
		model.addAttribute("registration_error", msg);
		return "login";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String register(@RequestParam("j_username")String username, @RequestParam("j_password")String password1, @RequestParam("j_password2")String password2, Model model) {
		if (username == null || username.isEmpty()) {
			return registrationError("username cannot be emtpy", model);
		}
		boolean existing = m_tweetRepository.getPassword(username) != null;
		if (existing) {
			return registrationError("user " + username + " already exists!", model);
		}
		if (password1 == null) {
			return registrationError("Password cannot be null", model);
		}
		if (!password1.equals(password2)) {
			return registrationError("Password1 and Password2 must match", model);
		}
		
		m_tweetRepository.saveUser(username, password1);
		
		UserDetails userDetails = m_userManager.loadUserByUsername(username);
		Authentication auth = new UsernamePasswordAuthenticationToken (userDetails.getUsername (),userDetails.getPassword (),userDetails.getAuthorities ());
		SecurityContextHolder.getContext().setAuthentication(auth);

		return "redirect:/";
	}
	
	@RequestMapping(value="/find-friends", method=RequestMethod.GET)
	public String findFriends(Principal principal, Model model, @RequestParam(value="q", required=false)String queryString) {
		boolean searched = false;
		List<String> friends = Collections.emptyList();
		if (principal != null) {
			friends = m_tweetRepository.getFriends(principal.getName());
		}
		if (queryString != null) {
			searched=true;
			boolean isFound = m_tweetRepository.getPassword(queryString) != null;
			boolean isFriend = false;
			if (isFound && principal != null) {
				isFriend = friends.contains(queryString);
			}
			model.addAttribute("isFound", isFound);
			model.addAttribute("isFriend", isFriend);
		}
		model.addAttribute("friends", friends);
		model.addAttribute("principal", principal);
		model.addAttribute("q", queryString);
		model.addAttribute("searched", searched);
		
		return "addFriends";
	}

	@RequestMapping(value="/modify-friend", method=RequestMethod.POST)
	public String modifyFriends(Principal principal, Model model, @RequestParam("action")String action, @RequestParam("friend")String friend, @RequestParam(value="next", required=false)String next) {

		boolean added = false;
		boolean removed = false;
		if ("add".equals(action)) {
			m_tweetRepository.addFriend(principal.getName(), friend);
			added = true;
		} else if ("remove".equals(action)) {
			m_tweetRepository.removeFriend(principal.getName(), friend);
			removed = true;
		}
		
		model.addAttribute("added", added);
		model.addAttribute("removed", removed);
		
		if (next != null) {
			return "redirect:/find-friends";
		} else {
			return "modifyFriend";
		}
		
	}
	

}
