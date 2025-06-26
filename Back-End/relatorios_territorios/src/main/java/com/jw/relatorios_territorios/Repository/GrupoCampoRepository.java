package com.jw.relatorios_territorios.Repository;

import com.jw.relatorios_territorios.Models.GrupoCampo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GrupoCampoRepository extends JpaRepository<GrupoCampo, Integer> {

    public Optional<GrupoCampo> findById(@Param("id") Integer id);
}
