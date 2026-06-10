package com.alita.medscript.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("MedScript API")
                        .description("""
                                Sistema de gestão clínica e prontuário eletrônico.

                                Funcionalidades disponíveis:

                                - Cadastro de pacientes
                                - Profissionais de saúde
                                - Agenda médica
                                - Atendimento clínico
                                - Prescrições médicas
                                - Solicitação de exames
                                - Fila de espera
                                - Gestão hospitalar

                                Projeto desenvolvido utilizando Spring Boot e arquitetura baseada em DDD.
                                """)
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Alita Kallyne")
                                .email("alytakallyne@email.com"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")))

                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Ambiente Local")
                ))

                .externalDocs(
                        new ExternalDocumentation()
                                .description("Repositório do Projeto")
                                .url("https://github.com/alitakallyne/medscript"));
        }
}
