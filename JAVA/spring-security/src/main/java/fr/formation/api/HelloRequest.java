package fr.formation.api;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HelloRequest {
    // @JsonIgnore
    private String username;

    // @JsonProperty("test")
    private Integer age;
}
