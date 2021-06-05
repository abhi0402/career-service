package com.bootcamp.config;

import com.bootcamp.dto.JobDto2;
import com.bootcamp.model.Job;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfig {

    @Bean
    public HazelcastInstance instance() {
        Config config = new Config();
        config.setInstanceName("job-cache");
        return Hazelcast.newHazelcastInstance(config);
    }

    @Bean
    public IMap<Integer, JobDto2> cache(HazelcastInstance instance) {
        return instance.getMap("jobs");
    }

}
