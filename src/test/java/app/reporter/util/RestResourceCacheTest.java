package app.reporter.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RestResourceCacheTest {

	@Test
	public void testGetRestResourceFromCache() {

		assertEquals("news_api_rest_get",RestResourceCache.getResource("news_api_rest_get").getResourceId());
	}
	
}
