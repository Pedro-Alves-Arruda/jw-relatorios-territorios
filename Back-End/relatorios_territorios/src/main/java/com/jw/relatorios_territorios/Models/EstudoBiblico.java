package com.jw.relatorios_territorios.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class EstudoBiblico {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nomeEstudante;
    private String endereco;
    private UUID idPublicador;
    private String publicacao;
    private String capitulo;
    private LocalDateTime createdAt;
}
