import static groovy.json.JsonOutput.*

class Main {

	static main(args) {
		def props = new CommandLineParser().parse(args)
		def feedly = new Feedly(props.devtoken, props.userid, props.verbose)
		
		if (props.labels)
			println "Labels => " + prettyPrint(toJson(feedly.getLabels()))
			
		if (props.tags)
			println "Tags => " + prettyPrint(toJson(feedly.getTags()))

		if (!props.posts.isEmpty()) {
			def posts = feedly.getPosts(props.posts, props.maxposts)
			println "Posts [$props.posts] => " + prettyPrint(toJson(posts))
			if (props.media) {
				// Nao esta pegando o nerdologia!
				posts.each { post -> post.value.enclosure?.each { 
						println "Downloading file [${it.href.tokenize('/')[-1]}] with [${it.length}]."
						if (Downloader.download(it.href, it.length, it.type)) {
							feedly.markPostUnsaved(post.key)
						}
				}}
			}
		}
			
		if (props.posts.isEmpty() && props.media)
			println "[WARN] You can only download media from posts. Please specify -posts first."

	}

}
