package pl.piotrek.tenants.model.entity;

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

    @ManyToMany(mappedBy = "houses")
    private Set<User> inhabitants = new HashSet<>();

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL)
    private Set<Housework> houseworks;

    // helper method to add inhabitant

    public void addInhabitant(User user){
        inhabitants.add(user);
        user.getHouses().add(this);
    }
}
