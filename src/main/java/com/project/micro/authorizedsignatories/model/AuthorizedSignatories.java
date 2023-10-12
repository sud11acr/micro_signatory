package com.project.micro.authorizedsignatories.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Document(collection = "authorizedSignatories")
public class AuthorizedSignatories {

    @Id
    private String idAuthorizedSignatories;
    private String idAccount;
    private String name;
    private String lastName;
    private String documentNumber;
    private Date registrationDate;
    private Date modificationDate;
    private Boolean status;
}
