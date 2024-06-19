package dw.secauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/principal/api")
public class ArtigoController {

    @Autowired
    ArtigoRepository rep;

    @GetMapping("/artigos")
    public ResponseEntity<List<Artigo>> getAllArtigos(@RequestParam(required = false) String titulo) {
        try {
            List<Artigo> la = new ArrayList<>();

            if (titulo == null)
                rep.findAll().forEach(la::add);
            else
                rep.findByTituloContaining(titulo).forEach(la::add);

            if (la.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(la, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/artigos")
    public ResponseEntity<Artigo> createArtigo(@RequestBody Artigo ar) {
        try {
            Artigo _a = rep.save(new Artigo(ar.getTitulo(), ar.getResumo(), ar.isPublicado()));
            return new ResponseEntity<>(_a, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/teste")
    public ResponseEntity<Artigo> teste(){
        Artigo art = new Artigo("teste2", "isso e um teste2", false);
        try {
            Artigo _a = rep.save(art);
            return new ResponseEntity<>(_a, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/artigos/{id}")
    public ResponseEntity<Artigo> getArtigoById(@PathVariable("id") long id) {
        Optional<Artigo> data = rep.findById(id);

        if (data.isPresent())
            return new ResponseEntity<>(data.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/artigos/{id}")
    public ResponseEntity<Artigo> updateArtigo(@PathVariable("id") long id, @RequestBody Artigo a) {
        Optional<Artigo> data = rep.findById(id);

        if (data.isPresent()) {
            Artigo ar = data.get();
            ar.setPublicado(a.isPublicado());
            ar.setResumo(a.getResumo());
            ar.setTitulo(a.getTitulo());

            return new ResponseEntity<>(rep.save(ar), HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/artigos/{id}")
    public ResponseEntity<HttpStatus> deleteArtigo(@PathVariable("id") long id) {
        try {
            rep.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/artigos")
    public ResponseEntity<HttpStatus> deleteAllArtigo() {
        try {
            rep.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
