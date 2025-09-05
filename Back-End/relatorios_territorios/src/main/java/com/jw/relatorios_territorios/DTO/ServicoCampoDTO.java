package com.jw.relatorios_territorios.DTO;

import java.util.List;

public record ServicoCampoDTO (
        String tempo,
        List<String> publicacoesDeixadas,
        String descricao,
        String email
){ }
