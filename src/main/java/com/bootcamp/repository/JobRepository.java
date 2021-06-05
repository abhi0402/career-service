package com.bootcamp.repository;

import com.bootcamp.model.Job;
import com.bootcamp.model.JobPrimaryKey;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface JobRepository extends ReactiveCassandraRepository<Job, JobPrimaryKey> {
    Mono<Job> findOneByKeyId(int i);
}
