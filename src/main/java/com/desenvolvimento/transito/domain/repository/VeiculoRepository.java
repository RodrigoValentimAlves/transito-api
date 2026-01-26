package com.desenvolvimento.transito.domain.repository;

import com.desenvolvimento.transito.domain.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {


    Optional<Veiculo> findByPlaca(String placa);
}
