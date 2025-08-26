package com.jw.relatorios_territorios.DTO;

import com.jw.relatorios_territorios.Models.Congregacao;
import com.jw.relatorios_territorios.Models.GrupoCampo;
import jakarta.persistence.*;

import java.util.UUID;

public record PublicadorDTO(UUID id, String nome, String cpf, String email, String telefone, String funcao, UUID grupoCampo, UUID congregacao, String password) {
}
