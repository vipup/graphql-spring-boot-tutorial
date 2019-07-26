package uk.co.benskin.graphql_spring_boot_tutorial;
 
import static graphql.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;  
import java.io.IOException;   
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.boot.test.context.SpringBootTest; 
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner; 

import com.fasterxml.jackson.databind.JsonNode;
import com.oembedler.moon.graphiql.boot.GraphiQLAutoConfiguration;


@ContextConfiguration(classes = {  GraphiQLAutoConfiguration.class, GraphQLSpringBootTutorialApplication.class})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GraphiqlCsrfEnabledTest { 
	/** Logger */
	private static final Logger LOG = LoggerFactory.getLogger(GraphiqlCsrfEnabledTest.class); 
    @Autowired
    private GraphQLTestUtils graphQLTestUtils;
    
    @Test
    public void get_pets() throws IOException {
        JsonNode parsedResponse = graphQLTestUtils.perform("graphql/pets.graphql");
        assertNotNull(parsedResponse);
        assertNotNull(parsedResponse.get("data"));
        assertNotNull(parsedResponse.get("data").get("pets"));
        assertEquals("MAMMOTH", parsedResponse.get("data").get("pets").get(0).get("type").asText());
        LOG.info("Done:{}",this.toString());
    }
      
  
}


