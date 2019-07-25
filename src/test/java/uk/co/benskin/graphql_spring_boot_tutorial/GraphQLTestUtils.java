package uk.co.benskin.graphql_spring_boot_tutorial;
import com.fasterxml.jackson.core.util.BufferRecyclers;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
@Component
public class GraphQLTestUtils {

    @Value("classpath:graphql/query-wrapper.json")
    private Resource queryWrapperFile;
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private TestRestTemplate restTemplate;
    private String queryWrapper;

    @PostConstruct
    public void initQueryWrapper() throws IOException {
        queryWrapper = loadResource(queryWrapperFile);
    }

    private String createJsonQuery(String graphql) {
        return queryWrapper.replace("__payload__", escapeQuery(graphql));
    }

    private String escapeQuery(String graphql) {
        StringBuilder result = new StringBuilder();
        BufferRecyclers.getJsonStringEncoder().quoteAsString(graphql, result);
        return result.toString();
    }

    private JsonNode parse(String payload) throws IOException {
        return new ObjectMapper().readTree(payload);
    }

    private String loadQuery(String location) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + location);
        return loadResource(resource);
    }

    private String loadResource(Resource resource) throws IOException {
        try (InputStream inputStream = resource.getInputStream()) {
            return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        }
    }

    public JsonNode perform(String graphqlResource) throws IOException {
        String graphql = loadQuery(graphqlResource);
        String payload = createJsonQuery(graphql);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> httpEntity = new HttpEntity<>(payload, headers);
        ResponseEntity<String> response = restTemplate.exchange("/graphql", HttpMethod.POST, httpEntity, String.class);

        String body = response.getBody();
		return parse(body);
    }

}