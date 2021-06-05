package com.bootcamp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobDto {

    @JsonProperty("job_id")
    private int id;
    @JsonProperty("job_name")
    private String name;
    @JsonProperty("java_exp")
    private double javaExp;
    @JsonProperty("spring_exp")
    private double springExp;
    private String status;

}
