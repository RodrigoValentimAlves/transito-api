package com.desenvolvimento.transito.domain.service;

import com.desenvolvimento.transito.domain.exception.NegocioException;
import com.desenvolvimento.transito.domain.model.Proprietario;
import com.desenvolvimento.transito.domain.repository.ProprietarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class RegistroProprietarioService {

    private final ProprietarioRepository proprietarioRepository;

    public Proprietario buscar(Long proprietarioID) {
        return proprietarioRepository.findById(proprietarioID)
                .orElseThrow(() -> new NegocioException("Proprietario não encontrado"));
    }

    @Transactional
    public Proprietario salvar(Proprietario proprietario) {
        boolean emailEmUso = proprietarioRepository.findByEmail(proprietario.getEmail())
                .filter(p -> !p.equals(proprietario))
                .isPresent();
        if(emailEmUso) {
            throw new NegocioException("Já existe um proprietario cadastrado para esse email");
        }
        return proprietarioRepository.save(proprietario);
    }

    public void excluir(Long proprietarioId) {
        proprietarioRepository.deleteById(proprietarioId);
    }

}
