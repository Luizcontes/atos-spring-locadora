package contes.atoslocadora.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import contes.atoslocadora.models.PriceTable;
import contes.atoslocadora.models.Priceused;
import contes.atoslocadora.service.PriceService;

@RestController
@RequestMapping("/price")
public class PriceController {

    @Autowired
    PriceService priceService;

    @GetMapping("/{pricetype}")
    public Priceused getAutomoveisPrice(@PathVariable("pricetype") String priceType) {

        // priceService.getAutomoveisByPrice(priceType);
        return priceService.getAutomoveisByPrice(priceType);
    }

    @PostMapping("/setprice")
    public ResponseEntity<?> setPrice(@RequestBody PriceTable priceTable) {
        
        return priceService.setPrice(priceTable);
    
    }
    @PutMapping("/updateprice")
    public ResponseEntity<?> updatePrice(@RequestBody PriceTable priceTable) {
        
        return priceService.updatePrice(priceTable);
    }

}
