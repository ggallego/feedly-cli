import static groovyx.net.http.Method.*

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.math.RandomUtils;

import groovyx.net.http.HTTPBuilder

class Downloader {

    static String PATTERN_YOUTUBE = /"(((https?:\/\/)?)(www\.)?(youtube\.com|youtu.be|youtube)\/(watch|embed).+?)"/

    static boolean downloadMediaWithWGet(String url, Integer length) {
		if (!isWGetAvailable()) {
			println("[WARN] wget app is not available, reverting to embedded.");
			return false
		}

		url = escapeIllegalURLCharacters(url)
		String filename = url.tokenize("/")[-1]
		filename = filename.replaceAll(/\&.*/, "")
		filename = filename.replaceAll(/\?.*/, "")

		def command = "wget"
		command += " -O " + filename
		command += " " + url
		def process = command.execute()
		print("\rDownloading media from url [$url] ...");

		process.waitFor()
		if (process.exitValue() != 0) {
			println "\n[WARN] Something went wrong when downloading media from url [$url]: ${process.text}"
			return false
		}
		println("\rDownloaded media from url [$url].                ");
		return true
    }

	static boolean downloadMediaWithEmbedded(String url, Integer length) {
		url = escapeIllegalURLCharacters(url)
		
		String filename = url.tokenize("/")[-1]
		filename = filename.replaceAll(/\&.*/, "")
		filename = filename.replaceAll(/\?.*/, "")

		File file = new File(filename)
		while (file.exists()) {
			print("[WARN] File [$filename] exists, generating new filename.");
			filename += ".${RandomUtils.nextInt()}"
			file = new File(filename)
		}
		
		URLConnection connection = new URL(url).openConnection();
		DataInputStream instream = null
		try {
			instream = new DataInputStream(connection.getInputStream());
		} catch (FileNotFoundException e) {
			println "[WARN] Something went wrong... file [$filename] not found in url [$url]."
			return false
		}
		
		long bytes = 0;
		long filesize = connection.getContentLength()?:length;
        def fmt = new DecimalFormat("###,###,###")
        def fmt_filename = filename.size() <= 66 ? filename : filename.take(60)+"...mp3"
		
		OutputStream outstream = new FileOutputStream(filename);
		try {
			while (true) {
				outstream.write(instream.readUnsignedByte());
				bytes++;
//				if (bytes % 1024 == 0)
//				if (bytes % 1000 == 0)
				if (bytes % 10000 == 0)
//				if (bytes % 100000 == 0)
					print("\rDownloading [$fmt_filename] with [${fmt.format(filesize)} bytes]. Stats: percentage [${Math.round(bytes*100/filesize)}%], processed [${fmt.format(bytes)} bytes].");
			}
		} catch (EOFException e) {
			outstream.close();
			println("\rDownloaded file [$filename] with [${fmt.format(bytes)} bytes].                                                       ");
		}

		if (file.size() != filesize) {
			println "[WARN] Something went wrong on file [$filename]... received [${fmt.format(file.size())} bytes] which does not match the calculated size of [${fmt.format(filesize)} bytes]."
			return false
		}
		return true
	}

	static boolean downloadYoutube(String url) {
		downloadYoutube(url, false)
	}

	static boolean downloadYoutubeAudio(String url) {
		downloadYoutube(url, true)
	}

	static boolean downloadYoutube(String url, boolean extractAudio) {
		if (!isYoutubeDownloaderAvailable()) {
			println("[WARN] Youtube-dl app is not available, ${extractAudio?'audio':'video'} from url [$url] cant be downloaded.");
			return false
		}
		url = escapeIllegalURLCharacters(url)
		def command = "youtube-dl"
		command += " --title"
		command += " --restrict-filenames"
		if (extractAudio) {
			command += " --extract-audio"
			command += " --audio-format=mp3"
		}
		command += " " + url
		def process = command.execute()
		print("\rDownloading ${extractAudio?'audio':'video'} from url [$url] ...");
		process.waitFor()
		if (process.exitValue() != 0) {
			println "\n[WARN] Something went wrong when downloading ${extractAudio?'audio':'video'} from url [$url]: ${process.text}"
			return false
		}
		println("\rDownloaded ${extractAudio?'audio':'video'} from url [$url].                ");
		return true
	}

	// ####################
	
	private static boolean isYoutubeDownloaderAvailable() {
		def command = "youtube-dl"
		if (SystemUtils.IS_OS_LINUX) command = "which " + command
		if (SystemUtils.IS_OS_MAC) command = "which " + command
		if (SystemUtils.IS_OS_WINDOWS) command = "where " + command

		def process = command.execute()
		process.waitFor()
		return process.exitValue() == 0
	}

	private static boolean isWGetAvailable() {
		def command = "wget"
		if (SystemUtils.IS_OS_LINUX) command = "which " + command
		if (SystemUtils.IS_OS_MAC) command = "which " + command
		if (SystemUtils.IS_OS_WINDOWS) command = "where " + command

		def process = command.execute()
		process.waitFor()
		return process.exitValue() == 0
	}
	
	private static String escapeIllegalURLCharacters(String url){
		// TODO: fazer um escape mais completo disso. Alias, precisa?
		def escapedUrl = url?.replaceAll(' ', '%20')
		escapedUrl = url?.replaceAll('Ç', '%C3%87')
		return escapedUrl
	}
		
}

