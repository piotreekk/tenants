package pl.piotrek.tenants.model.entity;

import lombok.Data;
import pl.piotrek.tenants.model.DateAudit;
import pl.piotrek.tenants.util.HouseworkStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Housework extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private LocalDate scheduledDate;

    @Enumerated(EnumType.STRING)
    private HouseworkStatus status;

    @ManyToMany(mappedBy = "houseworks")
    private Set<User> users = new HashSet<>();

    @ManyToOne
    @NotNull
    private House house;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "housework", fetch = FetchType.LAZY)
    private Set<HouseworkRating> ratings = new HashSet<>();

    // helper method to assign user to housework
    public void addUserToHousework(User user){
        users.add(user);
        user.getHouseworks().add(this);
    }

    public void addRateToHousework(HouseworkRating rating){
        ratings.add(rating);
        rating.setHousework(this);
    }

}
