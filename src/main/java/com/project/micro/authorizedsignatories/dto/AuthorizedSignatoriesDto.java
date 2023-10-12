package com.project.micro.authorizedsignatories.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AuthorizedSignatoriesDto {

    private String idAuthorizedSignatories;
    private String idAccount;
    private String name;

}
