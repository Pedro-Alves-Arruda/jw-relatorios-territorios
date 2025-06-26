package com.jw.relatorios_territorios.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@RequiredArgsConstructor
public class Congregacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    @OneToMany(mappedBy = "congregacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Publicador> anciaos;
    @OneToMany(mappedBy = "congregacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Publicador> servosMinisteriais;
    @OneToMany(mappedBy = "congregacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Publicador> publicadores;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public List<Publicador> getAnciaos() {
        return anciaos;
    }

    public void setAnciaos(List<Publicador> anciaos) {
        this.anciaos = anciaos;
    }

    public List<Publicador> getServosMinisteriais() {
        return servosMinisteriais;
    }

    public void setServosMinisteriais(List<Publicador> servosMinisteriais) {
        this.servosMinisteriais = servosMinisteriais;
    }

    public List<Publicador> getPublicadores() {
        return publicadores;
    }

    public void setPublicadores(List<Publicador> publicadores) {
        this.publicadores = publicadores;
    }
}
