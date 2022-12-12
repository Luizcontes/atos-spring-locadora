package contes.atoslocadora.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "cpf", nullable = false)
    private Long cpf;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cnh", nullable = false)
    private String cnh;

    @Column(name = "endereco", nullable = false)
    private String endereco;

    @Column(nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean ativo = true;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JsonIgnore
    // @org.hibernate.annotations.Fetch(
    //     org.hibernate.annotations.FetchMode.SUBSELECT
    // )
    private List<Contrato> contratos = new ArrayList<>();

    public Cliente() {
    }

    public Cliente(Long cpf, String nome, String cnh, String endereco) {
        this.cpf = cpf;
        this.nome = nome;
        this.cnh = cnh;
        this.endereco = endereco;
        this.ativo = true;
    }

    public Long getId() {
        return id;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void addContrato(Contrato contrato) {
        contratos.add(contrato);
    }

    public void removeContrato(Contrato contrato) {
        contratos.remove(contrato);
    }

    @Override
    public String toString() {

        String cliente = "\nNome: " + nome +
                "\nEndereco: " + endereco +
                "\nCPF: " + cpf +
                "\nCNH: " + cnh;

        return cliente;
    }

}
