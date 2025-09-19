package com.jw.relatorios_territorios.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

public record DesignacaoDTO(
        String designacao,
        String email,
        LocalDateTime dia,
        LocalDateTime createdAt) {
}
