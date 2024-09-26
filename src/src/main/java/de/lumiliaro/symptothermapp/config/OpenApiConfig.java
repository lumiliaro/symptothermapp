package de.lumiliaro.symptothermapp.config;

import java.util.List;

import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class OpenApiConfig {

        private final BuildProperties buildProperties;

        @Bean
        public OpenAPI customOpenAPI() {
                String version = (buildProperties != null) ? buildProperties.getVersion() : "unknown";

                return new OpenAPI()
                                .info(new Info()
                                                .title("Symptothermapp Backend API")
                                                .version(version)
                                                .description("Symptothermapp Backend API für das Frontend")
                                                .license(new License().name("MIT License")
                                                                .url("https://opensource.org/licenses/MIT")))
                                .servers(List.of(
                                                new Server().url("/").description("Default Server URL")));
        }
}
