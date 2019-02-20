package pl.piotrek.tenants.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.piotrek.tenants.model.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(of = "id")
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "email"
        })}
)
public class User extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String firstName;

    @NotBlank
    @Size(max = 40)
    private String lastName;

//    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(max = 100)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="user_house",
            joinColumns = { @JoinColumn(name="fk_user") },
            inverseJoinColumns = { @JoinColumn(name = "fk_house") } )
    private Set<House> houses = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="user_housework",
            joinColumns = { @JoinColumn(name="fk_user") },
            inverseJoinColumns = { @JoinColumn(name = "fk_housework") } )
    private Set<Housework> houseworks = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    private Set<HouseworkRating> ratings = new HashSet<>();

    // helper method to add house
    public void addHouse(House house){
        houses.add(house);
        house.getInhabitants().add(this);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
