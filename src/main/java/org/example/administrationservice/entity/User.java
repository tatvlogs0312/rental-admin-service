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
@Entity(name = "rent_user")
@Table(name = "rent_user")
public class User {

    //id
    @Id
    @Column(name = "id")
    private String id;

    //tên đăng nhập
    @Column(name = "username")
    private String username;

    //mật khẩu đăng nhập
    @Column(name = "password")
    private String password;

    //role
    @Column(name = "role")
    private String role;

    //trạng thái account
    @Column(name = "status")
    private String status;

    //email
    @Column(name = "email")
    private String email;
}