package com.jw.relatorios_territorios.DTO;

import java.util.UUID;

public record RevisitaDTO(
         UUID id,
         String rua,
         String bairro,
         String numero,
         String cidade,
         String estado,
         String cep,
         String descricao,
         String telefone,
         String nome,
         String email) {
}
