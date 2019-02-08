package pl.piotrek.tenants.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class HouseworkRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Housework housework;

    @ManyToOne
    private User user;
}
