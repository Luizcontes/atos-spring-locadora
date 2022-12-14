package contes.atoslocadora.controllers;

import java.io.IOException;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.node.ObjectNode;

import contes.atoslocadora.models.Automovel;
import contes.atoslocadora.service.AutomovelService;

@RestController
@RequestMapping("/automovel")
public class AutomovelController {

    @Autowired
    AutomovelService automovelService;

    @GetMapping("")
    public List<Automovel> getAutomoveis() {

        return automovelService.getAllAutomoveis();
    }

    @GetMapping("/placa/{placa}")
    public ResponseEntity<?> getAutomovelByPlaca(@PathVariable("placa") String placa) {

        return automovelService.getAutomovelByPlaca(placa);
    }

    @GetMapping("/marca/{marca}")
    public ResponseEntity<?> getAutomovelByMarca(@PathVariable("marca") String marca) {

        return automovelService.getAutomovelByMarca(marca);
    }

    @PostMapping("")
    public ResponseEntity<?> persistCliente(@RequestBody ObjectNode objects) throws JsonParseException, IOException {

        return automovelService.saveAutomovel(objects);
    }

    @PutMapping("")
    public ResponseEntity<?> updateAutomovel(@RequestBody ObjectNode objects) throws JsonParseException, IOException {

        return automovelService.updateAutomovel(objects);
    }

    @DeleteMapping("/{placa}")
    public ResponseEntity<?> deleteAutomovel(@PathVariable("placa") String placa) {
        return automovelService.deleteAutomovel(placa);
    }

}
