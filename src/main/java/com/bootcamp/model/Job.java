package com.bootcamp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;

@Table("job")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job {

    @PrimaryKey
    private JobPrimaryKey key;
    @Column("job_name")
    private String name;
    @Column("java_exp")
    private double javaExp;
    @Column("spring_exp")
    private double springExp;

}
