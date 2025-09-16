package com.jw.relatorios_territorios.Repository;

import com.jw.relatorios_territorios.Models.EstudoBiblico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface EstudoBiblicoRepository extends JpaRepository<EstudoBiblico, UUID> {

    @Query(value = "select * from estudo_biblico where id_publicador = :id", nativeQuery = true)
    public List<EstudoBiblico> findByIdPublicador(@Param("id") UUID id);
}
