package pl.piotrek.tenants.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(of = "id")
@Entity
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String address;
    @ManyToMany(mappedBy = "houses", fetch = FetchType.EAGER)
    private Set<User> inhabitants = new HashSet<>();

}
