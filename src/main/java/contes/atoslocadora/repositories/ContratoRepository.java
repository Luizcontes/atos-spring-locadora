package contes.atoslocadora.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import contes.atoslocadora.models.Contrato;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {


    @Query(value = "SELECT ct.* FROM contrato ct INNER JOIN cliente cl on cl.id = ct.cliente_id INNER JOIN automovel au on au.id = ct.automovel_id WHERE cliente_id = ?1", nativeQuery = true)
    List<Contrato> getByClienteCpf(Long id);

    @Query(value = "SELECT ct.* FROM contrato ct INNER JOIN cliente cl on cl.id = ct.cliente_id INNER JOIN automovel au on au.id = ct.automovel_id WHERE automovel_id = ?1", nativeQuery = true)
    List<Contrato> getByAutomovelPlaca(Long id);

    @Query(value = "SELECT ct.* FROM contrato ct INNER JOIN cliente cl on cl.id = ct.cliente_id INNER JOIN automovel au on au.id = ct.automovel_id WHERE n_contrato = ?1", nativeQuery = true)
    Contrato findByNContrato(Long nContrato);
    
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM contrato WHERE n_contrato = ?1", nativeQuery = true)
    void deleteByNContrato(Long contrato);
}
