package cn.zeemoo.rbac.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;

/**
 * jackson配置
 *
 * @author zhang.shushan
 * @date 2018/5/24
 */
@Configuration
public class JacksonConfig {

    /**
     * 通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化
     * 默认 Include.Include.ALWAYS
     * 属性为默认值不序列化 Include.NON_DEFAULT
     * 属性为 空（""） 或者为 NULL 都不序列化，Include.NON_EMPTY,则返回的json是没有这个字段的。这样对移动端会更省流量
     * 属性为NULL 不序列化,就是为null的字段不参加序列化 Include.NON_NULL
     * 设置方法为 objectMapper.setSerializationInclusion(Include.NON_EMPTY);
     *
     * @param builder
     * @return
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {

        ObjectMapper objectMapper = builder.createXmlMapper(false).build();

        // 字段保留，将null值转为""
        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeString("");
            }
        });
        return objectMapper;
    }

}
