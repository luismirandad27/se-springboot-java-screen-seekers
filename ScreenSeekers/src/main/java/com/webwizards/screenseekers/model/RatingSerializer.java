/*
 * Class File: RatingSerializer.java
 * 
 * ------------
 * Description:
 * ------------
 * The Rating table is an intermediate table from the combination of Users and Movies
 * 
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

public class RatingSerializer extends StdSerializer<Rating>{

	public RatingSerializer() {
		this(null);
	}
	
	public RatingSerializer(Class<Rating> t) {
		super(t);
	}
	
	@Override
    public void serialize(Rating value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeNumberField("userRating", value.getUserRating());
        gen.writeStringField("comment", value.getComment());
        
        //Retrieving the information of the movie
        gen.writeNumberField("movieId", value.getMovie().getId());
        gen.writeStringField("title", value.getMovie().getTitle());
        
        gen.writeObjectField("createdAt", value.getCreatedAt());
        gen.writeObjectField("updatedAt", value.getUpdatedAt());
        gen.writeObjectField("deletedAt", value.getDeletedAt());
        gen.writeEndObject();
    }
	
}
