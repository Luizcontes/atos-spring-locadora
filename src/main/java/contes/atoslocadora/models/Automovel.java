package contes.atoslocadora.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "automovel")
public class Automovel {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "placa", nullable = false)
    private String placa;

    @Column(name = "marca", nullable = false)
    private String marca;

    @Column(name = "modelo", nullable = false)
    private String modelo;

    @Column(name = "versao", nullable = false)
    private String versao;

    @Column(name = "ano", nullable = false)
    private int ano;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "priceused", nullable = true)
    private Priceused priceused;

    @Column(nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean ativo = true;
    
    @OneToMany(mappedBy = "automovel", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JsonIgnore
    private List<Contrato> contratos = new ArrayList<>();

    public Automovel() {
    }
 
    public Automovel(String placa, String marca, String modelo, String versao, int ano) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.versao = versao;
        this.ano = ano;
        this.ativo = true;
    }

    public Long getId() {
        return id;
    }

    public String getPlaca() {
        return placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
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

    public Priceused getPriceused() {
        return priceused;
    }

    public void setPriceused(Priceused priceUsed) {
        this.priceused = priceUsed;
    }

    public void removePriceused() {
        this.priceused = null;
    }

    @Override
    public String toString() {
        String automovel =  "\nMARCA: " + marca +
            "\nMODELO: " + modelo +
            "\nVERSAO: " + versao +
            "\nPLACA: " + placa +
            "\nANO: " + ano;
            
            return automovel;
    }
    
}
