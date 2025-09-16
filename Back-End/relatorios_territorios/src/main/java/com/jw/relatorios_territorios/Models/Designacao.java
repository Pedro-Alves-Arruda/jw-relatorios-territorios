package com.jw.relatorios_territorios.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Designacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String designacao;
    private UUID idPublicador;
    private LocalDateTime dia;
    private LocalDateTime createdAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public UUID getIdPublicador() {
        return idPublicador;
    }

    public void setIdPublicador(UUID idPublicador) {
        this.idPublicador = idPublicador;
    }

    public LocalDateTime getDia() {
        return dia;
    }

    public void setDia(LocalDateTime dia) {
        this.dia = dia;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
