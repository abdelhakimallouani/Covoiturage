package ma.youcode.covoiturage.conducteur.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "conducteurs")
@Getter
@Setter
@NoArgsConstructor
public class Conducteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telephone;

    @Column(name = "numero_permis", nullable = false)
    private String numeroPermis;
}
