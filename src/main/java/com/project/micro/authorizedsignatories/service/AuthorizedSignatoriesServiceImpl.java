package com.project.micro.authorizedsignatories.service;

import com.project.micro.authorizedsignatories.exception.ErrorException;
import com.project.micro.authorizedsignatories.integration.AuthorizedSignatoriesRequest;
import com.project.micro.authorizedsignatories.integration.AuthorizedSignatoriesResponse;
import com.project.micro.authorizedsignatories.mapper.AuthorizedSignatoriesMapper;
import com.project.micro.authorizedsignatories.model.AuthorizedSignatories;
import com.project.micro.authorizedsignatories.repo.IAuthorizedSignatoriesRepo;
import com.project.micro.authorizedsignatories.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class AuthorizedSignatoriesServiceImpl implements IAuthorizedSignatoriesService{

    @Autowired
    private IAuthorizedSignatoriesRepo repo;
    @Override
    public Mono<AuthorizedSignatoriesResponse> findById(String id) {
        return repo.findById(id).map(p-> AuthorizedSignatoriesMapper.toAuthorizedSignatoriesRespModel(p));
    }

    @Override
    public Flux<AuthorizedSignatoriesResponse> findAll() {
        return repo.findAll().map(p->AuthorizedSignatoriesMapper.toAuthorizedSignatoriesRespModel(p));
    }

    @Override
    public Mono<AuthorizedSignatoriesResponse> save(Mono<AuthorizedSignatoriesRequest> authorizedSignatoriesRequest) {

        return authorizedSignatoriesRequest
                .flatMap(request->{
                    return countSignatories(request.getIdAccount()).
                        flatMap(valid->{
                            if(valid){
                                return Mono.just(request);
                            }else{
                                return Mono.error(new ErrorException("Numero de firmantes permitido: 4"));
                            }
                        }).map(p-> AuthorizedSignatoriesMapper.toAuthorizedSignatoriesModelReq(p))
                        .flatMap(
                                p->{
                                    p.setRegistrationDate(new Date());
                                    p.setModificationDate(new Date());
                                    p.setStatus(true);
                                    return repo.save(p);
                                })
                        .map(p->AuthorizedSignatoriesMapper.toAuthorizedSignatoriesRespModel(p));
                });
        /*
        return authorizedSignatoriesRequest.map(p-> AuthorizedSignatoriesMapper.toAuthorizedSignatoriesModelReq(p))
                .flatMap(
                        p->{
                            p.setRegistrationDate(new Date());
                            p.setModificationDate(new Date());
                            p.setStatus(true);
                            return repo.save(p);
                        })
                .map(p->AuthorizedSignatoriesMapper.toAuthorizedSignatoriesRespModel(p));

         */
    }

    @Override
    public Mono<AuthorizedSignatoriesResponse> update(Mono<AuthorizedSignatoriesRequest> authorizedSignatoriesRequest, String id) {
        Mono<AuthorizedSignatories> monoBody = authorizedSignatoriesRequest.map(p-> AuthorizedSignatoriesMapper.toAuthorizedSignatoriesModelReq(p));
        Mono<AuthorizedSignatories> monoBD = repo.findById(id);

        return monoBD.zipWith(monoBody,(bd,pl)->{
                    bd.setModificationDate(new Date());
                    bd.setName(pl.getName());
                    bd.setIdAccount(pl.getIdAccount());
                    return bd;
                }).flatMap(p->repo.save(p))
                .map(c->AuthorizedSignatoriesMapper.toAuthorizedSignatoriesRespModel(c));
    }

    @Override
    public Mono<Void> delete(String id) {
        return repo.deleteById(id);
    }

    public Mono<Boolean> countSignatories(String idAccount) {
        return repo.countByIdAccountAndStatus(idAccount, true)
                .map(count -> count < Constants.MAX_SIGNATURES)
                .defaultIfEmpty(true);
    }


}
