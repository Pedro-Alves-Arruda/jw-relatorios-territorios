package com.jw.relatorios_territorios.Repository;


import com.jw.relatorios_territorios.Models.ServicoCampo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CampoRepository extends JpaRepository<ServicoCampo, UUID> {
}
