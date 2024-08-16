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
@Entity(name = "house")
@Table(name = "house")
public class House {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "house_code")
    private String houseCode;

    @Column(name = "house_position", length = Integer.MAX_VALUE)
    private String housePosition;

    @Column(name = "room_number")
    private Integer roomNumber;

    @Column(name = "room_available")
    private Integer roomAvailable;
}