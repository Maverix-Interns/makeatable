package com.maverix.makeatable.models;

import com.maverix.makeatable.enums.RoomType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private LocalDateTime dateTime;

    private Long seatNum;

    @Enumerated(EnumType.STRING)
    private RoomType typeRoom;
}
