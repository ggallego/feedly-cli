import static groovy.json.JsonOutput.*

class Main {

	static main(args) {
		def props = new CommandLineParser().parse(args)
		def feedly = new Feedly(props.devtoken, props.userid)
		
		if (props.labels)
			println "Labels => " + prettyPrint(toJson(feedly.getLabels()))
			
		if (props.tags)
			println "Tags => " + prettyPrint(toJson(feedly.getTags()))

		if (!props.posts.isEmpty())
			println "Posts [$props.posts] => " + prettyPrint(toJson(feedly.getPosts(props.posts, props.maxposts)))

	}

}
