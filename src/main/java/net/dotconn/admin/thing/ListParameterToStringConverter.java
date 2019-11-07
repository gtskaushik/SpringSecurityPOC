package net.dotconn.admin.thing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Converter
public class ListParameterToStringConverter implements AttributeConverter<List<Parameter>, String> {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Parameter> attribute) {
        try {
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    @Override
    public List<Parameter> convertToEntityAttribute(String dbData) {
        try {
            return mapper.readValue(dbData, new TypeReference<List<Parameter>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static void main(String[] args) {
        ListParameterToStringConverter converter = new ListParameterToStringConverter();
        List<Parameter> parameters = converter.convertToEntityAttribute("[{\"name\":\"s001\",\"displayName\":\"s001\"},{\"name\":\"s002\",\"displayName\":\"s002\"}]");
        System.out.println(parameters);

    }
}
