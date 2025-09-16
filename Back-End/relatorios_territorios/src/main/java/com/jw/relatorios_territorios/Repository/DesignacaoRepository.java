package com.jw.relatorios_territorios.Repository;

import com.jw.relatorios_territorios.Models.Designacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DesignacaoRepository extends JpaRepository<Designacao, UUID> {

    @Query(value = "select * from designacao where id_publicador = :id", nativeQuery = true)
    public List<Designacao> findAllByIdPublicador(@Param("id") UUID id);

}
