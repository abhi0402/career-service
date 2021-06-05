package com.bootcamp.router;

import com.bootcamp.handler.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class JobRouter {

    @Autowired
    private JobHandler jobHandler;

    @Bean
    public RouterFunction<ServerResponse> jobRoute() {
        return RouterFunctions.route()
                .POST("/createJobProfile", accept(MediaType.APPLICATION_JSON), jobHandler::saveJob)
                .GET("/findEmpForJobID", jobHandler::findEmpsByJobID)
                .GET("/getJobProfileFromCache", jobHandler::getJobFromCache)
                .build();
    }

}
