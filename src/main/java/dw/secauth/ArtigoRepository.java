package dw.secauth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtigoRepository extends JpaRepository<Artigo, Long>{
    
    List<Artigo> findByPublicado(boolean publicado);

    List<Artigo> findByTituloContaining(String titulo);
}
