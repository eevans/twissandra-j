package org.opennms.twissandra.persistence.memory.internal;

import org.opennms.twissandra.api.TweetRepository;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class MyActivator implements BundleActivator {

	private ServiceRegistration<TweetRepository> m_registration;

	public void start(BundleContext context) throws Exception {
		System.err.println("Starting memory repo");
		MemoryTweetRepository repo = new MemoryTweetRepository();
		m_registration = context.registerService(TweetRepository.class, repo, null);
	}

	public void stop(BundleContext context) throws Exception {
		m_registration.unregister();
		System.err.println("Stopped memory repo");
	}

}
