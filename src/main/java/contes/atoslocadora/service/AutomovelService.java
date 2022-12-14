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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import contes.atoslocadora.repositories.AutomovelRepository;
import contes.atoslocadora.repositories.PriceTableRepository;
import contes.atoslocadora.repositories.PriceusedRepository;
import contes.atoslocadora.exception.RecordExistenteException;
import contes.atoslocadora.exception.RecordInexistenteException;
import contes.atoslocadora.models.Automovel;
import contes.atoslocadora.models.PriceType;
import contes.atoslocadora.models.Priceused;

@Component
public class AutomovelService {

    @Autowired
    AutomovelRepository automovelRepository;

    @Autowired
    PriceTableRepository priceTableRepository;

    @Autowired
    PriceusedRepository priceusedRepository;

    public List<Automovel> getAllAutomoveis() {
        return automovelRepository.findAllByAtivo(true);
    }

    public ResponseEntity<?> getAutomovelByPlaca(String placa) {

        try {

            Automovel automovel = automovelRepository.findByPlaca(placa);

            if (automovel == null) {
                throw new RecordInexistenteException("Automovel nao cadastrado na base de dados");
            }

            return ResponseEntity.status(HttpStatus.OK).body(automovel);

        } catch (Exception e) {

            Map<String, Object> jsonContent = new HashMap<String, Object>();
            jsonContent.put("timestamp", Timestamp.valueOf(LocalDateTime.now()));
            jsonContent.put("status", "404");
            jsonContent.put("error", "Not Found");
            jsonContent.put("message", e.getMessage());
            jsonContent.put("path", "/automovel/" + placa);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonContent);
        }
    }

    public ResponseEntity<?> getAutomovelByMarca(String marca) {

        try {

            List<Automovel> automoveis = automovelRepository.findByMarcaContaining(marca);

            if (automoveis == null || automoveis.size() == 0) {
                throw new RecordInexistenteException("Nao ha automoveis cadastrado na base de dados");
            }

            return ResponseEntity.status(HttpStatus.OK).body(automoveis);

        } catch (Exception e) {

            Map<String, Object> jsonContent = new HashMap<String, Object>();
            jsonContent.put("timestamp", Timestamp.valueOf(LocalDateTime.now()));
            jsonContent.put("status", "404");
            jsonContent.put("error", "Not Found");
            jsonContent.put("message", e.getMessage());
            jsonContent.put("path", "/automovel/" + marca);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonContent);
        }
    }

    public ResponseEntity<?> saveAutomovel(ObjectNode objects) {

        try {
            Automovel automovelToSave = new ObjectMapper().convertValue(
                    objects.findValue("automovel"),
                    Automovel.class);

            Automovel automovel = automovelRepository.findByPlaca(objects.findValue("placa").asText());

            if (automovel != null && !automovel.isAtivo()) {
                automovel.setAtivo(true);
                update(automovel, automovelToSave);
                automovelRepository.save(automovel);
                return ResponseEntity.status(HttpStatus.CREATED).body(automovelToSave);

            } else if (automovel != null) {
                throw new RecordExistenteException("Automovel ja existente na base de dados");
            }

            PriceType priceType = PriceType.valueOf((objects.findValue("priceType").asText()));

            Priceused priceused = new Priceused(priceTableRepository.findByPriceType(priceType));
            automovelToSave.setPriceused(priceused);
            priceusedRepository.save(priceused);

            priceused.addAutomovel(automovelToSave);
            automovelRepository.save(automovelToSave);

            return ResponseEntity.status(HttpStatus.CREATED).body(automovelToSave);
        } catch (RecordExistenteException e) {

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

    public ResponseEntity<?> updateAutomovel(ObjectNode objects) {

        try {

            Automovel automovelToUpdate = new ObjectMapper().convertValue(
                    objects.findValue("automovel"),
                    Automovel.class);

            Automovel automovel = automovelRepository.findByPlaca(automovelToUpdate.getPlaca());

            if (automovel == null || !automovel.isAtivo()) {
                throw new RecordInexistenteException("Automovel nao cadastrado na base de dados");
            }

            Priceused priceToRemove = automovel.getPriceused();
            priceusedRepository.deleteById(priceToRemove.getId());

            PriceType priceType = PriceType.valueOf((objects.findValue("priceType").asText()));
            Priceused priceused = new Priceused(priceTableRepository.findByPriceType(priceType));

            automovel.setPriceused(priceused);
            priceusedRepository.save(priceused);

            update(automovel, automovelToUpdate);

            automovelRepository.save(automovel);

            return ResponseEntity.status(HttpStatus.OK).body(automovel);
        } catch (Exception e) {

            Map<String, Object> jsonContent = new HashMap<String, Object>();
            jsonContent.put("timestamp", Timestamp.valueOf(LocalDateTime.now()));
            jsonContent.put("status", "400 - Bad Request");
            jsonContent.put("error", "Bad Request");
            jsonContent.put("message", "Requisicao com atributos invalidos");
            jsonContent.put("path", "/automovel");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonContent);
        }
    }

    public ResponseEntity<?> deleteAutomovel(String placa) {

        try {

            Automovel automovel = automovelRepository.findByPlaca(placa);

            if (automovel == null) {
                throw new RecordInexistenteException("Automovel nao cadastrado na base de dados");
            }

            automovel.setAtivo(false);
            automovelRepository.save(automovel);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Automovel removido com sucesso...");

        } catch (Exception e) {

            Map<String, Object> jsonContent = new HashMap<String, Object>();
            jsonContent.put("timestamp", Timestamp.valueOf(LocalDateTime.now()));
            jsonContent.put("status", "404");
            jsonContent.put("error", "Not Found");
            jsonContent.put("message", e.getMessage());
            jsonContent.put("path", "/automovel/" + placa);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonContent);
        }
    }

    public void update(Automovel automovel, Automovel automovelToUpdate) {
        if (automovelToUpdate.getAno() != 0)
            automovel.setAno(automovelToUpdate.getAno());
        if (automovelToUpdate.getMarca() != null)
            automovel.setMarca(automovelToUpdate.getMarca());
        if (automovelToUpdate.getModelo() != null)
            automovel.setModelo(automovelToUpdate.getModelo());
        if (automovelToUpdate.getVersao() != null)
            automovel.setVersao(automovelToUpdate.getVersao());
    }
}
