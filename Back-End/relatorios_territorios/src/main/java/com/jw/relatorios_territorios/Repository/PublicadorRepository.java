package com.jw.relatorios_territorios.Repository;

import com.jw.relatorios_territorios.Models.Publicador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PublicadorRepository extends JpaRepository<Publicador, UUID> {

    public Optional<Publicador> findByEmail(@Param("email") String email);

    @Query(value = "select p.*, cg.nome as nomeCongregacao, cg.rua, cg.bairro, cg.cidade from publicador as p join congregacao as cg on p.congregacao_id = cg.id where p.id = :id", nativeQuery = true)
    public Optional<Publicador> findById(@Param("id") UUID id);
}
