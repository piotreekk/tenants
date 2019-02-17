package pl.piotrek.tenants.entity;

import lombok.Data;
import pl.piotrek.tenants.util.HouseworkStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
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
    @Enumerated(EnumType.STRING)
    private HouseworkStatus status;

    @ManyToMany(mappedBy = "houseworks")
    private Set<User> users = new HashSet<>();

    @ManyToOne
    @NotNull
    private House house;

    // helper method to assign user to housework
    public void addUserToHousework(User user){
        users.add(user);
        user.getHouseworks().add(this);
    }
}
