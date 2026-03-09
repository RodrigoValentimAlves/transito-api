package com.desenvolvimento.transito.api.model.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropritarioIdInput {

    @NotNull
    private Long id;

}
