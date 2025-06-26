package com.jw.relatorios_territorios.Repository;

import com.jw.relatorios_territorios.Models.Publicador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicadorRepository extends JpaRepository<Publicador, Integer> {
}
