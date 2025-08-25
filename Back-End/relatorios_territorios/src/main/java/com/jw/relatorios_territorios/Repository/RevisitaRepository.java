package com.jw.relatorios_territorios.Repository;

import com.jw.relatorios_territorios.Models.Revisita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevisitaRepository extends JpaRepository<Revisita, Long> {
}
