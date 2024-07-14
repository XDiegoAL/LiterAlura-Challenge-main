package com.literalura.challenge.modelosurl;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResultsResponse(
        @JsonAlias("results")List<ResultsUrl> results
        ) {}
