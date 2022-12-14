package contes.atoslocadora.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import contes.atoslocadora.models.Automovel;
import contes.atoslocadora.models.Contrato;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {

    List<Contrato> findAll();

    @Query(value = "SELECT ct.* FROM contrato ct INNER JOIN cliente cl on cl.id = ct.cliente_id INNER JOIN automovel au on au.id = ct.automovel_id WHERE cliente_id = ?1", nativeQuery = true)
    List<Contrato> getByClienteCpf(Long id);

    // @Query(value = "SELECT ct.* FROM contrato ct INNER JOIN cliente cl on cl.id = ct.cliente_id INNER JOIN automovel au on au.id = ct.automovel_id WHERE automovel_id = ?1", nativeQuery = true)
    // List<Contrato> getByAutomovelPlaca(Long id);

    @Query("select ct from Contrato ct inner join fetch ct.cliente cl inner join fetch ct.automovel au where ct.automovel = :automovel")
    List<Contrato> getByAutomovelPlaca(Automovel automovel);

    @Query(value = "SELECT ct.* FROM contrato ct INNER JOIN cliente cl on cl.id = ct.cliente_id INNER JOIN automovel au on au.id = ct.automovel_id WHERE n_contrato = ?1", nativeQuery = true)
    Contrato findByNContrato(Long nContrato);

    // @Query("SELECT ct FROM contrato ct INNER JOIN ct.cliente cl and INNER JOIN ct.automovel au WHERE ct.n_contrato = :nContrato")
    // Contrato findByNContrato1(Long nContrato);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM contrato WHERE n_contrato = ?1", nativeQuery = true)
    void deleteByNContrato(Long contrato);
}
