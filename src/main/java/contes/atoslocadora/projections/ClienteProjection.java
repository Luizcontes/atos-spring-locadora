package contes.atoslocadora.projections;

import java.util.List;

import contes.atoslocadora.models.Contrato;

public class ClienteProjection {

    public interface NCliente {

        Long getCpf();

        String getNome();

        String getCnh();

        String getEndereco();

        List<Contrato> getContratos();
    }
}
