package com.tcc.alphaservice.controller;

import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class AlphaController {

   final RestTemplate restTemplate;

   public AlphaController(RestTemplate restTemplate){
       this.restTemplate = restTemplate;
   }

   @GetMapping("/alpha")
   @Observed(
           name = "user.name",
           contextualName = "alfa-->beta",
           lowCardinalityKeyValues = {"userType", "userType2"}
   )
    public String comunicacaoModulos(){
       log.info("Serviço Alpha foi chamado");
       log.info("Comunicação de Alpha para Gama");
       ResponseEntity<String> response = restTemplate.exchange(
               "http://localhost:6060/beta-svc/beta",
               HttpMethod.GET,
               null,
               String.class
       );

       return "Comunicação entre os serviços(Alpha): "+response.getBody();
   }

}
