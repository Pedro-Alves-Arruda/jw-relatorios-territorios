package com.jw.relatorios_territorios.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificacaoDTO(
        String topic,
        String message,
        UUID idPublicador,
        LocalDateTime createdAt) {
}
