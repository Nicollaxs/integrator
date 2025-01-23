package com.seat.integradorapiwc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/feggow")
public class FeggowController {

    @Autowired
    private ApiService apiService;

    private static Long idPaciente;


    @GetMapping("/{paciente_cpf}")
    public ResponseEntity<Object> buscaPaciente(@PathVariable String paciente_cpf) {
        try {
            String response = apiService.buscaPaciente(paciente_cpf);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            ApiService.DataWrapper dataWrapper = objectMapper.readValue(response, ApiService.DataWrapper.class);

            ApiService.Data content = dataWrapper.getContent();

            // Montando o resultado com os campos solicitados
            Map<String, Object> result = new HashMap<>();
            result.put("Id", content.getId());
            result.put("Nome", content.getNome());
            result.put("Idade", content.getIdade());
            result.put("Sexo", content.getSexo());

            idPaciente =(long) content.getId();


            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro: " + e.getMessage());
        }
    }

    @GetMapping("/agendamentos/")
    public ResponseEntity<Object> buscarAgendamentos(){
        try{
            String response = apiService.buscaAgendamentos(idPaciente);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            return ResponseEntity.status(500).body("Erro : " + e.getMessage());
        }
    }
}
