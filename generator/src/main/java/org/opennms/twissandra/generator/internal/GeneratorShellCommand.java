package org.opennms.twissandra.generator.internal;

import java.util.Random;

import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.opennms.twissandra.api.TweetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(scope = "twissandra", name = "generate", description="Generate Twissandra Test Data.")
public class GeneratorShellCommand extends OsgiCommandSupport {
	private static final Logger LOG = LoggerFactory.getLogger(GeneratorShellCommand.class);
	private static Random random = new Random();

	private TweetRepository m_tweetRepository;
	
	public void setTweetRepository(TweetRepository tweetRepository) {
		m_tweetRepository = tweetRepository;
	}

	@Override
	protected Object doExecute() throws Exception {
		outInfo("Loading test data...");

		createUsers();
		resetFriends();
		createTweets();

		outInfo("Done.");

		return null;
	}

	private void createUsers() {
		outInfo("Creating users...");

		for (String user : USERS) {
			m_tweetRepository.saveUser(user, "qwerty");
		}
	}

	private void resetFriends() {
		outInfo("Resetting social graph...");

		for (String user : USERS) {
			for (String friend : m_tweetRepository.getFriends(user)) {
				m_tweetRepository.removeFriend(user, friend);
			}

			int numFriends = random.nextInt(USERS.length) + 1;
			
			// Number of friends might be less than numFriends; Good Enough
			for (int i = 0; i < numFriends; i++) {
				m_tweetRepository.addFriend(user, choose(USERS));
			}
		}
	}

	private void createTweets() {
		outInfo("Generating random tweets...");

		for (int i = 0; i < 10000; i++) {
			m_tweetRepository.saveTweet(
					choose(USERS),
					String.format("%s %s %s", choose(VERBS), choose(ADJECTIVES), choose(NOUNS)));
		}
	}

	private static void outInfo(String msg) {
		outInfo(msg, new Object[]{});
	}
	
	private static void outInfo(String msg, Object[] args) {
		LOG.info(msg, args);
		System.out.println(String.format(msg, args));
	}

	private static String choose(String[] items) {
		return items[random.nextInt(items.length)];
	}

	private static String[] USERS = {
			"stewie", "brian", "meg", "lois", "peter", "chris"
	};
	private static String[] VERBS = {
			"implement", "utilize", "integrate", "streamline", "optimize", "evolve", "transform", "embrace", "enable",
			"orchestrate", "leverage", "reinvent", "aggregate", "architect", "enhance", "incentivize", "morph",
			"empower", "envisioneer", "monetize", "harness", "facilitate", "seize", "disintermediate", "synergize",
			"strategize", "deploy", "brand", "grow", "target", "syndicate", "synthesize", "deliver", "mesh",
			"incubate", "engage", "maximize", "benchmark", "expedite", "reintermediate", "whiteboard", "visualize",
			"repurpose", "innovate", "scale", "unleash", "drive", "extend", "engineer", "revolutionize", "generate",
			"exploit", "transition", "e-enable", "iterate", "cultivate", "recontextualize"
	};
	private static String[] ADJECTIVES = {
			"clicks-and-mortar", "value-added", "vertical", "proactive", "robust", "revolutionary", "scalable",
			"leading-edge", "innovative", "intuitive", "strategic", "e-business", "mission-critical", "sticky",
			"one-to-one", "24/7", "end-to-end", "global", "B2B", "B2C", "granular", "frictionless", "virtual", "viral",
			"dynamic", "24/365", "best-of-breed", "killer", "magnetic", "bleeding-edge", "web-enabled", "interactive",
			"dot-com", "sexy", "back-end", "real-time", "efficient", "front-end", "distributed", "seamless",
			"extensible", "turn-key", "world-class", "open-source", "cross-platform", "cross-media", "synergistic",
			"bricks-and-clicks", "out-of-the-box", "enterprise", "integrated", "impactful", "wireless", "transparent",
			"next-generation", "cutting-edge", "user-centric", "visionary", "customized", "ubiquitous",
			"plug-and-play", "collaborative", "compelling", "holistic"
	};
	private static String[] NOUNS = {
			"synergies", "web-readiness", "paradigms", "markets", "partnerships", "infrastructures", "platforms",
			"initiatives", "channels", "eyeballs", "communities", "ROI", "solutions", "e-tailers", "e-services",
			"action-items", "portals", "niches", "technologies", "content", "vortals", "supply-chains", "convergence",
			"relationships", "architectures", "interfaces", "e-markets", "e-commerce", "systems", "bandwidth",
			"infomediaries", "models", "mindshare", "deliverables", "users", "schemas", "networks", "applications",
			"metrics", "e-business", "functionalities", "experiences", "methodologies"
	};
}
