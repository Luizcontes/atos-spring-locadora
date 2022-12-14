package contes.atoslocadora;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import contes.atoslocadora.models.Automovel;
import contes.atoslocadora.models.Cliente;
import contes.atoslocadora.models.PriceTable;
import contes.atoslocadora.models.PriceType;
import contes.atoslocadora.models.Priceused;
import contes.atoslocadora.repositories.AutomovelRepository;
import contes.atoslocadora.repositories.ClienteRepository;
import contes.atoslocadora.repositories.PriceTableRepository;
import contes.atoslocadora.repositories.PriceusedRepository;

@SpringBootApplication
public class AtoslocadoraApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtoslocadoraApplication.class, args);
	}

	@Bean
	public ApplicationRunner configure(PriceusedRepository priceusedRepository, PriceTableRepository priceTableRepository,
			AutomovelRepository automovelRepository, ClienteRepository clienteRepository) {

		return env -> {

			// CREATE SAMPLE CLIENTES
			Cliente cliente = new Cliente(1L, "Carlos", "HHFFJJ", "Rua das Margaridas");
			clienteRepository.save(cliente);

			PriceTable pricePopular = new PriceTable(PriceType.POPULAR, 30.50);
			priceTableRepository.save(pricePopular);
			PriceTable priceStandard = new PriceTable(PriceType.STANDARD, 50.40);
			priceTableRepository.save(priceStandard);
			PriceTable priceLuxury = new PriceTable(PriceType.LUXURY, 150.00);
			priceTableRepository.save(priceLuxury);

			Priceused priceUsed = new Priceused(pricePopular.getPriceType(), pricePopular.getPrice());

			Automovel automovel = new Automovel("AAA1122", "VW", "Gol", "1.0", 2022);
			automovel.setPriceused(priceUsed);
			priceusedRepository.save(priceUsed);

			Automovel automovel2 = new Automovel("BBB3344", "Fiat", "Uno", "1.0", 2021);
			automovel2.setPriceused(priceUsed);
			priceusedRepository.save(priceUsed);

			priceUsed.addAutomovel(automovel);
			priceUsed.addAutomovel(automovel2);

			automovelRepository.save(automovel);
			automovelRepository.save(automovel2);
		};
	}
}
