package adilet.entity;

import adilet.enums.RestType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_seq")
    @SequenceGenerator(name = "restaurant_seq", allocationSize = 1)
    private Long id;
    private String name;
    private String location;
    private RestType restType;
    private int numberOfEmployees;
    private int service;
    @OneToMany(mappedBy = "restaurant",
            cascade = CascadeType.ALL
    )
    private List<User> users;
    @OneToMany(mappedBy = "restaurant",
            cascade = CascadeType.ALL
    )
    private List<MenuItem> menuItems;

}