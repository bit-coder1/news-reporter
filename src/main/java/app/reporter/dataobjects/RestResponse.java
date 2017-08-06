package app.reporter.dataobjects;

import org.apache.http.HttpResponse;

public class RestResponse {

    private HttpResponse httpResponse;

	public HttpResponse getHttpResponse() {
		return httpResponse;
	}

	public void setHttpResponse(HttpResponse httpResponse) {
		this.httpResponse = httpResponse;
	}
    
}
