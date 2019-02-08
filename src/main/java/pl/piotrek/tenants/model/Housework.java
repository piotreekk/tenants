package pl.piotrek.tenants.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
public class Housework {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private LocalDate date;

    @ManyToMany(mappedBy = "houseworks")
    private Set<User> users;

    @OneToOne
    private House house;


}
