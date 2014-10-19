import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


class FeedlyEmptyLabelTest {
	
	private feedly

	@Before
	void before() {
		Feedly.metaClass.getDataFromPath = {String path, String queryString -> null}
		feedly = new Feedly("devtoken", "userid")
	}

	@Test
	void shouldReturnOnlyPredefinedLabels() {
		assert feedly.labels.size() == 3
		assert feedly.labels["All"]
		assert feedly.labels["Saved"]
		assert feedly.labels["Uncategorized"]
		assert !feedly.labels["Undefined"]
	}

}

class FeedlyFullLabelTest {
	
	private feedly

	@Before
	void before() {
		Feedly.metaClass.getDataFromPath = {String path, String queryString -> 
			[
				[label:"Podcast-Various", id:"user/${->userid}/category/Podcast-Various"],
				[label:"Podcast-Tech", id:"user/${->userid}/category/Podcast-Tech"],
				[label:"Computing", id:"user/${->userid}/category/Computing"],
				[label:"Programming", id:"user/${->userid}/category/Programming"],
				[label:"Podcast-Selection", id:"user/${->userid}/category/Podcast-Selection"],
				[label:"Podcast-Limbo", id:"user/${->userid}/category/Podcast-Limbo"],
				[label:"Limbo", id:"user/${->userid}/category/Limbo"],
				[label:"Blogs", id:"user/${->userid}/category/Blogs"],
				[label:"Webcomics", id:"user/${->userid}/category/Webcomics"]
			
			]
		}
		feedly = new Feedly("devtoken", "userid")
	}

	@Test
	void shouldReturnPredefinedAndInjectedLabels() {
		assert feedly.labels.size() == 12
		assert feedly.labels["All"]
		assert feedly.labels["Saved"]
		assert feedly.labels["Uncategorized"]
		assert feedly.labels["Podcast-Various"]
		assert feedly.labels["Podcast-Tech"]
		assert feedly.labels["Computing"]
	}

}
