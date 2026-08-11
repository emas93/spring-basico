package com.emas.spring_basico.infrastructure.repository;

import com.emas.spring_basico.infrastructure.entity.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefoneRepository extends JpaRepository <Telefone, Long> {
}
