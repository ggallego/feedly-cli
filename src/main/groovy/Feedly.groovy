import groovyx.net.http.RESTClient
import groovyx.net.http.HTTPBuilder
import groovy.json.JsonOutput
import static groovyx.net.http.ContentType.*


class Feedly {
	
	def devtoken = ""
	def userid = ""
	def _labels = [:]
	def _tags = [:]

	Feedly(devtoken, userid) {
		this.devtoken = devtoken
		this.userid = userid
	}

	def Map getLabels() {
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

	def Map getPosts(label, maxPosts) {
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
							 embeddedVideo: getVideoUrls(it.content?.content)]
		}
		return _posts;
	}

	// ########## private behaviour
	
	private List getVideoUrls(streamContent) {
		return null
	}

	private Object getDataFromPath(path, queryString) {
		def feedly = new RESTClient("https://cloud.feedly.com/v3/")
		feedly.headers.Authorization = "OAuth " + devtoken
		feedly.contentType = JSON
		def resp = feedly.get(path: path, queryString: queryString)
		//printResponse(resp)
		return resp.data
	}
	
	private printResponse(resp) {
		println("[INFO] Print HTTP Response =>")
		println("[INFO] HTTP Status: " + resp.status)
		println("[INFO] HTTP ContentType: "+ resp.contentType)
		resp.headers.each {println "[INFO] HTTP Header: " + it }
		println("[INFO] HTTP Body: "+resp.data.toString())
		println("[INFO] Print Response <=")
	}
		

}
