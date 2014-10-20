import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

class FeedlyEmptyLabelsTest {
	
	private feedly

	@Before
	void before() {
		Feedly.metaClass.getDataFromPath = {String path, String queryString -> []}
		feedly = new Feedly("devtoken", "userid")
	}

	@Test
	void shouldReturnOnlyPredefinedItems() {
		assert feedly.labels.size() == 3
		assert feedly.labels.find{it.label == "All"}
		assert feedly.labels.find{it.label == "Saved"}
		assert feedly.labels.find{it.label == "Uncategorized"}
	}

}

class FeedlyFullLabelsTest {
	
	private feedly

	@Before
	void before() {
		Feedly.metaClass.getDataFromPath = {String path, String queryString -> FakeData.LABELS_FULL}
		feedly = new Feedly("devtoken", "userid")
	}

	@Test
	void shouldReturnAllLabels() {
		assert feedly.labels.size() == 12
		assert feedly.labels.find{it.label == "All"}
		assert feedly.labels.find{it.label == "Saved"}
		assert feedly.labels.find{it.label == "Uncategorized"}
		assert feedly.labels.find{it.label == "Podcast-Various"}
		assert feedly.labels.find{it.label == "Podcast-Tech"}
		assert feedly.labels.find{it.label == "Computing"}
	}

}
