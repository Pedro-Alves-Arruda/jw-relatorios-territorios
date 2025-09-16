package com.jw.relatorios_territorios.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

public record EstudoBiblicoDTO(
        String nomeEstudante,
        String endereco,
        UUID idPublicador,
        String publicacao,
        String capitulo,
        LocalDateTime createdAt) {
}
