package org.opennms.twissandra.webapp.internal;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.opennms.twissandra.api.TweetRepository;

public class JNDIHelper {
	public static final TweetRepository getTweetRepository() throws IOException {
		try {
			InitialContext ic = new InitialContext();

			return (TweetRepository) ic.lookup("osgi:service/"
					+ TweetRepository.class.getName());
		} catch (NamingException e) {
			e.printStackTrace();
			IOException ioe = new IOException("TweetRepository resolution failed");
			ioe.initCause(e);
			throw ioe;
		}
	}
}
