package com.app.compilador.service;

import com.app.compilador.Clients.LexemaClient;
import com.app.compilador.Clients.ParserClient;
import com.app.compilador.Clients.SemanticaClient;
import com.app.compilador.domain.ArbolAst;
import com.app.compilador.domain.Compilador;
import com.app.compilador.domain.Dvariable;
import com.app.compilador.domain.Token;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CompiladorService {
    @Autowired
    private LexemaClient lexema;
    @Autowired
    private ParserClient parser;
    @Autowired
    private SemanticaClient semantica;


    private Compilador compiladorInfo(List<Token> datos){return new Compilador(datos);}

    public Map<String,Object> paginacion(String texto, Pageable page){
          Map<String,Object> mapLexema = lexema.getTokens(texto).getBody();
          List<Token> tokens = obtenLista(Objects.requireNonNull(mapLexema));
          int totalElements = tokens.size();
          int inicio = (int)page.getOffset();
          if(inicio>=tokens.size())return Map.of("error","Pagina fuera de rango");
          int end = Math.min(inicio+page.getPageSize(),tokens.size());
          List<Token> pagedTokens = tokens.subList(inicio,end);
          Page<Token> pagina = new PageImpl<>(pagedTokens,page,totalElements);
          mapLexema.replace("Tokens",pagina);
          return mapLexema;
    }
    public Map<String,Object> obtenCompilador(String texto){
        Map<String,Object> mapa = new HashMap<>();
        Map<String, Object> JSONLexema = lexema.getTokens(texto).getBody();
        List<Token> tokens = obtenLista(Objects.requireNonNull(JSONLexema));
        List<String> errores = new ArrayList<>();
        errores.addAll((List<String>)JSONLexema.get("Errores"));
        Compilador c = compiladorInfo(tokens);
        List<ArbolAst> arboles = new ArrayList<>();
        List<Token> tokens2 = new ArrayList<>();
        c.clase.metodo().variables().forEach(expresion->{
            ObjectMapper mapeador = new ObjectMapper();
            Map<String,Object> JSONParser = parser.obtenArbol(expresion.getExpresion()).getBody();
            ArbolAst arbol = mapeador.convertValue(Objects.requireNonNull(JSONParser).get("Arbol"),ArbolAst.class);
            List<String> ParserErrores = (List<String>) JSONParser.get("Errores");
            errores.addAll(ParserErrores);
            expresion.setArbol(arbol);
            if(ParserErrores.isEmpty()) {
                arboles.add(expresion.getArbol());
                tokens2.add(expresion.getExpresion().getFirst());
            }else {
                arboles.add(new ArbolAst());
                tokens2.add(new Token("",""));
            }
        });
        Map<String,Object> data = new HashMap<>();
        data.put("arboles",arboles);
        data.put("tokens",tokens2);
        Map<String,Object>JSONSemantica = semantica.semantica(data).getBody();
        List<String>tipos = (List<String>)JSONSemantica.get("Tipos");
        List<String>Serrores = (List<String>)JSONSemantica.get("Errores");
        errores.addAll(Serrores);
        for (int i = 0; i <tipos.size() ; i++) {
            c.clase.metodo().variables().get(i).setTipo(tipos.get(i));
        }
        mapa.put("Compilador", c.toString());
        mapa.put("Errores", errores);
        return mapa;
    }
    private void asignaExpresion(Dvariable expresion, List<String> errores){
        ObjectMapper mapeador = new ObjectMapper();
        Map<String,Object> JSONParser = parser.obtenArbol(expresion.getExpresion()).getBody();
        ArbolAst arbol = mapeador.convertValue(Objects.requireNonNull(JSONParser).get("Arbol"),ArbolAst.class);
        List<String> ParserErrores = (List<String>) JSONParser.get("Errores");
        errores.addAll(ParserErrores);
        expresion.setArbol(arbol);
        if(ParserErrores.isEmpty()) {
            Map<String, Object> data = new HashMap<>();
            data.put("Arbol", expresion.getArbol());
            data.put("Token", expresion.getExpresion().getFirst());
        }
    }

    private void asignaExpresionSemantico(Dvariable expresion,List<String> errores,Map<String,Object>data){
        Map<String,Object> JSONSemantico = semantica.semantica(data).getBody();
        String tipo = (String)JSONSemantico.get("Tipo");
        List<String> Serrores = (List<String>)JSONSemantico.get("Errores");
        errores.addAll(Serrores);
        expresion.setTipo(tipo);
    }

    private List<Token> obtenLista(Map<String,Object>resultado){
        List<Token>tokens = new ArrayList<>();
        List<Map<String,Object>> tokensMap = (List<Map<String,Object>>)resultado.get("Tokens");
        for(Map<String,Object> t : tokensMap){
            tokens.add(new Token(t.get("token").toString(),t.get("lexema").toString()));
        }
        return tokens;
    }


}
