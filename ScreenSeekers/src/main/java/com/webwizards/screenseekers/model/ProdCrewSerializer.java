/**
 * Class File: ProdCrewSerializer.java
 * 
 * ------------
 * Description:
 * ------------
 * The ProductionCrew table is an intermediate table from the combination of Movies and Crews
 * 
 * 
 * @author Regal Cruz
 * @version 1.0
 * 
 */

package com.webwizards.screenseekers.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class ProdCrewSerializer extends StdSerializer<ProductionCrew>{
	
	public ProdCrewSerializer() {
		this(null);
	}
	
	public ProdCrewSerializer(Class<ProductionCrew> t) {
		super(t);
	}
	
	@Override
    public void serialize(ProductionCrew value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeStringField("charName", value.getCharacterName());
        gen.writeStringField("role", value.getMovieRole());
        
        //Retrieving the information of the movie
        gen.writeNumberField("movieId", value.getMovie().getId());
        gen.writeStringField("title", value.getMovie().getTitle());
        
        //Retrieving the information of the movie
        gen.writeNumberField("crewId", value.getCrewMember().getId());
        gen.writeStringField("firstName", value.getCrewMember().getFirstName());
        gen.writeStringField("lastName", value.getCrewMember().getLastName());
        
        gen.writeObjectField("createdAt", value.getCreatedAt());
        gen.writeObjectField("updatedAt", value.getUpdatedAt());
        gen.writeEndObject();
    }
}
