package com.app.compilador.Clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name="lexema")
public interface LexemaClient {
    @PostMapping("/Lexema")
    public ResponseEntity<Map<String,Object>> getTokens(@RequestBody  String data);
}
