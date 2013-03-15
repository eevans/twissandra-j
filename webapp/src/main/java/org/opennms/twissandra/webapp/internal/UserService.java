package org.opennms.twissandra.webapp.internal;

import java.util.Collection;
import java.util.Collections;

import org.opennms.twissandra.api.TweetRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserService implements UserDetailsService {
	
	private static class User implements UserDetails {
		
		private static final GrantedAuthority ROLE_USER = new SimpleGrantedAuthority("ROLE_USER");
		private final String m_username;
		private final String m_password;
		
		public User(String username, String password) {
			m_username = username;
			m_password = password;
		}

		public Collection<? extends GrantedAuthority> getAuthorities() {
			return Collections.singleton(ROLE_USER);
		}

		public String getPassword() {
			return m_password;
		}

		public String getUsername() {
			return m_username;
		}

		public boolean isAccountNonExpired() {
			return true;
		}

		public boolean isAccountNonLocked() {
			return true;
		}

		public boolean isCredentialsNonExpired() {
			return true;
		}

		public boolean isEnabled() {
			return true;
		}
		
	}
	
	TweetRepository m_tweetRepository;
	
	public void setTweetRepository(TweetRepository tweetRepository) {
		m_tweetRepository = tweetRepository;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String password = m_tweetRepository.getPassword(username);
		if (password == null) {
			throw new UsernameNotFoundException("No user named " + username);
		}
		
		return new User(username, password);
	}

}
