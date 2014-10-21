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
	void givenAnUndefinedLabelAndUndefinedTagShouldReturnNoPosts() {
		assert feedly.getPosts('UndefinedLabelAndUndefinedTag').size() == 0
	}

	@Test
	void givenALabelWithEmptyPostsShouldReturnNoPosts() {
		assert feedly.getPosts('Podcast-Various').size() == 0
	}

	@Test
	void givenATagWithEmptyPostsShouldReturnNoPosts() {
		assert feedly.getPosts('Forever-list').size() == 0
	}

	@Test
	void givenALabelWithPostsAndZeroMaxPostsShouldReturnNoPosts() {
		assert feedly.getPosts('Saved', 0).size() == 0
	}

	@Test
	void givenATagWithPostsAndZeroMaxPostsShouldReturnNoPosts() {
		assert feedly.getPosts('Read-list', 0).size() == 0
	}

	@Test
	void givenALabelWithPostsAndASmallMaxPostsShouldReturnPostsEqualsToMaxPosts() {
		assert feedly.getPosts('Saved', 2).size() == 2
	}

	@Test
	void givenALabelWithPostsAndALargeMaxPostsShouldReturnLessPostsThanTheMaxPosts() {
		def size = feedly.getPosts('Saved', 50).size()
		assert size > 0 && size < 50
	}

	@Test
	void givenATagWithPostsAndASmallMaxPostsShouldReturnPostsEqualsToMaxPosts() {
		assert feedly.getPosts('Read-list', 2).size() == 2
	}

	@Test
	void givenATagWithPostsAndALargeMaxPostsShouldReturnLessPostsThanTheMaxPosts() {
		def size = feedly.getPosts('Read-list', 50).size()
		assert size > 0 && size < 50
	}

}
