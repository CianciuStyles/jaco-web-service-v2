package it.uniroma1.dis.jacov2.server;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.container.filter.UriConnegFilter;

public class MediaTypeFilter extends UriConnegFilter {
	private static final Map<String, MediaType> mappedMediaTypes = new HashMap<String, MediaType>(3);

	static {
		mappedMediaTypes.put("xml",  MediaType.APPLICATION_XML_TYPE);
		mappedMediaTypes.put("json", MediaType.APPLICATION_JSON_TYPE);
		mappedMediaTypes.put("txt",  MediaType.TEXT_PLAIN_TYPE);
	}

	public MediaTypeFilter() {
		super(mappedMediaTypes);
	}
}
