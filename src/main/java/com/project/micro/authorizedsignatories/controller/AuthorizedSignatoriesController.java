package com.project.micro.authorizedsignatories.controller;

import com.project.micro.authorizedsignatories.integration.AuthorizedSignatoriesRequest;
import com.project.micro.authorizedsignatories.integration.AuthorizedSignatoriesResponse;
import com.project.micro.authorizedsignatories.service.IAuthorizedSignatoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/authorizedSignatories")
public class AuthorizedSignatoriesController {

    @Autowired
    private IAuthorizedSignatoriesService service;

    @GetMapping("/findById/{id}")
    public Mono<ResponseEntity<AuthorizedSignatoriesResponse>> findByid(@PathVariable String id){
        return service.findById(id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @GetMapping("/findAll")
    public Mono<ResponseEntity<Flux<AuthorizedSignatoriesResponse>>> findAll() {
        return Mono.just(
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(service.findAll()));
    }

    @PostMapping("/create")
    public Mono<ResponseEntity<AuthorizedSignatoriesResponse>>save(@Validated @RequestBody  Mono<AuthorizedSignatoriesRequest> authorizedSignatoriesRequest){
        return service.save(authorizedSignatoriesRequest)
                .map(p -> ResponseEntity.created(URI.create("/create".concat("/").concat(p.getIdAuthorizedSignatories())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p));
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<AuthorizedSignatoriesResponse>>update(@PathVariable String id,@RequestBody Mono<AuthorizedSignatoriesRequest> authorizedSignatoriesRequest ){
        return service.update(authorizedSignatoriesRequest,id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return service.delete(id).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
    }
}
