package pl.piotrek.tenants.model.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class HouseworkRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @NotNull
    private Housework housework;

    @ManyToOne
    @NotNull
    private User user;
}
