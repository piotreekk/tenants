package pl.piotrek.tenants.model.entity;

import lombok.Data;
import pl.piotrek.tenants.model.RoleName;

import javax.persistence.*;

@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 60)
    private RoleName name;

}
