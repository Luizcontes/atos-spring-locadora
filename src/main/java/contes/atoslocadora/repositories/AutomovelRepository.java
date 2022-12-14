package contes.atoslocadora.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import contes.atoslocadora.models.Automovel;

public interface AutomovelRepository extends JpaRepository<Automovel, Long> {
    
    
    List<Automovel> findAllByAtivo(boolean ativo);
    
    Automovel findByPlaca(String string);
    List<Automovel> findByMarcaContaining(String marca);
}
