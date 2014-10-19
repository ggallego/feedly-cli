import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


class FeedlyEmptyTagTest {
	
	private feedly

	@Before
	void before() {
		Feedly.metaClass.getDataFromPath = {String path, String queryString -> null}
		feedly = new Feedly("devtoken", "userid")
	}

	@Test
	void shouldReturnEmptyTagList() {
		assert feedly.tags.size() == 0
		assert !feedly.tags["Undefined"]
	}

}

class FeedlyFullTagTest {
	
	private feedly

	@Before
	void before() {
		Feedly.metaClass.getDataFromPath = {String path, String queryString -> 
			[
				[label:"Forever list", id:"user/${->userid}/tag/Forever list"],
				[label:"Read list", id:"user/${->userid}/tag/Read list"]
			]
		}
		feedly = new Feedly("devtoken", "userid")
	}

	@Test
	void shouldReturnInjectedTags() {
		assert feedly.tags.size() == 2
		assert feedly.tags["Forever list"]
		assert feedly.tags["Read list"]
	}

}
