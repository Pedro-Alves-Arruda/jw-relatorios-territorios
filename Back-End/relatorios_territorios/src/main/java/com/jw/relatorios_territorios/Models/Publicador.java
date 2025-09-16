package com.jw.relatorios_territorios.Models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import java.util.List;
import java.util.UUID;

@Entity
@RequiredArgsConstructor
public class Publicador {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;
    @Column(unique = true)
    private String cpf;
    private String email;
    private String telefone;
    private String password;
    private String endereco;
    @Column(nullable = true)
    private boolean isPioneiro = false;
    @Column(nullable = true)
    private boolean isAnciao = false;


    @ElementCollection
    private String[] roles;

    @ManyToOne
    @JoinColumn(name = "grupo_campo_id", nullable = false)
    private GrupoCampo grupoCampo;

    @ManyToOne
    @JoinColumn(name = "congregacao_id", nullable = false)
    private Congregacao congregacao;

    @OneToMany(mappedBy = "publicador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicoCampo> servicoCampo;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public GrupoCampo getGrupoCampo() {
        return grupoCampo;
    }

    public void setGrupoCampo(GrupoCampo grupoCampo) {
        this.grupoCampo = grupoCampo;
    }

    public Congregacao getCongregacao() {
        return congregacao;
    }

    public void setCongregacao(Congregacao congregacao) {
        this.congregacao = congregacao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public List<ServicoCampo> getServicoCampo() {
        return servicoCampo;
    }

    public void setServicoCampo(List<ServicoCampo> servicoCampo) {
        this.servicoCampo = servicoCampo;
    }

    public boolean isPioneiro() {
        return isPioneiro;
    }

    public void setPioneiro(boolean pioneiro) {
        isPioneiro = pioneiro;
    }

    public boolean isAnciao() {
        return isAnciao;
    }

    public void setAnciao(boolean anciao) {
        isAnciao = anciao;
    }
}


