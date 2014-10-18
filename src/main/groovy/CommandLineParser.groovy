import static groovy.json.JsonOutput.*
import org.apache.commons.cli.Option

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
		props.devtoken = opts.dev ?: props.devtoken
		props.userid = opts.uid ?: props.userid
		props.maxposts = opts.max ?: props.maxposts
		props.labels = opts.labels ?: props.labels
		props.tags = opts.tags ?: props.tags
		props.posts =  opts.posts ?: props.posts
		props.media = opts.media ?: props.media
		props.verbose = opts.v ?: props.verbose

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
				println "[WARN] Number of posts ${props.maxposts} is not a valid number. Reverting to default [50]."
			}
			props.maxposts = 50
		}

		// Handle labels opt
		if (!props.labels instanceof ConfigObject) {
			props.labels = false
		}

		// Handle tags opt
		if (!props.tags instanceof ConfigObject) {
			props.tags = false
		}

		// Handle posts opt
		if (props.posts.size() == 0 || props.posts.isEmpty()) {
			props.posts = ''
		}

		// Handle media opt
		if (!props.media instanceof ConfigObject) {
			props.media = false
		}

		// Handle verbose opt
		if (!props.verbose instanceof ConfigObject) {
			props.verbose = false
		}

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
			max(longOpt: 'maxposts', 'Fetch <number> posts from feedly (default: 50)', args: 1, argName: 'NNN')
			labels("Fetch labels from feedly.")
			tags("Fetch tags from feedly.")
			posts("Fetch posts from feedly label.", args: 1, argName: 'LABEL')
			media("Also download the embedded media (podcasts and youtube content) from posts")
			v(longOpt: 'verbose', 'Output debug messages' )
			h(longOpt: 'help', "Prints this help message.")
		}
		return cli
	}
}