package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/clients")
    @ResponseStatus(HttpStatus.OK)
    public List<ClientDTO> getClients() {
        return clientRepository
                .findAll()
                .stream()
                .map(ClientDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/clients/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClientDTO getClient(@PathVariable(value = "id") Long id) {
        return clientRepository
                .findById(id)
                .map(ClientDTO::new)
                .orElse(null);
    }

    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {
        Random random = new Random();
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (clientRepository.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        } else {
            accountRepository.save(new Account("VIN-"+String.valueOf(random.nextInt(99999999-1)+1), LocalDate.now(), 0,
                    clientRepository.save(new Client(firstName, lastName, email, passwordEncoder.encode(password)))));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @GetMapping("/clients/current")
    public ClientDTO getClient(Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        return new ClientDTO(client);
    }


}



