package br.com.alura.loja.modelo;


import javax.persistence.*;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Pessoa pessoa;

    public Cliente() {
    }

    public Cliente(String nome, String cpf) {
        this.pessoa = new Pessoa(nome,cpf);

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public String getNome() {
        return this.pessoa.getNome();
    }
    public String getCpf() {
        return this.pessoa.getNome();
    }
}
