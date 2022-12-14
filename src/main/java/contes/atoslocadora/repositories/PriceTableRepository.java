package contes.atoslocadora.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import contes.atoslocadora.models.PriceTable;
import contes.atoslocadora.models.PriceType;

public interface PriceTableRepository extends JpaRepository<PriceTable, Long>{
 
    @Query("select pt from PriceTable pt where pt.priceType = :priceType")
    PriceTable findByPriceType(PriceType priceType);
}
