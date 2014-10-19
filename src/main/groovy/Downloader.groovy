import java.util.zip.GZIPInputStream;

class Downloader {

	static boolean download(String url) {
		download(url, null)
	}

	static boolean download(String url, long length) {
		download(url, null, null)
	}

	static boolean download(String url, long length, String type) {
		println "Downloading file [${url.tokenize('/')[-1]}] with informed [${url.length}] bytes."
		def file = new FileOutputStream(url.tokenize("/")[-1])
		def out = new BufferedOutputStream(file)
		out << new URL(url).openStream()
		out.close()
		return true
	}
	
	static main(args) {
		download("http://cbn.globoradio.globo.com/global/podcast.mp3?audio=2014/colunas/cortella_140926.mp3&materiaId=1041789&categoriaId=270", 486946, "audio/mpeg")
	}
	
	/*
URLConnection connection = new URL(sourceFileWebAddress).openStream();
InputStream stream = connection.getInputStream();

System.out.println("total size: "+connection.getContentLength();//size

BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
FileOutputStream fileOutputStream = new FileOutputStream(targetFile);

int count;
byte buffer[] = new byte[1024];

while ((count = bufferedInputStream.read(buffer, 0, buffer.length)) != -1)
fileOutputStream.write(buffer, 0, count);	 
	 */
	
}
