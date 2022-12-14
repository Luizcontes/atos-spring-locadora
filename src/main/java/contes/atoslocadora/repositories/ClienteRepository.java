package contes.atoslocadora.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import contes.atoslocadora.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByCpf(Long cpf);
    Cliente findByNome(String nome);
    Cliente findByCnh(String cnh);
    Cliente findByEndereco(String endereco);
    List<Cliente> findByNomeContaining(String text);

    List<Cliente> findByAtivo(boolean ativo);

    @Query("select c from Cliente c inner join fetch c.contratos where c.cpf = :cpf")
    Cliente findClienteWithContratos(@Param("cpf") Long cpf);
}
