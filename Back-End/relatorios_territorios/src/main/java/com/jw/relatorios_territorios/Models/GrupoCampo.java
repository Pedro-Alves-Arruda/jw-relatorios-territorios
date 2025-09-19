package com.jw.relatorios_territorios.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.UUID;

@Entity
@RequiredArgsConstructor
public class GrupoCampo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "id_responsavel")
    private UUID idResponsavel;
    @Column(name = "id_ajudante")
    private UUID idAjudante;
    private String nome;
    private String endereco;
    private UUID idCongregacao;

    @OneToMany(mappedBy = "grupoCampo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Publicador> publicadores;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Publicador> getPublicadores() {
        return publicadores;
    }

    public void setPublicadores(List<Publicador> publicadores) {
        this.publicadores = publicadores;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public UUID getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(UUID idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    public UUID getIdAjudante() {
        return idAjudante;
    }

    public void setIdAjudante(UUID idAjudante) {
        this.idAjudante = idAjudante;
    }

    public UUID getIdCongregacao() {
        return idCongregacao;
    }

    public void setIdCongregacao(UUID idCongregacao) {
        this.idCongregacao = idCongregacao;
    }
}
