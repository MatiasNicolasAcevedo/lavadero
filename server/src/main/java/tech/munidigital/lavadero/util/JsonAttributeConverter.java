package tech.munidigital.lavadero.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.postgresql.util.PGobject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Converter(autoApply = false)
public class JsonAttributeConverter implements AttributeConverter<Map<String, Object>, PGobject> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public PGobject convertToDatabaseColumn(Map<String, Object> attribute) {
        try {
            PGobject jsonObject = new PGobject();
            jsonObject.setType("json");
            // Si el mapa es nulo o está vacío, guardamos "{}"
            String json = (attribute == null || attribute.isEmpty())
                    ? "{}"
                    : objectMapper.writeValueAsString(attribute);
            jsonObject.setValue(json);
            return jsonObject;
        } catch (JsonProcessingException | SQLException e) {
            throw new IllegalArgumentException("Error convirtiendo mapa a JSON", e);
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(PGobject dbData) {
        if (dbData == null || dbData.getValue() == null || dbData.getValue().isEmpty()) {
            return new HashMap<>();
        }
        try {
            return objectMapper.readValue(dbData.getValue(), new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            throw new IllegalArgumentException("Error convirtiendo JSON a mapa", e);
        }
    }

}