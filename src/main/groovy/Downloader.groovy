import static groovyx.net.http.Method.*
import groovyx.net.http.HTTPBuilder

class Downloader {

	static boolean download(String url) {
		download(url, null)
	}

	static boolean download(String url, long length) {
		download(url, null, null)
	}

	static boolean download(String url, long length, String type) {
		def filename = url.tokenize("/")[-1]
		filename = filename.replaceAll(/\&.*/, "")
		filename = filename.replaceAll(/\?.*/, "")
		
		def filesize = length
		def resource = new HTTPBuilder(url).request(HEAD) {
			response.sucess = {resp -> filesize = resp.headers.'Content-Length'}
			response.failure = {resp -> return false}
		}
		println "Downloading file [$filename] with [$filesize] bytes."

		def file = new FileOutputStream(filename)
		def out = new BufferedOutputStream(file)
		out << new URL(url).openStream()
		out.close()
		
		def checkfile = new File(filename)
		if (checkfile.size() == filesize) {
			print "[OK]"
			return true
		}
		print "[ERROR]"
		return false
	}
	
}













