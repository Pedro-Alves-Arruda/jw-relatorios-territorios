package com.jw.relatorios_territorios.Models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Entity
public class ServicoCampo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String tempo;
    private List<String> publicacoesDeixadas;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "publicador", nullable = false)
    private Publicador publicador;

    private LocalDateTime created_at;

    public List<String> getPublicacoesDeixadas() {
        return publicacoesDeixadas;
    }

    public void setPublicacoesDeixadas(List<String> publicacoesDeixadas) {
        this.publicacoesDeixadas = publicacoesDeixadas;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public List<String> getLisPublicacoesDeixadas() {
        return publicacoesDeixadas;
    }

    public void setLisPublicacoesDeixadas(List<String> lisPublicacoesDeixadas) {
        this.publicacoesDeixadas = lisPublicacoesDeixadas;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

//    public UUID getPublicador() {
//        return publicador_id;
//    }
//
//    public void setPublicador(UUID publicador_id) {
//        this.publicador_id = publicador_id;
//    }


    public Publicador getPublicadorId() {
        return publicador;
    }

    public void setPublicadorId(Publicador publicadorId) {
        this.publicador = publicadorId;
    }
}
