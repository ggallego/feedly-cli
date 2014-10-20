import static groovyx.net.http.ContentType.*
import groovyx.net.http.RESTClient


class Feedly {
	
	def static DEFAULT_MAX_POSTS = 50
	
	private devtoken = ""
	private userid = ""
	private verbose = false
	
	private _labels = []
	private _tags = []

	Feedly(String devtoken, String userid) {
		this(devtoken, userid, false)
	}

	Feedly(String devtoken, String userid, boolean verbose) {
		this.devtoken = devtoken
		this.userid = userid
		this.verbose = verbose
	}

	def List getLabels() {
		if (_labels.isEmpty()) {
		    _labels << [id: "user/$userid/category/global.all", 			label: "All"];
		    _labels << [id: "user/$userid/tag/global.saved",				label: "Saved"];
		    _labels << [id: "user/$userid/category/global.uncategorized",	label: "Uncategorized"];
			if (verbose) println "[INFO] Retrieving Labels..."
			getDataFromPath("categories", "").each {
				if (it.label != null)
					_labels << [id: it.id, label: it.label]
			}
		}
		return _labels
	}

	def List getTags() {
		if (_tags.isEmpty()) {
			if (verbose) println "[INFO] Retrieving Tags..."
			getDataFromPath("tags", "").each {
				if (it.label != null)
					_tags << [id: it.id, label: it.label]
			}
		}
		return _tags
	}

	def List getPosts(String label) {
		getPosts(label, DEFAULT_MAX_POSTS)
	}

	def List getPosts(String label, Integer maxPosts) {
		def streamId = labels.find{it.label == label}?.id
		if (streamId == null)
			streamId = tags.find{it.label == label}?.id
		def _posts = []
		if (verbose) println "[INFO] Retrieving [$maxPosts] Posts[${label}]..."
		def data = getDataFromPath("streams/contents", "unreadOnly=true&ranked=newest&count=" + maxPosts +"&streamId=" + streamId)?.items.each {
			_posts << [	id: it.id,
						origin:	it.origin?.title,
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
