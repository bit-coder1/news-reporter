package app.reporter.util;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import app.reporter.dataobjects.ApiKey;
import app.reporter.dataobjects.RestResource;

public class RestResourceCache {

	private static Map<String, RestResource> restResourcesCache = null;
	private static Map<String, ApiKey> restResourcesAPIKeys = null;

	public static boolean refreshCache() {

		boolean status = false;
		try {
			if (restResourcesCache == null)
				restResourcesCache = new ConcurrentHashMap<String, RestResource>();

			// load rest resources cache
			ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
			try {
				List<RestResource> restResources = mapper.readValue(Thread.currentThread().getContextClassLoader().getResourceAsStream("rest-resources-config.yaml"),new TypeReference<List<RestResource>>(){});
				restResources.forEach(r -> System.out.println(r.getName()+" - "+ ReflectionToStringBuilder.toString(r, ToStringStyle.MULTI_LINE_STYLE)));
				restResources.forEach(r -> restResourcesCache.put(r.getResourceId(),r));
				//System.out.println(ReflectionToStringBuilder.toString(restResources, ToStringStyle.MULTI_LINE_STYLE));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			 if(restResourcesAPIKeys == null)
			 restResourcesAPIKeys = new ConcurrentHashMap<String,ApiKey>();
			
			// load resources keys cache
			try {
				InputStream apiKeysInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("rest-resources-keys.yaml");
				if(apiKeysInputStream != null) {
					List<ApiKey> restResourcesApiKeys = mapper.readValue(apiKeysInputStream,new TypeReference<List<ApiKey>>(){});
					restResourcesApiKeys.forEach(r -> System.out.println(r.getResourceId()+" - "+ ReflectionToStringBuilder.toString(r, ToStringStyle.MULTI_LINE_STYLE)));
					restResourcesApiKeys.forEach(r -> restResourcesAPIKeys.put(r.getResourceId(),r));
				}
				else {
					System.out.println("Api Keys file: rest-resources-keys.yaml was NOT found - make sure you have this placed in classpath");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public static RestResource getResource(String resourceId) {
		if(restResourcesCache == null)
			refreshCache();
		return restResourcesCache.get(resourceId);
	}

	public static ApiKey getResourceApiKey(String resourceId) {
		if(restResourcesAPIKeys == null)
			refreshCache();
		return restResourcesAPIKeys.get(resourceId);
	}

}
