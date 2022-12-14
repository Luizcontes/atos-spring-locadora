package contes.atoslocadora.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "contrato")
public class Contrato {

    @Id
    @GeneratedValue()
    private Long id_contrato;

    @CreationTimestamp
    private LocalDate data;

    private Long nContrato = generateContratoNumber();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", nullable = false)

    private Cliente cliente;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "automovel_id", nullable = true)

    private Automovel automovel;

    private double preco;

    private int periodo;

    public Contrato() {
    }

    public Long getId() {
        return id_contrato;
    }

    public LocalDate getData() {
        return data;
    }

    public Long getnContrato() {
        return nContrato;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Automovel getAutomovel() {
        return automovel;
    }

    public void setAutomovel(Automovel automovel) {
        this.automovel = automovel;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    // public Long getCpf() {
    //     return cpf;
    // }

    // public String getPlaca() {
    //     return placa;
    // }

    // public void setPlaca(String placa) {
    //     this.placa = placa;
    // }

    public Long generateContratoNumber() {
        String second = String.valueOf(LocalDateTime.now().toLocalTime().toSecondOfDay());
        String year = String.valueOf(LocalDate.now().getYear());
        String day = String.valueOf(LocalDate.now().getDayOfYear());

        return Long.parseLong(year + day + second);
    }

    @Override
    public String toString() {
        String contrato = "\nNome: " + cliente.getNome() +
                "\nAutomovel: " + automovel.getMarca() +
                "\nCliente: " + cliente.getNome() +
                "\nNumero do Contrato: " + nContrato +
                "\nData: " + data +
                "\nPeriodo: " + periodo +
                "\nPreco total: " + (periodo * preco) +
                "\nData da devolucao: " + (data.plusDays(periodo));

        return contrato;
    }
}
