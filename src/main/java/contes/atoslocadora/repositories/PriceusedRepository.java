package contes.atoslocadora.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import contes.atoslocadora.models.PriceType;
import contes.atoslocadora.models.Priceused;

public interface PriceusedRepository extends JpaRepository<Priceused, Long>{
    
    List<Priceused> findByPriceType(PriceType priceType);

    @Query("select p from Priceused p inner join fetch p.automoveis where p.priceType = :priceType")
    Priceused getPriceByPriceType(PriceType priceType);

    @Modifying
    @Transactional
    // @Query("delete p from Priceused p where p.priceused = :priceused")
    void deleteById(Long id);
}
