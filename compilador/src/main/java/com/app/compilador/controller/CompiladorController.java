package com.app.compilador.controller;

import com.app.compilador.service.CompiladorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class CompiladorController {

    @Autowired
    private CompiladorService compilador;

    @PostMapping("/Paginacion")
    public ResponseEntity<Map<String,Object>> Pagina (@RequestBody String texto
            ,Pageable pagina) {
        return ResponseEntity.ok(compilador.paginacion(texto,pagina));
    }
    @PostMapping("/Compilador")
    public ResponseEntity<Map<String,Object>> compila(@RequestBody String texto) {
        return ResponseEntity.ok(compilador.obtenCompilador(texto));
    }


}
