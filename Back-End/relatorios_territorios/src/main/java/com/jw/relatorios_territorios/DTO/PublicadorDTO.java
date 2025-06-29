package com.jw.relatorios_territorios.DTO;

import com.jw.relatorios_territorios.Models.Congregacao;
import com.jw.relatorios_territorios.Models.GrupoCampo;
import jakarta.persistence.*;

public record PublicadorDTO(Integer id,String nome,String cpf, String email, String telefone,String funcao, Integer grupoCampo,Integer congregacao, String password) {
}
