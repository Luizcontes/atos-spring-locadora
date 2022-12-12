package contes.atoslocadora;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import contes.atoslocadora.configuration.SpringDataConfiguration;
import contes.atoslocadora.models.Automovel;
import contes.atoslocadora.models.Cliente;
import contes.atoslocadora.models.Contrato;
import contes.atoslocadora.models.Price;
import contes.atoslocadora.models.PriceType;
import contes.atoslocadora.repositories.AutomovelRepository;
import contes.atoslocadora.repositories.ClienteRepository;
import contes.atoslocadora.repositories.ContratoRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringDataConfiguration.class})
class GenerateContrato {

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	ContratoRepository contratoRepository;

	@Autowired
	AutomovelRepository AutomovelRepository;

	@Test
	void generateCliente() {

		Cliente cliente = new Cliente(885544L, "Luiz", "aabbcc", "Rua das Gaivotas");
		Price price = new Price(PriceType.POPULAR, 40.00);
		Automovel automovel = new Automovel("AAA8877", "VW", "Gol", "1.8", 2022);
		Contrato contrato = new Contrato(885544L, "AAA8877", 45.90, 5);
		Contrato contrato2 = new Contrato(885544L, "AAA8877", 450.90, 3);
		Contrato contrato3 = new Contrato(885544L, "AAA8877", 18.70, 8);

		clienteRepository.save(cliente);
		AutomovelRepository.save(automovel);
		cliente.addContrato(contrato);
		cliente.addContrato(contrato2);
		cliente.addContrato(contrato3);
		automovel.addContrato(contrato);
		automovel.addContrato(contrato2);
		automovel.addContrato(contrato3);

		contratoRepository.save(contrato);
		contratoRepository.save(contrato2);
		contratoRepository.save(contrato3);
		
		Cliente cliente1 = clienteRepository.findClienteWithContratos(885544L);
		List<Contrato> contratos = cliente1.getContratos();
		contratos.forEach(c -> {
			System.out.println(c);
			if (c.getPeriodo() == 3) {
				cliente1.removeContrato(c);
			}
		});
		clienteRepository.save(cliente1);

		List<Contrato> contratos1 = contratoRepository.findAll();
		contratos1.forEach(c -> {
			if (c.getPreco() == 45.90) {
				contratoRepository.delete(c);
			}
		});

		List<Cliente> clientes = clienteRepository.findAll();

		Cliente clienteTmp = clienteRepository.findClienteWithContratos(885544L);

		Cliente clienteCpf = clienteRepository.findByCpf(885544L);
		Cliente clienteNome = clienteRepository.findByNome("Luiz");
		Cliente clienteCnh = clienteRepository.findByCnh("aabbcc");
		Cliente clienteEndereco = clienteRepository.findByEndereco("Rua das Gaivotas");		

		assertAll(
			() -> assertEquals(2, clientes.size()),
			() -> assertEquals(1, clienteTmp.getContratos().size()),
			() -> assertEquals(885544L, clienteCpf.getCpf()),
			() -> assertEquals("Luiz", clienteNome.getNome()),
			() -> assertEquals("aabbcc", clienteCnh.getCnh()),
			() -> assertEquals("Rua das Gaivotas", clienteEndereco.getEndereco()),
			() -> assertEquals(4, clienteEndereco.getId())
		);
	}

	@Test
	void addAutomovel() {

		Cliente cliente = new Cliente(223344L, "Juliana", "xxzzyy", "Rua dos Pardais");
		Automovel automovel = new Automovel("CCC1111", "Fiat", "Uno", "1.0", 2020);
		Contrato contrato = new Contrato(885544L, "AAA8877", 19.99, 15);
		
		clienteRepository.save(cliente);
		AutomovelRepository.save(automovel);
		
		cliente.addContrato(contrato);
		automovel.addContrato(contrato);
		contratoRepository.save(contrato);

		assertEquals("Juliana", cliente.getNome());
	}

	// @Autowired
    // private TestService testService;

    // @Test
    // void testStoreLoadEntities() {

    //     testService.storeLoadEntities();

    // }
}
