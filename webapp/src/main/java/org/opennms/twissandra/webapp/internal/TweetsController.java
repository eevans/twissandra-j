package org.opennms.twissandra.webapp.internal;

import java.security.Principal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.opennms.twissandra.api.Tweet;
import org.opennms.twissandra.api.TweetRepository;
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
	
	@Autowired
	private TweetRepository m_tweetRepository;
	
	@Autowired
	private UserDetailsService m_userManager;
	
	@ModelAttribute("username")
	public String getUsername(Principal principal) {
		return principal == null ? "Public" : principal.getName();
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
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
	
	@RequestMapping(value="/public", method=RequestMethod.GET)
	public String publicTweets(Model model) {
//		List<Tweet> tweets = m_tweetRepository.getTweets(start, limit);
//		// TODO: Add code that sets 'next' to the timestamp of the last one
//		model.addAttribute("tweets", tweets);
		return "publicLine";		
	}
	
	@RequestMapping(value="/{username}", method=RequestMethod.GET)
	public String userTweets(@PathVariable String username, Model model) {
		
		List<Tweet> tweets = m_tweetRepository.getTimeline(username, new Date(), 50);

		model.addAttribute("username", username);
		model.addAttribute("tweets", tweets);
		
		return "tweets";
		
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
	public String findFriends() {
		return "find_friends";
	}

	@RequestMapping(value="/modify-friends", method=RequestMethod.GET)
	public String modifyFriends() {
		return "modify_friends";
	}
	

}
