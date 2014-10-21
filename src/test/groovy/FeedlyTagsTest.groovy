import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

class FeedlyEmptyTagsTest {
	
	private feedly

	@Before
	void before() {
		Feedly.metaClass.getDataFromPath = {String path, String queryString -> []}
		feedly = new Feedly("devtoken", "userid")
	}

	@Test
	void shouldReturnNoTags() {
		assert feedly.tags.size() == 0
	}

}

class FeedlyFullTagsTest {
	
	private feedly

	@Before
	void before() {
		Feedly.metaClass.getDataFromPath = {String path, String queryString -> FakeData.TAGS_FULL }
		feedly = new Feedly("devtoken", "userid")
	}

	@Test
	void shouldReturnAllTags() {
		assert feedly.tags.size() == 2
		assert feedly.tags.find{it.label == "Forever-list"}
		assert feedly.tags.find{it.label == "Read-list"}
	}

}
