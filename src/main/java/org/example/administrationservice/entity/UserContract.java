package org.example.administrationservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "user_contract")
@Table(name = "user_contract")
public class UserContract {

    @Id
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "contract_code")
    private String contractCode;
}
