class Main {

	static main(args) {
		
		def props = new CommandLineParser().parse(args)
		
		def feedly = new Feedly(props.devtoken, props.userid, props.verbose)
		
		if (props.labels)
			Printer.printAsJson("Labels", feedly.getLabels())
			
		if (props.tags)
			Printer.printAsJson("Tags", feedly.getTags())

		if (!props.posts.isEmpty()) {
			def posts = feedly.getPosts(props.posts, props.maxposts)
			Printer.printAsJson("Posts [$props.posts]", posts)
			if (props.media) {
				posts.each { post -> post.enclosure?.each { 
						if (Downloader.download(it.href, it.length, it.type)) {
							feedly.unsavePost(post.id)
						}
				}}
			}
		}
			
		if (props.posts.isEmpty() && props.media)
			println "[WARN] You can only download media from posts. Please specify -posts first."

	}

}
