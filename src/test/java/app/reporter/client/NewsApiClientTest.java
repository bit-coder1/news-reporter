package app.reporter.client;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.reporter.dataobjects.ApiKey;
import app.reporter.dataobjects.NewsFeed;
import app.reporter.dataobjects.NewsSource;
import app.reporter.dataobjects.RestResource;
import app.reporter.exception.RestException;
import app.reporter.util.RestResourceCache;

public class NewsApiClientTest {

	//@Test
	public void testGetNewsFromSourceTechCrunch() {
		String source = "techcrunch";
		String resourceId = "news_api_rest_get";

		try {
			RestResource resource = RestResourceCache.getResource(resourceId);
			ApiKey apiKey = RestResourceCache.getResourceApiKey(resourceId);
			List<NewsFeed> newsFeeds = NewsApiClient.getNewsFeed(resource.getEndpointUrl(), source, apiKey);
			assertEquals(4, newsFeeds.size());
		} catch (RestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	//@Test
	public void testGetNewsFromSourceCNN() {
		String source = "cnn";
		String resourceId = "news_api_rest_get";

		try {
			RestResource resource = RestResourceCache.getResource(resourceId);
			ApiKey apiKey = RestResourceCache.getResourceApiKey(resourceId);
			List<NewsFeed> newsFeeds = NewsApiClient.getNewsFeed(resource.getEndpointUrl(), source, apiKey);
			assertEquals(10, newsFeeds.size());
		} catch (RestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testGetNewsSourcesForLanguage_English() {
		String language = "en";
		String resourceId = "news_api_get_sources";

		try {
			RestResource resource = RestResourceCache.getResource(resourceId);
			ApiKey apiKey = RestResourceCache.getResourceApiKey(resourceId);
			List<NewsSource> sources = NewsApiClient.getNewsSources(resource, language, apiKey);
			assertEquals(60, sources.size());
		} catch (RestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
