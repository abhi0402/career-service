package com.bootcamp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;

@PrimaryKeyClass
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobPrimaryKey {

    @PrimaryKeyColumn(name = "job_id", type = PrimaryKeyType.PARTITIONED)
    private int id;

}
