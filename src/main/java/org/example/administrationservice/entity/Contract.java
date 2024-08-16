package org.example.administrationservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "contract")
@Table(name = "contract")
public class Contract {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "birthdate")
    private String birthdate;

    @Column(name = "identity_card_number")
    private String identityCardNumber;

    @Column(name = "gender_code")
    private String genderCode;

    @Column(name = "gender")
    private String gender;

    @Column(name = "place_of_origin", length = Integer.MAX_VALUE)
    private String placeOfOrigin;

    @Column(name = "place_of_residence", length = Integer.MAX_VALUE)
    private String placeOfResidence;

    @Column(name = "contract_code")
    private String contractCode;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "rent_user")
    private String rentUser;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "other_phone_number")
    private String otherPhoneNumber;

    @Column(name = "username")
    private String username;

    @Column(name = "status")
    private String status;

    @Column(name = "room_code")
    private String roomCode;
}