package com.project.micro.authorizedsignatories.repo;

import com.project.micro.authorizedsignatories.model.AuthorizedSignatories;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface IAuthorizedSignatoriesRepo extends ReactiveMongoRepository<AuthorizedSignatories,String> {

    Mono<Long> countByIdAccountAndStatus(String idAccount, boolean status);
}
