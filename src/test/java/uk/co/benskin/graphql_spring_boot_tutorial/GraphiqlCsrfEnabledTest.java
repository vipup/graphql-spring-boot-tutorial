package uk.co.benskin.graphql_spring_boot_tutorial;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.oembedler.moon.graphiql.boot.GraphiQLAutoConfiguration;

import graphql.schema.GraphQLFieldDefinition;
import uk.co.benskin.graphql_spring_boot_tutorial.resolvers.Query;

import static graphql.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
//@WebAppConfiguration()
//  GraphiQLAutoConfiguration.class,GQLServletController.class  ,Query.class,GraphQLSpringBootTutorialApplication.class, GQLServletController
@ContextConfiguration(classes = {  GraphiQLAutoConfiguration.class, GraphQLSpringBootTutorialApplication.class})
@ComponentScan("uk.co.benskin.graphql_spring_boot_tutorial.resolvers")
@RunWith(SpringRunner.class)
//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GraphiqlCsrfEnabledTest {
	private static final String GRAPHQL_API_PATH = "/graphql";
	/** Logger */
	private static final Logger LOG = LoggerFactory.getLogger(GraphiqlCsrfEnabledTest.class);
    @Autowired
    private GraphQLTestUtils graphQLTestUtils;
    
    @Test
    public void get_pets() throws IOException {
        JsonNode parsedResponse = graphQLTestUtils.perform("graphql/pets.graphql");
        assertNotNull(parsedResponse);
        assertNotNull(parsedResponse.get("data"));
        assertNotNull(parsedResponse.get("data").get("post"));
        assertEquals("1", parsedResponse.get("data").get("post").get("id").asText());
    }
    
    
	@Test
	public void testGetBook() throws Exception {
		String query = " { pets { type  age id name    } }";
		ResultActions graphqlresultTmp = doGraphQLRequest(query);
		graphqlresultTmp.andExpect(status().isOk()).andExpect(content().string("xx"));
		LOG.error("{}", graphqlresultTmp);
	}

	private ResultActions doGraphQLRequest(String qPar) throws Exception {
		Map<String, String> variables = null;
		String generateRequest = generateRequest(qPar, variables);
		MockHttpServletRequestBuilder getTMP = post(GRAPHQL_API_PATH)
				// get(GRAPHQL_API_PATH)
				.contentType(MediaType.APPLICATION_JSON).content(generateRequest).param("id", "1234567")
				.param("name", "Joe").param("gender", "M").with(testUser()).with(csrf());
		ResultActions graphqlresultTmp = mockMvc.perform(getTMP);
		return graphqlresultTmp;
	}

	private String generateRequest(String query, Map<String, String> variables) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("query", query);
		if (variables != null) {
			jsonObject.put("variables", Collections.singletonMap("input", variables));
		}
		return jsonObject.toString();
	}

	@Test
	public void testGetPet() throws Exception {
		MockHttpServletRequestBuilder getTMP = get(GRAPHQL_API_PATH).contentType(MediaType.APPLICATION_JSON)
				.param("id", "1234567").param("name", "Joe").param("gender", "M").with(testUser()).with(csrf());
		ResultActions andExpect = mockMvc.perform(getTMP).andExpect(status().isNotFound());
		LOG.error("{}", andExpect);
	}

	@Test
	public void testGraphiql() throws Exception {
		MockHttpServletRequestBuilder getTMP = get("/graphiql").contentType(MediaType.APPLICATION_JSON)
				.param("id", "1234567").param("name", "Joe").param("gender", "M").with(testUser()).with(csrf());
		ResultActions andExpect = mockMvc.perform(getTMP).andExpect(status().isOk());
		LOG.error("{}", andExpect);
	}

	@Test
	public void testGraphql() throws Exception {
		LOG.debug("1");
		MockHttpServletRequestBuilder getTMP = get(GRAPHQL_API_PATH).contentType(MediaType.APPLICATION_JSON)
				.param("id", "1234567").param("name", "Joe").param("gender", "M").with(testUser()).with(csrf());
		ResultActions andExpect = mockMvc.perform(getTMP).andExpect(status().is4xxClientError())
				.andExpect(status().is(400));
		LOG.error("{}", andExpect);
	}

	private RequestPostProcessor testUser() {
		return user("user1").password("user1Pass").roles("USER");
	}

	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext context;

	@Before
	public void init() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	
}


