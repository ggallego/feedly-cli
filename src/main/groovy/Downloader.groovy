import static groovyx.net.http.Method.*

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.lang.math.RandomUtils;

import groovyx.net.http.HTTPBuilder

class Downloader {

	static boolean downloadMedia(String url) {
		downloadMedia(url, -1)
	}

	static boolean downloadMedia(String url, Integer length) {
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
		
		int bytes = 0;
		int filesize = connection.getContentLength()?:length;
		
		OutputStream outstream = new FileOutputStream(filename);
		try {
			while (true) {
				outstream.write(instream.readUnsignedByte());
				bytes++;
				if (bytes % 100000 == 0)
					print("\rDownloading [$filename] with [$filesize] bytes. Stats: percentage [${Math.round(bytes*100/filesize)}%], bytes [${bytes}].");
			}
		} catch (EOFException e) {
			outstream.close();
			println("\rDownloaded [$filename] with [$bytes] bytes.                                                       ");
		}

		if (file.size() != filesize) {
			println "[WARN] Something went wrong on file [$filename]... received [${file.size()}] which does not match the calculated size of [${filesize}]."
			return false
		}
		return true
	}

	static boolean downloadYoutube(String url) {
		url = escapeIllegalURLCharacters(url)
		def command = "youtube-dl --title --restrict-filenames " + url
		def process = command.execute()
		print("\rDownloading youtube url [$url] ...");
		process.waitFor();
		if (process.exitValue() != 0) {
			println "[WARN] Something went wrong downloading youtube url [$url]: ${process.text}"
			return false
		}
		println("\rDownloaded youtube url [$url].                ");
		return true
	}

	static boolean downloadYoutubeAudio(String url) {
		url = escapeIllegalURLCharacters(url)
		def command = "youtube-dl --title --restrict-filenames --extract-audio --audio-format=mp3 " + url
		def process = command.execute()
		print("\rDownloading audio from youtube url [$url] ...");
		process.waitFor();
		if (process.exitValue() != 0) {
			println "[WARN] Something went wrong downloading audio from youtube url [$url]: ${process.text}"
			return false
		}
		println("\rDownloaded audio from youtube url [$url].                ");
		return true
	}

	// ####################
	
	static String escapeIllegalURLCharacters(String url){
		// TODO: fazer um escape mais completo disso. Alias, precisa?
		def escapedUrl = url?.replaceAll(' ', '%20')
		escapedUrl = url?.replaceAll('Ç', '%C3%87')
		return escapedUrl
	}
		
}













