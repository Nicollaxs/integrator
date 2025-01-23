package com.seat.integradorapiwc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.PublicKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
public class ApiService {

    private final RestTemplate restTemplate;

    public ApiService() {
        this.restTemplate = new RestTemplate();
    }

    public String buscaPaciente(String pacienteCpf) {
        String url = "https://api.feegow.com/v1/api/patient/search";

        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("paciente_cpf", pacienteCpf)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-access-token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmZWVnb3ciLCJhdWQiOiJwdWJsaWNhcGkiLCJpYXQiOjE3MzY3OTU3MjEsImxpY2Vuc2VJRCI6NDAzODJ9.9zKsOx_Cq7HxhkhLAXuQY9cIg2BTbQX9vQrMkScxVDc");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(uri,
                org.springframework.http.HttpMethod.GET,
                entity,
                String.class);

        return response.getBody();
    }

    public String buscaAgendamentos(Long pacienteId){
        String url = "https://api.feegow.com/v1/api/appoints/search";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate data = LocalDate.now();

        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("paciente_id",pacienteId)
                .queryParam("data_start",data.format(formatter))
                .queryParam("data_end",data.format(formatter)).toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-access-token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmZWVnb3ciLCJhdWQiOiJwdWJsaWNhcGkiLCJpYXQiOjE3MzY3OTU3MjEsImxpY2Vuc2VJRCI6NDAzODJ9.9zKsOx_Cq7HxhkhLAXuQY9cIg2BTbQX9vQrMkScxVDc");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(uri,org.springframework.http.HttpMethod.GET,
                entity,
                String.class);

        return response.getBody();
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataWrapper {
        private Data content;

        public Data getContent() {
            return content;
        }

    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {
        private int id;
        private String nome;
        @JsonFormat(pattern = "dd-MM-yyyy")
        private LocalDate nascimento;
        private String sexo;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public LocalDate getNascimento() {
            return nascimento;
        }

        public void setNascimento(LocalDate nascimento) {
            this.nascimento = nascimento;
        }

        public String getSexo() {
            return sexo;
        }

        public void setSexo(String sexo) {
            this.sexo = sexo;
        }

        public int getIdade() {
            if (nascimento != null) {
                return Period.between(nascimento, LocalDate.now()).getYears();
            }
            return 0;
        }
    }
}
