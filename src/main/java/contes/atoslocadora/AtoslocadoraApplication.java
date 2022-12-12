package contes.atoslocadora;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import contes.atoslocadora.models.Price;
import contes.atoslocadora.models.PriceType;
// import contes.atoslocadora.repositories.PriceRepository;

@SpringBootApplication
public class AtoslocadoraApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtoslocadoraApplication.class, args);
	}

	// @Bean
	// public ApplicationRunner configure(PriceRepository priceRepository) {

	// 	return env -> {
	// 		Price pricePopular = new Price(PriceType.POPULAR, 30.50);
	// 		priceRepository.save(pricePopular);

	// 		Price priceStandard = new Price(PriceType.STANDARD, 50.40);
	// 		priceRepository.save(priceStandard);

	// 		Price priceLuxury = new Price(PriceType.LUXURY, 150.00);
	// 		priceRepository.save(priceLuxury);

	// 		// priceRepository.findAll().forEach(System.out::println);
	// 	};

	// }
}
