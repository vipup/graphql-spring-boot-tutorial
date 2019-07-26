package uk.co.benskin.graphql_spring_boot_tutorial;

   
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc; 

 
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {  "uk.co.benskin.graphql_spring_boot_tutorial.resolvers" } )
public class GQLServletConfigurator extends SpringBootServletInitializer {

 
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) { 
        return builder.sources(GraphQLSpringBootTutorialApplication.class).profiles("tomcat-dev");
    }
}
