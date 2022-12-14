package contes.atoslocadora.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import contes.atoslocadora.exception.RecordExistenteException;
import contes.atoslocadora.exception.RecordInexistenteException;
import contes.atoslocadora.models.PriceTable;
import contes.atoslocadora.models.PriceType;
import contes.atoslocadora.models.Priceused;
import contes.atoslocadora.repositories.AutomovelRepository;
import contes.atoslocadora.repositories.PriceTableRepository;
import contes.atoslocadora.repositories.PriceusedRepository;

@Repository
public class PriceService {
    
    @Autowired
    PriceusedRepository priceusedRepository;

    @Autowired
    PriceTableRepository priceTableRepository;

    @Autowired
    AutomovelRepository automovelRepository;

    public Priceused getAutomoveisByPrice(String priceString) {

        PriceType priceType = PriceType.valueOf(priceString);

        return priceusedRepository.getPriceByPriceType(priceType);
    }

    public ResponseEntity<?> setPrice(PriceTable priceTableToSet) {
        
        try {
            PriceTable priceTable = priceTableRepository.findByPriceType(priceTableToSet.getPriceType());
            
            if (priceTable.equals(priceTableToSet)) {
                throw new RecordExistenteException("Preco ja existente na base de dados");
            }

            priceTableRepository.save(priceTableToSet);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(priceTableToSet);
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

    public ResponseEntity<?> updatePrice(PriceTable priceTableToSet) {
        
        try {
            PriceTable priceTable = priceTableRepository.findByPriceType(priceTableToSet.getPriceType());
            
            if (!priceTable.equals(priceTableToSet)) {
                throw new RecordInexistenteException("Preco nao existente na base de dados");
            }

            priceTable.setPrice(priceTableToSet.getPrice());
            priceTableRepository.save(priceTable);

            return ResponseEntity.status(HttpStatus.OK).body(priceTable);
        } catch (RecordInexistenteException e) {

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

}
