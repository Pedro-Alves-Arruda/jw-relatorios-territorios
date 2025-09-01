package com.jw.relatorios_territorios.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Entity
@RequiredArgsConstructor
public class Relatorio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String horas;
    private String nome;
    private String publicacoes;
    private Integer revisitas;
    private Integer estudos;
    private UUID grupoCampo;
    private UUID idPublicador;

    public UUID getIdPublicador() {
        return idPublicador;
    }

    public void setIdPublicador(UUID idPublicador) {
        this.idPublicador = idPublicador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPublicacoes() {
        return publicacoes;
    }

    public void setPublicacoes(String publicacoes) {
        this.publicacoes = publicacoes;
    }

    public Integer getRevisitas() {
        return revisitas;
    }

    public void setRevisitas(Integer revisitas) {
        this.revisitas = revisitas;
    }

    public Integer getEstudos() {
        return estudos;
    }

    public void setEstudos(Integer estudos) {
        this.estudos = estudos;
    }

    public UUID getGrupoCampo() {
        return grupoCampo;
    }

    public void setGrupoCampo(UUID grupoCampo) {
        this.grupoCampo = grupoCampo;
    }
}
