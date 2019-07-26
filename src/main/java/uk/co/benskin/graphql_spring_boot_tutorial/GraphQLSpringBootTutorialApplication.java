package uk.co.benskin.graphql_spring_boot_tutorial;
import java.util.Collections;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GraphQLSpringBootTutorialApplication {

    public static void main(String[] args) {
	SpringApplication app =new SpringApplication(GraphQLSpringBootTutorialApplication.class );
        app.setDefaultProperties(Collections
          .singletonMap("server.port", "8083"));
	app.run(args);
    }
}
