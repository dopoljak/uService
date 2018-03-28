package com.ilirium.webservice.commons;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * @author DoDo
 */
@Provider
public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

    private final ObjectMapper mapper;

    public ObjectMapperContextResolver() {
        mapper = new ObjectMapper();
        mapper.getSerializerProvider().setNullKeySerializer(new MyDtoNullKeySerializer());
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }

    class MyDtoNullKeySerializer extends StdSerializer<Object> {

        public MyDtoNullKeySerializer() {
            this(null);
        }

        public MyDtoNullKeySerializer(Class<Object> t) {
            super(t);
        }

        @Override
        public void serialize(Object nullKey, JsonGenerator jsonGenerator, SerializerProvider unused) throws IOException, JsonProcessingException {
            jsonGenerator.writeFieldName("");
        }
    }






    /*
    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@code")
    abstract class TestMixIn {

    }
    */


    //mapper.addMixIn(Currency.class, TestMixIn.class);
    //mapper.setSerializationInclusion(Include.ALWAYS);
    //mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    //mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, true);

        /*
        JacksonAnnotationIntrospector ignoreJsonTypeInfoIntrospector = new JacksonAnnotationIntrospector() {
            @Override
            protected TypeResolverBuilder<?> _findTypeResolver(
                    MapperConfig<?> config, Annotated ann, JavaType baseType) {
                if (!ann.hasAnnotation(JsonTypeInfo.class)) {
                    return super._findTypeResolver(config, ann, baseType);
                }
                return null;
            }
        };
        mapper.setAnnotationIntrospector(ignoreJsonTypeInfoIntrospector);
        */

}
