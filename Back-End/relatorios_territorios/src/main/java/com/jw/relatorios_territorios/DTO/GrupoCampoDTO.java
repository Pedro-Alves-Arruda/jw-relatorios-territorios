package com.jw.relatorios_territorios.DTO;

import jakarta.persistence.Column;

import java.util.UUID;

public record GrupoCampoDTO(
            UUID id,
            UUID idResponsavel,
            UUID idAjudante,
            String nome,
            String endereco) {
}
