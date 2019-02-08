package pl.piotrek.tenants.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;


    @ManyToMany
    @JoinTable(name="user_house",
            joinColumns = { @JoinColumn(name="fk_user") },
            inverseJoinColumns = { @JoinColumn(name = "fk_house") } )
    private Set<House> houses;

    @ManyToMany
    @JoinTable(name="user_housework",
            joinColumns = { @JoinColumn(name="fk_user") },
            inverseJoinColumns = { @JoinColumn(name = "fk_housework") } )
    private Set<Housework> houseworks = new HashSet<>();

    @OneToMany
    private Set<HouseworkRating> ratings = new HashSet<>();
}
