package app.reporter.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;

import app.reporter.dataobjects.ApiKey;
import app.reporter.dataobjects.ApiKey.KeyPassedByMethodEnum;
import app.reporter.dataobjects.Articles;
import app.reporter.dataobjects.NewsFeed;
import app.reporter.dataobjects.NewsFeedDO;
import app.reporter.dataobjects.NewsSource;
import app.reporter.dataobjects.RestResource;
import app.reporter.exception.RestException;

public class NewsApiClient {

	public enum NewsApiSourceEnum {
		cnn, techcrunch
	}

	public static List<NewsFeed> getNewsFeed(String endPointUrl, String source, ApiKey apiKey) throws RestException {

		List<NewsFeed> newsFeeds = new ArrayList<NewsFeed>();
		List<NewsFeed> newsFeedsList = new ArrayList<NewsFeed>();
		try {

			RestTemplate restTemplate = new RestTemplate();

			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(endPointUrl);
			if (source != null && !source.equals(""))
				builder.queryParam("source", source);
			if (apiKey != null) {
				if (apiKey.getPassedBy().equals(KeyPassedByMethodEnum.REQUEST_PARAM))
					builder.queryParam(apiKey.getApiKeyName(), apiKey.getApiKeyValue());
			}

			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

			HttpEntity<?> entity = new HttpEntity<>(headers);

			ParameterizedTypeReference<Map<String, List<NewsFeed>>> newsFeedsTypeRef = new ParameterizedTypeReference<Map<String, List<NewsFeed>>>() {
			};
			// ResponseEntity<Map<String, List<NewsFeed>>> response =
			// restTemplate.exchange(builder.build().encode().toUri(),
			// HttpMethod.GET, entity, newsFeedsTypeRef);

			NewsFeedDO newsFeedDO = restTemplate.getForObject(builder.build().encode().toUri(), NewsFeedDO.class);

			if (newsFeedDO != null && newsFeedDO.getArticles() != null) {
				newsFeedDO.getArticles().forEach(a -> {
					NewsFeed f = new NewsFeed();
					f.setAuthor(a.getAuthor());
					f.setBrief(a.getTitle());
					f.setDetail(a.getDescription());
					f.setUrl(a.getUrl());
					newsFeedsList.add(f);
				});
			}

			// newsFeeds = (ArrayList<NewsFeed>)response.getBody().get("data");
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		return newsFeedsList;
	}

	public static List<NewsSource> getNewsSources(RestResource restResource, String language, ApiKey apiKey)
			throws RestException {

		List<NewsSource> newsSources = new ArrayList<NewsSource>();
		try {

			RestTemplate restTemplate = new RestTemplate();

			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(restResource.getEndpointUrl());
			if (language != null && !language.equals(""))
				builder.queryParam("language", language);

			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

			HttpEntity<?> entity = new HttpEntity<>(headers);

//			ParameterizedTypeReference<Map<String, List<NewsSource>>> newsFeedsTypeRef = new ParameterizedTypeReference<Map<String, List<NewsSource>>>() {};
//			ResponseEntity<Map<String, List<NewsSource>>> response = restTemplate
//					.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, newsFeedsTypeRef);

			try {
				ObjectMapper mapper = new ObjectMapper();
				ObjectNode resource = restTemplate.getForObject(builder.build().encode().toUri(), ObjectNode.class);
				JsonNode sourcesNode =  resource.get("sources");
				ObjectReader reader = mapper.readerFor(new TypeReference<List<NewsSource>>() {});
				newsSources = reader.readValue(sourcesNode);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

//			newsSources = (ArrayList<NewsSource>) response.getBody().get("data");
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		return newsSources;
	}
}
