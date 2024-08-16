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
@Entity(name = "room")
@Table(name = "room")
public class Room {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "room_code")
    private String roomCode;

    @Column(name = "name")
    private String name;

    @Column(name = "room_position")
    private String roomPosition;

    @Column(name = "house_code")
    private String houseCode;

    @Column(name = "room_status")
    private String roomStatus;

    @Column(name = "room_price")
    private Long roomPrice;
}