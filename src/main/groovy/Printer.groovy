import static groovy.json.JsonOutput.*


class Printer {

	static void printHTTPResponse(response) {
		println("===============>")
		println("HTTP Status: $response.status")
		println("HTTP ContentType: $response.contentType")
		response.headers.each {println "HTTP Header: $it" }
		println("HTTP Body: $response.data.toString")
		println("<===============")
	}
	
	static void printAsJson(String label, List list) {
		if (label != null)
			print("$label => ")
		println prettyPrint(toJson(list))
	}

}
