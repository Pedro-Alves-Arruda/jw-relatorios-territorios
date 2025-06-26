package com.jw.relatorios_territorios.Models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@RequiredArgsConstructor
public class Publicador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    @Column(unique = true)
    private String cpf;
    private String email;
    private String telefone;

    @ElementCollection
    private String[] roles;

    @ManyToOne
    @JoinColumn(name = "grupo_campo_id", nullable = false)
    private GrupoCampo grupoCampo;

    @ManyToOne
    @JoinColumn(name = "congregacao_id", nullable = false)
    private Congregacao congregacao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
}
