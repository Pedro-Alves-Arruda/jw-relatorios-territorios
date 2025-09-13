package com.jw.relatorios_territorios.Models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String topic;
    @Column(columnDefinition = "TEXT")
    private String message;
    private UUID idPublicadorEmissor;
    private UUID idPublicadorRemetente;
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private boolean lida = false;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isLida() {
        return lida;
    }

    public void setLida(boolean lida) {
        this.lida = lida;
    }

    public UUID getIdPublicadorEmissor() {
        return idPublicadorEmissor;
    }

    public void setIdPublicadorEmissor(UUID idPublicadorEmissor) {
        this.idPublicadorEmissor = idPublicadorEmissor;
    }

    public UUID getIdPublicadorRemetente() {
        return idPublicadorRemetente;
    }

    public void setIdPublicadorRemetente(UUID idPublicadorRemetente) {
        this.idPublicadorRemetente = idPublicadorRemetente;
    }
}
