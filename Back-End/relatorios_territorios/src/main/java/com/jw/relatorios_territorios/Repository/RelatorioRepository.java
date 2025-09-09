package com.jw.relatorios_territorios.Repository;

import com.jw.relatorios_territorios.Models.Relatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RelatorioRepository extends JpaRepository<Relatorio, UUID> {

    @Query(value = "select distinct pb.nome, grc.nome, SUM(sc.tempo::interval) as tempo_trabalhado, \n" +
            "grc.id_responsavel, grc.id_ajudante,\n" +
            "(select pb.email from publicador as pb where pb.id = grc.id_responsavel) as email_responsavel\n" +
            "from publicador as pb \n" +
            "join servico_campo as sc on pb.id = sc.publicador\n" +
            "join grupo_campo as grc on pb.grupo_campo_id = grc.id\n" +
            "where sc.created_at between date_trunc('month', current_date) ::timestamp\n" +
            "and (date_trunc('month', current_date)+ interval '1 month - 1 day') ::timestamp\n" +
            "group by pb.nome, grc.nome, grc.id_responsavel, grc.id_ajudante;", nativeQuery = true)
    public List<Object[]> findByRelatoriosForSend();

}
