import static groovyx.net.http.ContentType.*
import groovyx.net.http.RESTClient

class Feedly {
	
	private devtoken = ""
	private userid = ""
	private verbose = false
	
	private _labels = [:]
	private _tags = [:]

	Feedly(String devtoken, String userid) {
		this(devtoken, userid, false)
	}

	Feedly(String devtoken, String userid, boolean verbose) {
		this.devtoken = devtoken
		this.userid = userid
		this.verbose = verbose
	}

	def Map<String, String> getLabels() {
		if (_labels.isEmpty()) {
		    _labels["All"] = "user/" + userid + "/category/global.all";
		    _labels["Saved"] = "user/" + userid + "/tag/global.saved";
		    _labels["Uncategorized"] = "user/" + userid + "/category/global.uncategorized";
			getDataFromPath("categories", "").each {
				if (it.label != null)
					_labels[it.label] = it.id
			}
		}
		return _labels
	}

	def Map getTags() {
		if (_tags.isEmpty()) {
			getDataFromPath("tags", "").each {
				if (it.label != null)
					_tags[it.label] = it.id
			}
		}
		return _tags
	}

	def Map getPosts(String label, int maxPosts) {
		def streamId = labels[label]
		if (streamId == null)
			streamId = tags[label]
		def _posts = [:]
		getDataFromPath("streams/contents", "unreadOnly=true&ranked=newest&count=" + maxPosts +"&streamId=" + streamId).items.each {
			_posts[it.id] = [origin:	it.origin?.title,
							 title:     it.title, 
							 summary:   it.summary?.content,
							 published: it.published,
							 enclosure: it.enclosure,
							 content:   it.content?.content,
// Nao esta pegando o nerdologia!
							 embeddedVideo: getVideoUrls(it.content?.content)]
		}
		return _posts;
	}
	
	def boolean tagPost(postId) {
		
	}

	def boolean untagPost(postId) {
		
	}

	def boolean savePost(postId) {
		
	}

	def boolean unsavePost(postId) {
		
	}

	// ########## private behaviour
	
	private List getVideoUrls(streamContent) {
		def embeddedVideo = []
		def matcher = streamContent =~ /"(((https?:\/\/)?)(www\.)?(youtube\.com|youtu.be|youtube)\/.+)"/
		matcher.each {embeddedVideo << it[1]}
		return embeddedVideo.isEmpty()?null:embeddedVideo
	}

	private Object getDataFromPath(String path, String queryString) {
		def feedly = new RESTClient("https://cloud.feedly.com/v3/")
		feedly.headers.Authorization = "OAuth " + devtoken
		feedly.contentType = JSON
		def resp = feedly.get(path: path, queryString: queryString)
		if (verbose) Printer.printHTTPResponse(resp)
		return resp.data
	}
	
}
