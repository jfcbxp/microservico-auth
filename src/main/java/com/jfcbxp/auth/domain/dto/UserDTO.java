package com.jfcbxp.auth.domain.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 4525405587547949112L;

    private String userName;
    private String password;

}
