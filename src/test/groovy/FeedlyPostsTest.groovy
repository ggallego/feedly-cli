import static org.junit.Assert.*

import java.lang.invoke.SwitchPoint;

import org.junit.Before
import org.junit.Test


class FeedlyEmptyPostsTest {

	private feedly

	@Before
	void before() {
		Feedly.metaClass.getDataFromPath = {String path, String queryString -> null}
		feedly = new Feedly("devtoken", "userid")
	}

	@Test
	void shouldReturnNoPosts() {
		assert feedly.getPosts("Undefined").size() == 0
	}
}

class FeedlyFullPostsTest {

	private feedly

	@Before
	void before() {
		Feedly.metaClass.getDataFromPath = {String path, String queryString -> FakeData.getPOSTS(path, queryString)}
		feedly = new Feedly("devtoken", "userid")
	}

	@Test
	void shouldReturnNoPostsGivenAnUndefinedLabelAndUndefinedTag() {
		assert feedly.getPosts('UndefinedLabelAndUndefinedTag').size() == 0
	}

	@Test
	void shouldReturnNoPostsGivenALabelWithEmptyItems() {
		assert feedly.getPosts('Podcast-Various').size() == 0
	}

	@Test
	void shouldReturnNoPostsGivenATagWithEmptyItems() {
		assert feedly.getPosts('Forever list').size() == 0
	}

	@Test
	void shouldReturnNoPostsGivenALabelWithItemsAndZeroMaxPosts() {
		assert feedly.getPosts('Saved', 0).size() == 0
	}

	@Test
	void shouldReturnNoPostsGivenATagWithItemsAndZeroMaxPosts() {
		assert feedly.getPosts('Read list', 0).size() == 0
	}

	@Test
	void shouldReturnAnNumberOfPostsGivenALabelAndARestrictedNumberOfMaxPosts() {
		assert feedly.getPosts('Saved', 1).size() == 1
	}

	@Test
	void shouldReturnARescrictedNumberOfPostsGivenALabelAndALargeNumberOfMaxPosts() {
		assert feedly.getPosts('Saved', 50).size() < 50
	}
}
