package com.udacity.jdnd.course4.ecommerce.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartModifyRequest {

    @JsonProperty
    private String username;

    @JsonProperty
    private long itemId;

    @JsonProperty
    private int quantity;
}
