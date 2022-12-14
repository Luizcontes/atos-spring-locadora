package contes.atoslocadora.controllers;

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

import com.fasterxml.jackson.databind.node.ObjectNode;

import contes.atoslocadora.service.ContratoService;

@RestController
@RequestMapping("/contrato")
public class ContratoController {

    @Autowired
    ContratoService contratoService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {

        return contratoService.findAll();
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<?> getAutomoveis(@PathVariable("cpf") Long cpf) {

        return contratoService.getAllContratosFromCliente(cpf);
    }

    @GetMapping("/placa/{placa}")
    public ResponseEntity<?> getAutomovelByPlaca(@PathVariable("placa") String placa) {

        return contratoService.getAllContratosFromAutomovel(placa);
    }

    @PostMapping("")
    public ResponseEntity<?> persistContrato(@RequestBody ObjectNode object) {

        return contratoService.saveContrato(object);
    }

    @PutMapping("/{nContrato}")
    public ResponseEntity<?> updateContrato(@RequestBody ObjectNode object, @PathVariable("nContrato") Long nContrato) {

        return contratoService.updateContrato(object, nContrato);
    }
    
    @DeleteMapping("/{contrato}")
    public ResponseEntity<?> deleteContrato(@PathVariable("contrato") Long nContrato) {
        return contratoService.deleteContrato(nContrato);
    }
}
