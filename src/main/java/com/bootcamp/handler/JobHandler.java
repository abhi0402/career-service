package com.bootcamp.handler;

import com.bootcamp.dto.CommonResponseDto;
import com.bootcamp.dto.EmployeeDto;
import com.bootcamp.dto.JobDto;
import com.bootcamp.dto.JobDto2;
import com.bootcamp.model.Job;
import com.bootcamp.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Optional;

@Component
public class JobHandler {

    @Autowired
    private JobService jobService;

    public Mono<ServerResponse> saveJob(ServerRequest request) {
        Mono<JobDto> jobDtoMono = request.bodyToMono(JobDto.class);
        Mono<JobDto> jobDtoMonoResponse = jobService.saveJob(jobDtoMono);
        return jobDtoMonoResponse.flatMap(res -> {
            if (res.getStatus().equalsIgnoreCase("Created"))
                return ServerResponse.created(URI.create("/createJobProfile/" + res.getId())).bodyValue(res);
            else
                return ServerResponse.badRequest().bodyValue(res);
        });
    }

    public Mono<ServerResponse> findEmpsByJobID(ServerRequest request) {
        Optional<String> jobId = request.queryParam("job_id");
        Flux<EmployeeDto> empFluxResponse = Flux.empty();
        if (!jobId.isPresent())
            return ServerResponse.badRequest().bodyValue(new CommonResponseDto("No query param provided in the request"));
        empFluxResponse = jobService.findEmpsByJobId(Integer.parseInt(jobId.get()));
        return ServerResponse.ok().body(empFluxResponse, EmployeeDto.class);
    }

    public Mono<ServerResponse> getJobFromCache(ServerRequest request) {
        Optional<String> jobId = request.queryParam("job_id");
        Mono<JobDto2> jobMonoResponse = Mono.empty();
        if (!jobId.isPresent())
            return ServerResponse.badRequest().bodyValue(new CommonResponseDto("No query param provided in the request"));
        jobMonoResponse = jobService.getJobFromCache(Integer.parseInt(jobId.get()));
        return ServerResponse.ok().body(jobMonoResponse, Job.class);
    }
}
