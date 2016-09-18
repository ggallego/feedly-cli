import static groovyx.net.http.ContentType.*

import org.apache.commons.lang.SystemUtils;

import groovyx.net.http.HttpResponseException;
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
		// TODO: fazer um escape mais completo disso. Alias, precisa?
		streamId = Downloader.escapeIllegalURLCharacters(streamId)
		def _posts = []
		if (verbose) println "[INFO] Retrieving [$maxPosts] posts from label [${label}]..."
		def data = getDataFromPath("streams/contents", "unreadOnly=true&ranked=newest&count=" + maxPosts +"&streamId=" + streamId)?.items.each {
			_posts << [	id: it.id,
						origin:	it.origin?.title,
						title:     it.title, 
						summary:   it.summary?.content,
						published: it.published,
						enclosure: getUniqEnclosure(it.enclosure),
						content:   it.content?.content,
						embeddedVideo: getVideoUrls(it.summary?.content, it.content?.content),
						tags: it.tags,
						labels: it.labels]
		}
		return _posts;
	}
	
	def boolean tagPost(postId) {
		println "[WARN] tagPost was not implemented yet."
	}

	def boolean untagPost(postId) {
		println "[WARN] untagPost was not implemented yet."
	}

	def boolean savePost(postId) {
		println "[WARN] savePost was not implemented yet."
	}

	def boolean unsavePost(postId) {
		def feedly = new RESTClient("https://cloud.feedly.com/v3/")
		feedly.headers.Authorization = "OAuth " + devtoken
		feedly.contentType = JSON
		def resp = feedly.post(	path: 'markers', 
								body: [	"action": "markAsUnsaved",
										"entryIds": [postId],
										"type": "entries"])
		if (resp.status != 200) {
			println "[WARN] could not unsave post $postId, please check your feedly site."
			return false
		}
		return true
	}

	// ########## private behaviour
	
	private List getUniqEnclosure(enclosures) {
        if (enclosures?.size() < 2)
            return enclosures
        def uniques = []
        enclosures.each { e ->
            def uniq = uniques.find { 
                it.href == e.href 
            }
            if (uniq == null)
                uniques << e
            else {
                if (uniq.href == e.ref)
                    if (uniq.length < e.length)
                        uniq.length = e.length
                    if (uniq.type?.trim())
                        uniq.type = e.type
            }
        }
        return uniques
	}

	private List getVideoUrls(String summary, String content) {
		return getVideoUrls(summary) + getVideoUrls(content)
	}

	private List getVideoUrls(String text) {
		def embeddedVideo = []
		def matcher = text =~ Downloader.PATTERN_YOUTUBE
		matcher.each {embeddedVideo << it[1]}
		return embeddedVideo.unique()
	}

	private Object getDataFromPath(String path, String queryString) {
		def feedly = new RESTClient("https://cloud.feedly.com/v3/")
		feedly.headers.Authorization = "OAuth " + devtoken
		feedly.contentType = JSON
		try {
			def resp = feedly.get(path: path, queryString: queryString)
			if (verbose) Printer.printHTTPResponse(resp)
			return resp.data
		} catch (HttpResponseException e) {
			if (e.message.contains("Unauthorized")) registerDevtokenForFeedly()
		}
	}
	
	private registerDevtokenForFeedly() {
		println "[ERROR] You must get an 'developer token' to access feedly from command line."
		println "------- Now you will be redirected to Feedly developer login page at 'https://feedly.com/v3/auth/dev/'."		
		println "------- Please sign in, copy your 'user id' and retrive the 'developer token' from your email."
		println "------- Inform the --devtoken and --userid arguments in CLI or copy it to a feedly.properties file."
		println "------- And restart the feedly-cli. Opening your browser in 3 secs..."
		Thread.sleep(3000);
		if (SystemUtils.IS_OS_LINUX) "xdg-open https://feedly.com/v3/auth/dev/".execute()
		if (SystemUtils.IS_OS_MAC) "open https://feedly.com/v3/auth/dev/".execute()
		if (SystemUtils.IS_OS_WINDOWS) "open https://feedly.com/v3/auth/dev/".execute()
		System.exit(-1)
	}
	
}
