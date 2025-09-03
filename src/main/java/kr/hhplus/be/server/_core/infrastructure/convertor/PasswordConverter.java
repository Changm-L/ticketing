package kr.hhplus.be.server._core.infrastructure.convertor;

import java.util.regex.Pattern;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Converter
@RequiredArgsConstructor
public class PasswordConverter implements AttributeConverter<String, String> {

    private final PasswordEncoder passwordEncoder;
    private final Pattern         BCRYPT_PATTERN = Pattern.compile("\\A\\$2([aby])?\\$\\d\\d\\$[./0-9A-Za-z]{53}");

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData;
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }

        if (BCRYPT_PATTERN.matcher(attribute).matches()) {
            return attribute;
        }

        return passwordEncoder.encode(attribute);
    }
}
