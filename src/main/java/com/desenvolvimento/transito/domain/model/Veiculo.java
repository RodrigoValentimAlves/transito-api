package com.desenvolvimento.transito.domain.model;

import com.desenvolvimento.transito.domain.validation.ValidationGroups;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Valid
    @ConvertGroup(from = Default.class, to = ValidationGroups.ProprietarioId.class)
    @NotNull
    @ManyToOne
    private Proprietario proprietario;

    @NotBlank
    private String marca;

    @NotBlank
    private String modelo;

    @Pattern(regexp = "[A-Z]{3}[0-9][0-9 A-Z][0-9]{2}")
    @NotBlank
    private String placa;

    @JsonProperty(access = READ_ONLY)
    @Enumerated(EnumType.STRING)
    private StatusVeiculo status;

    @JsonProperty(access = READ_ONLY)
    private OffsetDateTime dataCadastro;

    @JsonProperty(access = READ_ONLY)
    private OffsetDateTime dataApreensao;

}
