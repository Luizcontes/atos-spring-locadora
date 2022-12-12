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

import contes.atoslocadora.models.Contrato;
import contes.atoslocadora.service.ContratoService;

@RestController
@RequestMapping("/contrato")
public class ContratoController {

    @Autowired
    ContratoService contratoService;

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<?> getAutomoveis(@PathVariable("cpf") Long cpf) {

        return contratoService.getAllContratosFromCliente(cpf);
    }

    @GetMapping("/placa/{placa}")
    public ResponseEntity<?> getAutomovelByPlaca(@PathVariable("placa") String placa) {

        return contratoService.getAllContratosFromAutomovel(placa);
    }

    @PostMapping("")
    public ResponseEntity<?> persistContrato(@RequestBody Contrato contrato) {

        return contratoService.saveContrato(contrato);
    }

    @PutMapping("")
    public ResponseEntity<?> updateContrato(@RequestBody Contrato contrato) {

        return contratoService.updateContrato(contrato);
    }

    @DeleteMapping("/{contrato}")
    public ResponseEntity<?> deleteContrato(@PathVariable("contrato") Long nContrato) {
        return contratoService.deleteContrato(nContrato);
    }
}
