package contes.atoslocadora.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.node.ObjectNode;

import contes.atoslocadora.repositories.AutomovelRepository;
import contes.atoslocadora.repositories.ClienteRepository;
import contes.atoslocadora.repositories.ContratoRepository;
import contes.atoslocadora.repositories.PriceTableRepository;
import contes.atoslocadora.repositories.PriceusedRepository;
import contes.atoslocadora.exception.RecordInexistenteException;
import contes.atoslocadora.models.Automovel;
import contes.atoslocadora.models.Cliente;
import contes.atoslocadora.models.Contrato;
import contes.atoslocadora.models.PriceTable;
import contes.atoslocadora.models.Priceused;

@Component
public class ContratoService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    AutomovelRepository automovelRepository;

    @Autowired
    ContratoRepository contratoRepository;

    @Autowired
    PriceusedRepository priceusedRepository;

    @Autowired
    PriceTableRepository priceTableRepository;

    public ResponseEntity<?> findAll() {

        return ResponseEntity.status(HttpStatus.OK).body(contratoRepository.findAll());
    }

    public ResponseEntity<?> getAllContratosFromCliente(Long cpf) {

        Cliente cliente = clienteRepository.findByCpf(cpf);

        try {

            if (cliente == null) {
                throw new RecordInexistenteException("Cliente nao cadastrado na base de dados.");
            }

            List<Contrato> contratos = contratoRepository.getByClienteCpf(cliente.getId());

            return ResponseEntity.status(HttpStatus.OK).body(contratos);

        } catch (RecordInexistenteException e) {

            Map<String, Object> jsonContent = new HashMap<String, Object>();
            jsonContent.put("timestamp", Timestamp.valueOf(LocalDateTime.now()));
            jsonContent.put("status", "404");
            jsonContent.put("error", "Not Found");
            jsonContent.put("message", e.getMessage());
            jsonContent.put("path", "/contrato/cpf/" + cpf);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonContent);
        }
    }

    public ResponseEntity<?> getAllContratosFromAutomovel(String placa) {

        Automovel automovel = automovelRepository.findByPlaca(placa);

        try {

            if (automovel == null) {
                throw new RecordInexistenteException("Automovel nao cadastrado na base de dados.");
            }

            List<Contrato> contratos = contratoRepository.getByAutomovelPlaca(automovel);

            return ResponseEntity.status(HttpStatus.OK).body(contratos);

        } catch (RecordInexistenteException e) {

            Map<String, Object> jsonContent = new HashMap<String, Object>();
            jsonContent.put("timestamp", Timestamp.valueOf(LocalDateTime.now()));
            jsonContent.put("status", "404");
            jsonContent.put("error", "Not Found");
            jsonContent.put("message", e.getMessage());
            jsonContent.put("path", "/contrato/placa/" + placa);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonContent);
        }
    }

    public ResponseEntity<?> saveContrato(ObjectNode object) {

        Contrato contratoToSave = new Contrato();

        try {

            Cliente cliente = clienteRepository.findByCpf(object.findValue("cpf").asLong());
            if (cliente == null) {
                throw new RecordInexistenteException("Cliente nao cadastrado na base de dados");
            }
            contratoToSave.setCliente(cliente);
            clienteRepository.save(cliente);

            Automovel automovel = automovelRepository.findByPlaca(object.findValue("placa").asText());
            if (automovel == null) {
                throw new RecordInexistenteException("Automovel nao cadastrado na base de dados");
            }

            PriceTable newPrice = priceTableRepository.findByPriceType(automovel.getPriceused().getPriceType());
            Priceused newPriceUsed = new Priceused(newPrice);
            automovel.setPriceused(newPriceUsed);
            priceusedRepository.save(newPriceUsed);

            contratoToSave.setAutomovel(automovel);
            automovelRepository.save(automovel);

            cliente.addContrato(contratoToSave);
            automovel.addContrato(contratoToSave);

            contratoToSave.setPeriodo(object.findValue("periodo").asInt());
            contratoToSave.setPreco(automovel.getPriceused().getPrice());

            contratoRepository.save(contratoToSave);

            return ResponseEntity.status(HttpStatus.CREATED).body(contratoToSave);

        } catch (RecordInexistenteException e) {

            Map<String, Object> jsonContent = new HashMap<String, Object>();
            jsonContent.put("timestamp", Timestamp.valueOf(LocalDateTime.now()));
            jsonContent.put("status", "400");
            jsonContent.put("error", "Bad Request");
            jsonContent.put("message", e.getMessage());
            jsonContent.put("path", "/automovel");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonContent);
        } catch (Exception e) {

            e.printStackTrace();

            Map<String, Object> jsonContent = new HashMap<String, Object>();
            jsonContent.put("timestamp", Timestamp.valueOf(LocalDateTime.now()));
            jsonContent.put("status", "400 - Bad Request");
            jsonContent.put("error", "Bad Request");
            jsonContent.put("message", "Objeto com campo null nao permitido");
            jsonContent.put("path", "/contrato");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonContent);
        }
    }

    public ResponseEntity<?> updateContrato(ObjectNode object, Long nContrato) {

        
        try {
            Contrato contrato = contratoRepository.findByNContrato(nContrato);
            
            if (contrato == null) {
                throw new RecordInexistenteException("Contrato nao cadastrado na base de dados");
            }
            
            Long cpf = object.findValue("cpf").asLong();
            Cliente cliente = clienteRepository.findByCpf(cpf);
            if (cliente != null) {
                contrato.setCliente(cliente);
                clienteRepository.save(cliente);
                contratoRepository.save(contrato);
            }

            Automovel automovel = automovelRepository.findByPlaca(object.findValue("placa").asText());
            if (automovel != null) {
                PriceTable newPrice = priceTableRepository.findByPriceType(automovel.getPriceused().getPriceType());
                Priceused newPriceUsed = new Priceused(newPrice);
                automovel.setPriceused(newPriceUsed);
                priceusedRepository.save(newPriceUsed);
                contrato.setAutomovel(automovel);
                automovelRepository.save(automovel);
            }

            contrato.setPeriodo(object.findValue("periodo").asInt());
            contrato.setPreco(automovel.getPriceused().getPrice());
            contratoRepository.save(contrato);

            return ResponseEntity.status(HttpStatus.OK).body(contrato);
        } catch (Exception e) {

            e.printStackTrace();

            Map<String, Object> jsonContent = new HashMap<String, Object>();
            jsonContent.put("timestamp", Timestamp.valueOf(LocalDateTime.now()));
            jsonContent.put("status", "400 - Bad Request");
            jsonContent.put("error", "Bad Request");
            jsonContent.put("message", "Requisicao com atributos invalidos");
            jsonContent.put("path", "/contrato");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonContent);
        }
    }

    public ResponseEntity<?> deleteContrato(Long nContrato) {
        contratoRepository.deleteByNContrato(nContrato);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("ok");
    }
}
