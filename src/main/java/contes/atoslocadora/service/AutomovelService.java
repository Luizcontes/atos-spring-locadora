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
import contes.atoslocadora.exception.RecordExistenteException;
import contes.atoslocadora.exception.RecordInexistenteException;
import contes.atoslocadora.models.Automovel;

@Component
public class AutomovelService {
    

    @Autowired
    AutomovelRepository automovelRepository;

    public List<Automovel> getAllAutomoveis() {
        return automovelRepository.findAll();
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

    public ResponseEntity<?> saveAutomovel(Automovel automovelToSave) {
        
        try {
            Automovel automovel = automovelRepository.findByPlaca(automovelToSave.getPlaca());
            if (automovel!= null) {
                throw new RecordExistenteException("Automovel ja existente na base de dados");
            }

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

    public ResponseEntity<?> updateAutomovel(Automovel automovelToUpdate) {

        try {
            Automovel automovel = automovelRepository.findByPlaca(automovelToUpdate.getPlaca());

            if (automovel == null) {
                throw new RecordInexistenteException("Automovel nao cadastrado na base de dados");
            }

            if (automovelToUpdate.getAno() != 0) automovel.setAno(automovelToUpdate.getAno());
            if (automovelToUpdate.getMarca() != null) automovel.setMarca(automovelToUpdate.getMarca());
            if (automovelToUpdate.getModelo() != null) automovel.setModelo(automovelToUpdate.getModelo());
            if (automovelToUpdate.getVersao() != null) automovel.setVersao(automovelToUpdate.getVersao());

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

    // public void delete(automovel automovel) {

    // }
}
