package com.jw.relatorios_territorios.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import java.util.List;
import java.util.UUID;

@Entity
@RequiredArgsConstructor
public class GrupoCampo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Integer idResponsavel;
    private Integer idAjudante;
    private String nome;
    private String endereco;

    @OneToMany(mappedBy = "grupoCampo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Publicador> publicadores;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(Integer idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    public Integer getIdAjudante() {
        return idAjudante;
    }

    public void setIdAjudante(Integer idAjudante) {
        this.idAjudante = idAjudante;
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
}
