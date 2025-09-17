package com.jw.relatorios_territorios.Repository;

import com.jw.relatorios_territorios.Models.Publicador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PublicadorRepository extends JpaRepository<Publicador, UUID> {

    public Optional<Publicador> findByEmail(@Param("email") String email);

    @Query(value = "select p.*, cg.nome as nomeCongregacao, cg.rua, cg.bairro, cg.cidade from publicador as p join congregacao as cg on p.congregacao_id = cg.id where p.id = :id", nativeQuery = true)
    public Optional<Publicador> findById(@Param("id") UUID id);

    @Query(value = "select count(*) as numero_revisitas,\n" +
            "(select count(*) from servico_campo where publicador = :id and created_at between date_trunc('month', current_date) ::timestamp and (date_trunc('month', current_date)+ interval '1 month - 1 day') ::timestamp) as numero_servico_campo, \n" +
            "(select count(*) from estudo_biblico where created_at between date_trunc('month', current_date) ::timestamp and (date_trunc('month', current_date)+ interval '1 month - 1 day') ::timestamp and id_publicador = :id) as numero_estudo \n" +
            "from revisita as rv\n" +
            "where created_at between date_trunc('month', current_date) ::timestamp\n" +
            "and (date_trunc('month', current_date)+ interval '1 month - 1 day') ::timestamp\n" +
            "and rv.id_publicador = :id", nativeQuery = true)
    public List<Object[]> buscarDadosGrafico(@Param("id") UUID id);

    @Query(value = "SELECT \n" +
            "CASE EXTRACT(MONTH FROM created_at)\n" +
            "  WHEN 1 THEN 'Janeiro'\n" +
            "  WHEN 2 THEN 'Fevereiro'\n" +
            "  WHEN 3 THEN 'Março'\n" +
            "  WHEN 4 THEN 'Abril'\n" +
            "  WHEN 5 THEN 'Maio'\n" +
            "  WHEN 6 THEN 'Junho'\n" +
            "  WHEN 7 THEN 'Julho'\n" +
            "  WHEN 8 THEN 'Agosto'\n" +
            "  WHEN 9 THEN 'Setembro'\n" +
            "  WHEN 10 THEN 'Outubro'\n" +
            "  WHEN 11 THEN 'Novembro'\n" +
            "  WHEN 12 THEN 'Dezembro'\n" +
            "END AS mes, \n" +
            "COUNT(*) AS quantidade\n" +
            "FROM revisita\n" +
            "WHERE id_publicador = :id \n" +
            "AND created_at >= date_trunc('month', CURRENT_DATE) - interval '3 months' \n"+
            "GROUP BY TO_CHAR(created_at, 'TMMonth'), EXTRACT(MONTH FROM created_at)\n" +
            "ORDER BY EXTRACT(MONTH FROM created_at)", nativeQuery = true)
    public List<Object[]> buscarDadosGraficoLinha(@Param("id") UUID id);


    @Query(value = "select distinct SUM(sc.tempo::interval) as tempo_trabalhado_ano,\n" +
            "(select distinct SUM(tempo::interval) from servico_campo where created_at between date_trunc('month', current_date) ::timestamp\n" +
            "and (date_trunc('month', current_date)+ interval '1 month - 1 day') ::timestamp and publicador = :id) as tempo_trabalhado_mes\n" +
            "from servico_campo as sc \n" +
            "where publicador = :id", nativeQuery = true)
    public List<Object[]> getTotalHoras(@Param("id") UUID id);


}
