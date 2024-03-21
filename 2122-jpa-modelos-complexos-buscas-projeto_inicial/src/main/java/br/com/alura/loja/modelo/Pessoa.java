package br.com.alura.loja.modelo;

import javax.persistence.Embeddable;

@Embeddable
public class Pessoa {


    private String nome;
    private String cpf;

    public Pessoa() {}

    public Pessoa(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }
}
