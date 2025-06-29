package com.jw.relatorios_territorios.Repository;

import com.jw.relatorios_territorios.Models.Publicador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublicadorRepository extends JpaRepository<Publicador, Integer> {

    public Optional<Publicador> findByEmail(@Param("email") String email);

}
