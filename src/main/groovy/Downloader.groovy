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

	static boolean download(String url) {
		download(url, -1)
	}

	static boolean download(String url, Integer length) {
		String filename = url.tokenize("/")[-1]
		filename = filename.replaceAll(/\&.*/, "")
		filename = filename.replaceAll(/\?.*/, "")
		File file = new File(filename)
		while (file.exists()) {
			print("[WARN] File ${filename} exists, generating new filename.");
			filename += ".${RandomUtils.nextInt()}"
			file = new File(filename)
		}
			
		URLConnection connection = new URL(url).openConnection();
		DataInputStream instream = new DataInputStream(connection.getInputStream());
		OutputStream outstream = new FileOutputStream(filename);
		
		int bytes = 0;
		int filesize = connection.getContentLength()?:length;
		
		try {
			while (true) {
				outstream.write(instream.readUnsignedByte());
				bytes++;
				if (bytes % 100000 == 0)
					print("\rDownloading [$filename] with ${bytes} bytes... of ${filesize} bytes.");
			}
		} catch (EOFException e) {
			outstream.close();
			println("\rDownloaded [$filename] with ${bytes} bytes.                                   ");
		}

		if (file.size() != filesize) {
			println "[WARN] Something went wrong... received [${file.size()}] which does not match calculated size of [${filesize}]."
			return false
		}
		return true
	}
	
}













