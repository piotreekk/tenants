package pl.piotrek.tenants.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(of = "id")
@Entity
public class HouseworkRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rate;

    private String comment;

    @ManyToOne
    @NotNull
    private Housework housework;

    @ManyToOne
    @NotNull
    private User user;


}
