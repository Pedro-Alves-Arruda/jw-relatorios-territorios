package com.jw.relatorios_territorios.Repository;


import com.jw.relatorios_territorios.Models.ServicoCampo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CampoRepository extends JpaRepository<ServicoCampo, UUID> {

    @Query(value = "select publicacoes_deixadas\n" +
            "from servico_campo as sc\n" +
            "join publicador as pb on pb.id = sc.publicador\n" +
            "where publicador = :id \n" +
            "and sc.created_at between date_trunc('month', current_date) ::timestamp\n" +
            "and (date_trunc('month', current_date)+ interval '1 month - 1 day') ::timestamp", nativeQuery = true)
    public List<String> buscarPublicacoesDeixadas(@Param("id") UUID id);

}
