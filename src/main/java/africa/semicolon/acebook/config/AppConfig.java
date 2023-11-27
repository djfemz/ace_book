package africa.semicolon.acebook.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AppConfig {
    @Value("${mail.api.key}")
    private String mailApiKey;
    @Value("${mail.api.url}")
    private String mailServiceUrl;

    @Value("${cloud.api.name}")
    private String cloudApiName;
    @Value("${cloud.api.key}")
    private String cloudApiKey;
    @Value("${cloud.api.secret}")
    private String cloudApiSecret;
    @Bean
    public ModelMapper modelMapper(){
        var mapper =  new ModelMapper();
        mapper.getConfiguration().setDeepCopyEnabled(true);
        return mapper;
    }
}
