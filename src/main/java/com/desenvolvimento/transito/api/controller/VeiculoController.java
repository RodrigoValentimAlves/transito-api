package com.desenvolvimento.transito.api.controller;

import com.desenvolvimento.transito.api.assembler.VeiculoAssembler;
import com.desenvolvimento.transito.api.model.VeiculoModel;
import com.desenvolvimento.transito.domain.model.Veiculo;
import com.desenvolvimento.transito.domain.repository.VeiculoRepository;
import com.desenvolvimento.transito.domain.service.RegistroVeiculoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private final VeiculoRepository veiculoRepository;
    private final RegistroVeiculoService registroVeiculoService;
    private final VeiculoAssembler veiculoAssembler;

    @GetMapping
    public List<VeiculoModel> listar() {
        return veiculoAssembler.toCollectionModel(veiculoRepository.findAll());
    }

    @GetMapping("/{veiculoId}")
    public ResponseEntity<VeiculoModel> buscar(@PathVariable Long veiculoId) {
        return veiculoRepository.findById(veiculoId)
                .map(veiculoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VeiculoModel cadastrar(@Valid @RequestBody Veiculo veiculo) {
        return veiculoAssembler.toModel(registroVeiculoService.cadastrar(veiculo));
    }

}
