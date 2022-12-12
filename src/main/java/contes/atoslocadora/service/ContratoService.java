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

import contes.atoslocadora.repositories.AutomovelRepository;
import contes.atoslocadora.repositories.ClienteRepository;
import contes.atoslocadora.repositories.ContratoRepository;
import contes.atoslocadora.exception.RecordInexistenteException;
import contes.atoslocadora.models.Automovel;
import contes.atoslocadora.models.Cliente;
import contes.atoslocadora.models.Contrato;

@Component
public class ContratoService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    AutomovelRepository automovelRepository;

    @Autowired
    ContratoRepository contratoRepository;

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

            List<Contrato> contratos = contratoRepository.getByAutomovelPlaca(automovel.getId());

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

    public ResponseEntity<?> saveContrato(Contrato contratoToSave) {

        try {

            Cliente cliente = clienteRepository.findByCpf(contratoToSave.getCpf());
            if (cliente == null) {
                throw new RecordInexistenteException("Cliente nao cadastrado na base de dados");
            }
            contratoToSave.setCliente(cliente);
            clienteRepository.save(cliente);

            Automovel automovel = automovelRepository.findByPlaca(contratoToSave.getPlaca());
            if (automovel == null) {
                throw new RecordInexistenteException("Automovel nao cadastrado na base de dados");
            }
            contratoToSave.setAutomovel(automovel);
            automovelRepository.save(automovel);

            cliente.addContrato(contratoToSave);
            automovel.addContrato(contratoToSave);

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

            Map<String, Object> jsonContent = new HashMap<String, Object>();
            jsonContent.put("timestamp", Timestamp.valueOf(LocalDateTime.now()));
            jsonContent.put("status", "400 - Bad Request");
            jsonContent.put("error", "Bad Request");
            jsonContent.put("message", "Objeto com campo null nao permitido");
            jsonContent.put("path", "/automovel");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonContent);
        }
    }

    public ResponseEntity<?> updateContrato(Contrato contratoToUpdate) {

        try {
            Contrato contrato = contratoRepository.findByNContrato(contratoToUpdate.getnContrato());

            if (contrato == null) {
                throw new RecordInexistenteException("Contrato nao cadastrado na base de dados");
            }

            if (contratoToUpdate.getCpf() != 0) contrato.setCliente(clienteRepository.findByCpf(contratoToUpdate.getCpf()));
    
            if (contratoToUpdate.getPlaca() != null) {
                contrato.setAutomovel(automovelRepository.findByPlaca(contratoToUpdate.getPlaca()));
                contrato.setPlaca(contratoToUpdate.getPlaca());
            }
    
            if (contratoToUpdate.getPreco() > 0) contrato.setPreco(contratoToUpdate.getPreco());

            if (contratoToUpdate.getPeriodo() > 0) contrato.setPeriodo(contratoToUpdate.getPeriodo());

            contratoRepository.save(contrato);

            return ResponseEntity.status(HttpStatus.OK).body(contrato);
        } catch (Exception e) {

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
