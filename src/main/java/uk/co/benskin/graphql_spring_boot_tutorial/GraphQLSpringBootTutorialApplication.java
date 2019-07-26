package uk.co.benskin.graphql_spring_boot_tutorial;
import java.util.Collections;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc; 

@SpringBootApplication
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {  "uk.co.benskin.graphql_spring_boot_tutorial.resolvers" } )
public class GraphQLSpringBootTutorialApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
	SpringApplication app =new SpringApplication(GraphQLSpringBootTutorialApplication.class );
        app.setDefaultProperties(Collections
          .singletonMap("server.port", "8083"));
	app.run(args);
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) { 
        return builder.sources(GraphQLSpringBootTutorialApplication.class).profiles("tomcat-dev");
    }
}
