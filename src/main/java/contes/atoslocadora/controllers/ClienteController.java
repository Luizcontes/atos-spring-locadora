package contes.atoslocadora.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import contes.atoslocadora.models.Cliente;
import contes.atoslocadora.service.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @GetMapping("")
    public List<Cliente> getClientes() {

        return clienteService.getAllClientes();
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<?> getClienteByCpf(@PathVariable("cpf") Long cpf) {

        return clienteService.getClienteByCpf(cpf);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<?> getClienteByCpf(@PathVariable("nome") String texto) {

        return clienteService.getClienteByNome(texto);
    }

    @PostMapping("")
    public ResponseEntity<?> persistCliente(@RequestBody Cliente cliente) {

        return clienteService.save(cliente);
    }

    @PutMapping("")
    public ResponseEntity<?> updateCliente(@RequestBody Cliente cliente) {

        return clienteService.updateCliente(cliente);
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<?> deleteCliente(@PathVariable("cpf") Long cpf) {

        return clienteService.deleteCliente(cpf);
    }
}
