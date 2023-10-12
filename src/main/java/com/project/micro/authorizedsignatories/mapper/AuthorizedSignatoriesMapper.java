package com.project.micro.authorizedsignatories.mapper;

import com.project.micro.authorizedsignatories.integration.AuthorizedSignatoriesRequest;
import com.project.micro.authorizedsignatories.integration.AuthorizedSignatoriesResponse;
import com.project.micro.authorizedsignatories.model.AuthorizedSignatories;
import org.springframework.beans.BeanUtils;

public class AuthorizedSignatoriesMapper {

    public static AuthorizedSignatories toAuthorizedSignatoriesModelReq(AuthorizedSignatoriesRequest authorizedSignatoriesRequest){
        AuthorizedSignatories authorizedSignatories=new AuthorizedSignatories();
        BeanUtils.copyProperties(authorizedSignatoriesRequest,authorizedSignatories);
        return authorizedSignatories;
    }

    public static AuthorizedSignatoriesResponse toAuthorizedSignatoriesRespModel(AuthorizedSignatories authorizedSignatories){
        AuthorizedSignatoriesResponse authorizedSignatoriesResponse=new AuthorizedSignatoriesResponse();
        BeanUtils.copyProperties(authorizedSignatories,authorizedSignatoriesResponse);
        return authorizedSignatoriesResponse;
    }
}
