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
    private String revisitas;
    private String estudos;
    private String grupoCampo;
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

    public String getRevisitas() {
        return revisitas;
    }

    public void setRevisitas(String revisitas) {
        this.revisitas = revisitas;
    }

    public String getEstudos() {
        return estudos;
    }

    public void setEstudos(String estudos) {
        this.estudos = estudos;
    }

    public String getGrupoCampo() {
        return grupoCampo;
    }

    public void setGrupoCampo(String grupoCampo) {
        this.grupoCampo = grupoCampo;
    }
}
