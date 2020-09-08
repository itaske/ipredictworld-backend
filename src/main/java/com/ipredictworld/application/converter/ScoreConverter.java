package com.ipredictworld.application.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.ipredictworld.application.entities.Score;
import org.apache.kafka.common.header.Headers;
import org.springframework.boot.jackson.JsonComponent;

import javax.persistence.AttributeConverter;
import java.io.IOException;

@JsonComponent
public class ScoreConverter implements AttributeConverter<Score, String> {


    @Override
    public String convertToDatabaseColumn(Score attribute) {
        return attribute.toString();
    }

    @Override
    public Score convertToEntityAttribute(String dbData) {
        return Score.covertFromStringToScore(dbData);
    }

    public static class ScoreSerializer extends JsonSerializer<Score>{

        @Override
        public void serialize(Score value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
           try{
               if (value == null){
                   gen.writeNull();
               } else{
                   gen.writeString(value.toString());
               }
           }catch(Exception e){
               e.printStackTrace();
           }
        }
    }

    public static class ScoreDeserializer extends JsonDeserializer<Score> {


        @Override
        public Score deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
           try{

               String score = p.getText();
               if (score == null){
                   return null;
               }else {
                   return Score.covertFromStringToScore(p.getValueAsString());
               }
           }catch(Exception e){
               e.printStackTrace();
           }
           return null;
        }
    }
}
