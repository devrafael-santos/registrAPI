package com.raffasdev.registrapi;

import com.raffasdev.registrapi.config.EnvInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RegistrApiApplication {

    public static void main(String[] args) {
        var app = new SpringApplication(RegistrApiApplication.class);
        app.addInitializers(new EnvInitializer());
        app.run(args);
    }

}
