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
            "TO_CHAR(created_at, 'TMMonth') AS mes, \n" +
            "COUNT(*) AS quantidade\n" +
            "FROM revisita\n" +
            "WHERE id_publicador = :id \n" +
            "AND created_at >= date_trunc('month', CURRENT_DATE) - interval '3 months' \n"+
            "GROUP BY TO_CHAR(created_at, 'TMMonth'), EXTRACT(MONTH FROM created_at)\n" +
            "ORDER BY EXTRACT(MONTH FROM created_at)", nativeQuery = true)
    public List<Object[]> buscarDadosGraficoLinha(@Param("id") UUID id);



}
