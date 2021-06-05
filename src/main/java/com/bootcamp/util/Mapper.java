package com.bootcamp.util;

import com.bootcamp.dto.JobDto;
import com.bootcamp.dto.JobDto2;
import com.bootcamp.model.Job;
import com.bootcamp.model.JobPrimaryKey;

public class Mapper {

    public static Job mapDtoToJobEntity(JobDto jobDto) {
        Job job = new Job();
        job.setKey(new JobPrimaryKey(jobDto.getId()));
        job.setName(jobDto.getName());
        job.setJavaExp(jobDto.getJavaExp());
        job.setSpringExp(jobDto.getSpringExp());
        return job;
    }

    public static JobDto2 EntityToDto(Job job) {
        JobDto2 jobDto = new JobDto2();
        jobDto.setId(job.getKey().getId());
        jobDto.setName(job.getName());
        jobDto.setJavaExp(job.getJavaExp());
        jobDto.setSpringExp(job.getSpringExp());
        return jobDto;
    }

}
