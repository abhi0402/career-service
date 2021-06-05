package com.bootcamp.service;

import com.bootcamp.dto.EmployeeDto;
import com.bootcamp.dto.JobDto;
import com.bootcamp.dto.JobDto2;
import com.bootcamp.model.Job;
import com.bootcamp.repository.JobRepository;
import com.bootcamp.util.Mapper;
import com.hazelcast.map.IMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private WebClient webClient;

    @Autowired
    private IMap<Integer, JobDto2> cache;

    public Mono<JobDto> saveJob(Mono<JobDto> jobDtoMono) {
        return jobDtoMono.flatMap(jobDto -> jobRepository.findOneByKeyId(jobDto.getId())
        .doOnNext(job -> {
            if(job != null)
                jobDto.setStatus("Already Exists");
        })
        .switchIfEmpty(Mono.defer(() -> {
            return jobRepository.save(Mapper.mapDtoToJobEntity(jobDto))
                    .map(savedJob -> {
                        jobDto.setStatus("Created");
                        return savedJob;
                    });
        }))
        .map(job -> jobDto));
    }

    public Flux<EmployeeDto> findEmpsByJobId(int id) {
        return jobRepository.findOneByKeyId(id)
        .flatMapMany(job -> getEmpsByExp(job.getJavaExp(), job.getSpringExp()));
    }

    public Flux<EmployeeDto> getEmpsByExp(double javaExp, double springExp) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/findEmpSkillset/")
                        .queryParam("java_exp", javaExp)
                        .queryParam("spring_exp", springExp)
                        .build())
                .retrieve()
                .bodyToFlux(EmployeeDto.class);
    }

    public Mono<JobDto2> getJobFromCache(int jobId) {
        return Mono.fromCompletionStage(() -> cache.getAsync(Integer.valueOf(jobId)))
                .doOnNext(job -> log.info("Job with id " + job.getId() + " found in cache"))
                .switchIfEmpty(jobRepository.findOneByKeyId(jobId)
                        .map(job -> Mapper.EntityToDto(job))
                        .doOnNext(job -> {
                            cache.putAsync(Integer.valueOf(job.getId()), job);
                            log.info("Job with id " + job.getId() + " set in cache");
                        })
                );
    }
}
