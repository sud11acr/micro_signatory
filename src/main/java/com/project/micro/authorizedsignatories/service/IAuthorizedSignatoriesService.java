package com.project.micro.authorizedsignatories.service;

import com.project.micro.authorizedsignatories.integration.AuthorizedSignatoriesRequest;
import com.project.micro.authorizedsignatories.integration.AuthorizedSignatoriesResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAuthorizedSignatoriesService {

    Mono<AuthorizedSignatoriesResponse> findById(String id);
    Flux<AuthorizedSignatoriesResponse> findAll();
    Mono<AuthorizedSignatoriesResponse>save(Mono<AuthorizedSignatoriesRequest>ownerRequestMono);
    Mono<AuthorizedSignatoriesResponse>update(Mono<AuthorizedSignatoriesRequest>ownerRequestMono,String id);
    Mono<Void>delete(String id);
}
