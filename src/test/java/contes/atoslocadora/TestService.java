package contes.atoslocadora;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import contes.atoslocadora.models.Automovel;
import contes.atoslocadora.repositories.AutomovelRepository;

@Service
public class TestService {
    
    @Autowired
    private AutomovelRepository automovelRepository;

    @Autowired

    @Transactional
    public void storeLoadEntities() {

        Automovel automovel = new Automovel("AAA1122", "VW", "Gol", "1.0", 2022);
        automovelRepository.save(automovel);

        Automovel automovel2 = new Automovel("BBB3344", "Fiat", "Uno", "1.0", 2021);
        automovelRepository.save(automovel2);


        // Price pricePopular = new Price(PriceType.POPULAR, 30.50);
        // priceRepository.save(pricePopular);

        // Price priceStandard = new Price(PriceType.STANDARD, 50.40);
        // priceRepository.save(priceStandard);

        // Price priceLuxury = new Price(PriceType.LUXURY, 150.00);
        // priceRepository.save(priceLuxury);

        // String priceType = "POPULAR";

        // Price price = priceRepository.findByPriceType(PriceType.valueOf(priceType));

        // price.addAutomovel(automovel);
        // automovel.setPrice(price);

        // price.addAutomovel(automovel2);
        // automovel2.setPrice(price);

        // priceRepository.save(price);

    }
}
