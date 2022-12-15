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

import contes.atoslocadora.exception.RecordExistenteException;
import contes.atoslocadora.exception.RecordInexistenteException;
import contes.atoslocadora.models.Cliente;
import contes.atoslocadora.projections.ClienteProjection;
import contes.atoslocadora.repositories.ClienteRepository;

@Component
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    public List<Cliente> getAllClientes() {

        return clienteRepository.findByAtivo(true);
    }

    public ResponseEntity<?> getClienteByCpf(Long cpf) {

        try {

            Cliente cliente = clienteRepository.findByCpf(cpf);

            if (cliente == null) {
                throw new RecordInexistenteException("Cliente nao cadastrado na base de dados");
            }

            return ResponseEntity.status(HttpStatus.OK).body(cliente);

        } catch (Exception e) {

            Map<String, Object> jsonContent = new HashMap<String, Object>();
            jsonContent.put("timestamp", Timestamp.valueOf(LocalDateTime.now()));
            jsonContent.put("status", "404");
            jsonContent.put("error", "Not Found");
            jsonContent.put("message", e.getMessage());
            jsonContent.put("path", "/cliente/" + cpf);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonContent);
        }
    }

    public ResponseEntity<?> getClienteByNome(String nome) {

        try {

            List<Cliente> clientes = clienteRepository.findByNomeContaining(nome);

            if (clientes == null || clientes.size() == 0) {
                throw new RecordInexistenteException("Nao ha clientes cadastrado na base de dados");
            }

            return ResponseEntity.status(HttpStatus.OK).body(clientes);

        } catch (Exception e) {

            Map<String, Object> jsonContent = new HashMap<String, Object>();
            jsonContent.put("timestamp", Timestamp.valueOf(LocalDateTime.now()));
            jsonContent.put("status", "404");
            jsonContent.put("error", "Not Found");
            jsonContent.put("message", e.getMessage());
            jsonContent.put("path", "/cliente/" + nome);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonContent);
        }
    }

    public ResponseEntity<?> save(Cliente clienteToSave) {

        try {
            Cliente cliente = clienteRepository.findByCpf(clienteToSave.getCpf());
            if (cliente != null && !cliente.isAtivo()) {
                cliente.setAtivo(true);
                update(cliente, clienteToSave);
                clienteRepository.save(cliente);

                return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
            }
            if (cliente != null) {
                throw new RecordExistenteException("Cliente ja existente na base de dados");
            }

            clienteRepository.save(clienteToSave);
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteToSave);
        } catch (RecordExistenteException e) {

            Map<String, Object> jsonContent = new HashMap<String, Object>();
            jsonContent.put("timestamp", Timestamp.valueOf(LocalDateTime.now()));
            jsonContent.put("status", "400");
            jsonContent.put("error", "Bad Request");
            jsonContent.put("message", e.getMessage());
            jsonContent.put("path", "/cliente");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonContent);
        } catch (Exception e) {

            Map<String, Object> jsonContent = new HashMap<String, Object>();
            jsonContent.put("timestamp", Timestamp.valueOf(LocalDateTime.now()));
            jsonContent.put("status", "400 - Bad Request");
            jsonContent.put("error", "Bad Request");
            jsonContent.put("message", "Objeto com campo null nao permitido");
            jsonContent.put("path", "/cliente");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonContent);
        }
    }

    public ResponseEntity<?> updateCliente(Cliente clienteToUpdate) {

        try {
            Cliente cliente = clienteRepository.findByCpf(clienteToUpdate.getCpf());

            if (cliente == null || !cliente.isAtivo()) {
                throw new RecordInexistenteException("Cliente nao cadastrado na base de dados");
            }

            update(cliente, clienteToUpdate);

            // if (clienteToUpdate.getCnh() != null)
            // cliente.setCnh(clienteToUpdate.getCnh());
            // if (clienteToUpdate.getNome() != null)
            // cliente.setNome(clienteToUpdate.getNome());
            // if (clienteToUpdate.getEndereco() != null)
            // cliente.setEndereco(clienteToUpdate.getEndereco());

            clienteRepository.save(cliente);

            return ResponseEntity.status(HttpStatus.OK).body(cliente);
        } catch (Exception e) {

            Map<String, Object> jsonContent = new HashMap<String, Object>();
            jsonContent.put("timestamp", Timestamp.valueOf(LocalDateTime.now()));
            jsonContent.put("status", "400 - Bad Request");
            jsonContent.put("error", "Bad Request");
            jsonContent.put("message", "Requisicao com atributos invalidos");
            jsonContent.put("path", "/cliente");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonContent);
        }
    }

    public ResponseEntity<?> deleteCliente(Long cpf) {

        try {

            Cliente cliente = clienteRepository.findByCpf(cpf);

            if (cliente == null) {
                throw new RecordInexistenteException("Cliente nao cadastrado na base de dados");
            }

            cliente.setAtivo(false);
            clienteRepository.save(cliente);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cliente removido com sucesso...");

        } catch (Exception e) {

            Map<String, Object> jsonContent = new HashMap<String, Object>();
            jsonContent.put("timestamp", Timestamp.valueOf(LocalDateTime.now()));
            jsonContent.put("status", "404");
            jsonContent.put("error", "Not Found");
            jsonContent.put("message", e.getMessage());
            jsonContent.put("path", "/cpf/" + cpf);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonContent);
        }
    }

    public void update(Cliente cliente, Cliente clienteToUpdate) {
        if (clienteToUpdate.getCnh() != null)
            cliente.setCnh(clienteToUpdate.getCnh());
        if (clienteToUpdate.getNome() != null)
            cliente.setNome(clienteToUpdate.getNome());
        if (clienteToUpdate.getEndereco() != null)
            cliente.setEndereco(clienteToUpdate.getEndereco());
    }

}
