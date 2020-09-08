package com.ipredictworld.application.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.ipredictworld.application.entities.Season;
import org.springframework.boot.jackson.JsonComponent;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@JsonComponent
@Converter
public class SeasonConverter implements AttributeConverter<Season, String> {


    @Override
    public String convertToDatabaseColumn(Season attribute) {
        if (attribute == null)
            return null;

        return attribute.toString();
    }

    @Override
    public Season convertToEntityAttribute(String dbData) {
        return Season.convertStringToSeason(dbData);
    }

    public static class Serialize extends JsonSerializer<Season>{

        @Override
        public void serialize(Season value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            try{
                if (value == null){
                    gen.writeNull();
                } else{
                    gen.writeString(value.toString());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static class Deserialize extends JsonDeserializer<Season>{
        @Override
        public Season deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            try{
                String serviceAsString = p.getText();
                if (serviceAsString == null){
                    return null;
                } else{
                    return Season.convertStringToSeason(serviceAsString);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
