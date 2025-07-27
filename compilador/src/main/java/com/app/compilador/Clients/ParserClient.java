package com.app.compilador.Clients;

import com.app.compilador.domain.ArbolAst;
import com.app.compilador.domain.Token;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name="parser")
public interface ParserClient {
    @PostMapping("/Parser")
    public ResponseEntity<Map<String,Object>> obtenArbol(@RequestBody List<Token> expresion);
}
