package com.jw.relatorios_territorios.Repository;

import com.jw.relatorios_territorios.Models.Publicacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PublicacaoRepository extends JpaRepository<Publicacao, UUID> {
}
