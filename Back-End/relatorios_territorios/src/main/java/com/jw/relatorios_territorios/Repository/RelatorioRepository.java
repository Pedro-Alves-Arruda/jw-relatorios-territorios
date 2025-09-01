package com.jw.relatorios_territorios.Repository;

import com.jw.relatorios_territorios.Models.Relatorio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RelatorioRepository extends JpaRepository<Relatorio, UUID> {
}
