package contes.atoslocadora.projections;

import java.time.LocalDate;

import contes.atoslocadora.models.Automovel;
import contes.atoslocadora.models.Cliente;

public class ContratoProjection {

     public interface NContrato {

         LocalDate getData();
    
         Long getnContrato();
         
         Cliente getCliente();
        
         Automovel getAutomovel();

         double getPreco();
         
         int getPeriodo();
    }
    
}
