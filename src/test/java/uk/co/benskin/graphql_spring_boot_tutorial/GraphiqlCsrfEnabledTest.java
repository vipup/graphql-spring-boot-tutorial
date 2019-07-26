package uk.co.benskin.graphql_spring_boot_tutorial;

  
import static graphql.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import java.io.IOException; 
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan; 
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc; 
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.oembedler.moon.graphiql.boot.GraphiQLAutoConfiguration;


@ContextConfiguration(classes = {  GraphiQLAutoConfiguration.class, GraphQLSpringBootTutorialApplication.class})
@ComponentScan("uk.co.benskin.graphql_spring_boot_tutorial.resolvers")
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
    }
      
  
 
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext context;

	@Before
	public void init() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	
}


