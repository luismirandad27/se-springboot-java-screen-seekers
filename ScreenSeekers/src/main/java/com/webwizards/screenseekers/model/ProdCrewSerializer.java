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
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
        gen.writeFieldName("movie");
        gen.writeStartObject();
        gen.writeNumberField("movieId", value.getMovie().getId());
        gen.writeStringField("title", value.getMovie().getTitle());
        gen.writeEndObject();
        
        //Retrieving the information of the movie
        gen.writeFieldName("crewMember");
        gen.writeStartObject();
        gen.writeNumberField("crewId", value.getCrewMember().getId());
        gen.writeStringField("firstName", value.getCrewMember().getFirstName());
        gen.writeStringField("lastName", value.getCrewMember().getLastName());
        gen.writeEndObject();
        
        gen.writeObjectField("createdAt", value.getCreatedAt());
        gen.writeObjectField("updatedAt", value.getUpdatedAt());
        gen.writeEndObject();
    }
}
