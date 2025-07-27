package com.app.compilador.Clients;

import com.app.compilador.domain.ArbolAst;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.Map;

@FeignClient(name="semantica")
public interface SemanticaClient {
    @PostMapping("/semantica")
    public ResponseEntity<Map<String,Object>> semantica(@RequestBody Map<String,Object> data);

}
