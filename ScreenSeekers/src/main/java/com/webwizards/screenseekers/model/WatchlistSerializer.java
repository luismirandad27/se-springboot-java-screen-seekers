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
        
        //Retrieving the information of the movie
        gen.writeNumberField("movieId", value.getMovie().getId());
        gen.writeStringField("title", value.getMovie().getTitle());
        
        gen.writeObjectField("createdAt", value.getCreatedAt());
        gen.writeObjectField("updatedAt", value.getUpdatedAt());
        gen.writeObjectField("deletedAt", value.getDeletedAt());
        gen.writeEndObject();
    }

}
