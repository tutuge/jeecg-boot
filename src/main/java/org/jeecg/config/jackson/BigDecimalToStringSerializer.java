package org.jeecg.config.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.IOException;
import java.math.BigDecimal;

@JacksonStdImpl
public class BigDecimalToStringSerializer extends ToStringSerializer {
    public final static BigDecimalToStringSerializer instance = new BigDecimalToStringSerializer();

    public BigDecimalToStringSerializer() {
        super(Object.class);
    }

    public BigDecimalToStringSerializer(Class<?> handledType) {
        super(handledType);
    }

    @Override
    public boolean isEmpty(SerializerProvider prov, Object value) {
        if (value == null) {
            return true;
        }
        String str = ((BigDecimal) value).stripTrailingZeros().toPlainString();
        return str.isEmpty();
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider)
            throws IOException {
        gen.writeString(((BigDecimal) value).stripTrailingZeros().toPlainString());
    }

    @Override
    public void serializeWithType(Object value, JsonGenerator gen,
                                  SerializerProvider provider, TypeSerializer typeSer)
            throws IOException {
        // no type info, just regular serialization
        serialize(value, gen, provider);
    }
}
