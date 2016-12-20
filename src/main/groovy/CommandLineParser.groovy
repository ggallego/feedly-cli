import static groovy.json.JsonOutput.*

class CommandLineParser {

	def Properties parse(String... args) {
		def opts = clibuilder.parse(args)

		if (!opts)
			System.exit(0)
		if (opts.h) {
			clibuilder.usage()
			System.exit(0)
		}

		def file_config = opts.f ?: 'feedly.properties'
		if (opts.f && !new File(file_config).exists()) {
			println "[ERROR] Configuration file ${file_config} does not exist!"
			System.exit(-1)
		}
		if (!opts.dev && !opts.uid && !new File(file_config).exists()) {
			println "[WARN] Setup your feedly account using command line switches or use a config file."
			clibuilder.usage()			
		}
			
		def jprops = new Properties();
		if (new File(file_config).exists()) {
			def file_configIS = new FileInputStream(file_config);
			jprops.load(file_configIS);
		}

		def props = new ConfigSlurper().parse(jprops)
		props.with {
			devtoken = opts.dev ?: props.devtoken
			userid = opts.uid ?: props.userid
			maxposts = opts.max ?: props.maxposts
			labels = opts.labels ?: props.labels
			tags = opts.tags ?: props.tags
			posts =  opts.posts ?: props.posts
			media = opts.media ?: props.media
			youtube = opts.youtube ?: props.youtube
			youtubeaudio = opts.youtubeaudio ?: props.youtubeaudio
            downloader = opts.downloader ?: props.downloader
			verbose = opts.v ?: props.verbose
		}

		// Handle developertoken opt
		if (props.devtoken.size() == 0 || props.devtoken.isEmpty()) {
			println "[ERROR] Option [devtoken] is required!"
			System.exit(-1)
		}
		
		// Handle userid opt
		if (props.userid.size() == 0 || props.userid.isEmpty()) {
			println "[ERROR] Option [userid] is required!"
			System.exit(-1)
		}

		// Handle maxposts opt
		try {
			props.maxposts = props.maxposts.toInteger()
		} catch (Exception e) {
			if (!props.maxposts.isEmpty() && !props.maxposts.isNumber()) {
				println "[WARN] Number of posts [${props.maxposts}] is not a valid number. Reverting to default [$Feedly.DEFAULT_MAX_POSTS]."
			}
			props.maxposts = Feedly.DEFAULT_MAX_POSTS
		}

		// Handle labels opt
		if (props.labels in ConfigObject) {
			props.labels = false
		}
		if (props.labels in String)
		props.labels = props.labels.toBoolean()

		// Handle tags opt
		if (props.tags in ConfigObject) {
			props.tags = false
		}
		if (props.tags in String)
		props.tags = props.tags.toBoolean()

		// Handle posts opt
		if (props.posts.size() == 0 || props.posts.isEmpty()) {
			props.posts = ''
		}

		// Handle media opt
		if (props.media in ConfigObject) {
			props.media = false
		}
		if (props.media in String)
		props.media = props.media.toBoolean()

		// Handle youtube opt
		if (props.youtube in ConfigObject) {
			props.youtube = false
		}
		if (props.youtube in String)
		props.youtube = props.youtube.toBoolean()

		// Handle youtube opt
		if (props.youtubeaudio in ConfigObject) {
			props.youtubeaudio = false
		}
		if (props.youtubeaudio in String)
		props.youtubeaudio = props.youtubeaudio.toBoolean()

		// Handle downloader opt
		if (props.downloader.size() == 0 || props.downloader.isEmpty()) {
			props.downloader = 'embedded'
		}

		// Handle verbose opt
		if (props.verbose in ConfigObject)
			props.verbose = false
		if (props.verbose in String) 
			props.verbose = props.verbose.toBoolean()

		if (props.verbose) println "[INFO] Command line / Properties: \n ${prettyPrint(toJson(props))}"

		if (!props.labels && !props.tags && props.posts.isEmpty()) {
			println "[WARN] Choose one of the following actions: -labels, -tags, -posts"
			System.exit(0)
		}

		return props;
	}

	// ########## private behaviour
	
	private CliBuilder getClibuilder() {
		def cli = new CliBuilder(
				usage: 'feedly-cli [options]',
				header: '\nAvailable options (use -h for help):\n'
				)
		cli.with {
			f(longOpt:  'fileconfig', 'Path to config file (default: feedly.properties)', args: 1)
			dev(longOpt: 'devtoken', 'Feedly developer token (see feednix usage).', args: 1, argName: 'TOKEN')
			uid(longOpt: 'userid', 'Feedly userid.', args: 1, argName: 'id')
			max(longOpt: 'maxposts', 'Fetch only <number> posts from feedly (default: '+Feedly.DEFAULT_MAX_POSTS+')', args: 1, argName: 'NNN')
			labels("List labels from feedly.")
			tags("List tags from feedly.")
			posts("List posts from feedly label.", args: 1, argName: 'LABEL')
			media("Download embedded media (mainly podcasts) from posts (IMPORTANT: calls markAsUnsaved on feedly).")
			youtube("Download youtube video from posts (IMPORTANT: calls markAsUnsaved on feedly).")
			youtubeaudio("Download youtube audio (extract it from videos) from posts (IMPORTANT: calls markAsUnsaved on feedly).")
            downloader("Define the media downloader to use (default: embedded). Available providers: 'wget', 'embedded'.", args: 1, argName: 'PROVIDER')
			v(longOpt: 'verbose', 'Output debug messages' )
			h(longOpt: 'help', "Prints this help message.")
		}
		return cli
	}
}
