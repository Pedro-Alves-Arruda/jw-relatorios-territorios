package com.jw.relatorios_territorios.Repository;

import com.jw.relatorios_territorios.Models.Revisita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RevisitaRepository extends JpaRepository<Revisita, UUID> {


    @Query(value = "select * from revisita where id = :id", nativeQuery = true)
    List<Revisita> findAll(@Param("id") UUID id);

    @Query(value = "select rv.* from revisita as rv join Publicador as pb on rv.id_publicador = pb.id where rv.id_publicador = :id", nativeQuery = true)
    List<Revisita> findAllById(@Param("id") UUID id);
}
