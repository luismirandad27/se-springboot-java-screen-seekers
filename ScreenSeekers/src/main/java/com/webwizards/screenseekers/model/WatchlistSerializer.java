/**
 * Class File: WatchlistSerializer.java
 * 
 * ------------
 * Description:
 * ------------
 * The WatchlistDetail table is an intermediate table from the combination of Watchlists and Movies
 * This serializer class will help us to retrieve the information of the watchlist and each item.
 * 
 * @author Luis Miguel Miranda
 * @version 1.0
 * 
 */

package com.webwizards.screenseekers.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class WatchlistSerializer extends StdSerializer<WatchlistDetail> {

	public WatchlistSerializer() {
		this(null);
	}
	
	public WatchlistSerializer(Class<WatchlistDetail> t) {
		super(t);
	}
	
	@Override
    public void serialize(WatchlistDetail value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        
        
        gen.writeNumberField("id", value.getId());
        
        if (value.getMovie() != null) {
        	//Retrieving the information of the movie
        	gen.writeFieldName("movie");
        	gen.writeStartObject();
            gen.writeNumberField("movieId", value.getMovie().getId());
            gen.writeStringField("title", value.getMovie().getTitle());
            gen.writeStringField("posterImage",value.getMovie().getPosterImage());
            gen.writeEndObject();
        }
        
        
        gen.writeEndObject();
    }

}
