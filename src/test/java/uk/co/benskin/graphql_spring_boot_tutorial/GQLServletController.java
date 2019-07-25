package uk.co.benskin.graphql_spring_boot_tutorial;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import graphql.schema.GraphQLSchema;
//import graphql.schema.GraphQLSchema.Builder;
//import graphql.schema.GraphQLType; 
import graphql.servlet.OsgiGraphQLServlet; 

@RestController
public class GQLServletController {
 
    @Autowired
    private OsgiGraphQLServlet graphQLServlet;
    
 
    
    @Bean 
    @Lazy
    public OsgiGraphQLServlet getServlet(){
//    	Builder schemaBuoilder = GraphQLSchema.newSchema();
//    	Set<GraphQLType> additionalTypes = new  HashSet<>();
//    	GraphQLType e = new GraphQLType() {
//			
//			@Override
//			public String getName() {
//				 return "junit";
//			}
//		};
//		additionalTypes.add(e );
//		schemaBuoilder.build(additionalTypes );
//    	GraphQLSchema schema = schemaBuoilder.build();
		OsgiGraphQLServlet retval = new OsgiGraphQLServlet(  );
    	return retval;
    }

    @RequestMapping("/graphql")
    public void graphql(HttpServletRequest request, HttpServletResponse response) throws Exception {
        graphQLServlet.service(request, response);
    }
 }