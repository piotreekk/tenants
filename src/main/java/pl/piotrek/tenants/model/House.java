package pl.piotrek.tenants.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String address;
    @ManyToMany(mappedBy = "houses")
    private Set<User> inhabitants;

}
