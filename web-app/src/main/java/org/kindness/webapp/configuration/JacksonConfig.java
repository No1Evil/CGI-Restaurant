package org.kindness.webapp.configuration;

import com.hubspot.jackson3.datatype.protobuf.ProtobufModule;
import org.kindness.common.model.util.JsonUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

@Configuration
public class JacksonConfig {
    @Bean
    public ObjectMapper objectMapper(){
        return JsonUtil.getMapper();
    }
}
