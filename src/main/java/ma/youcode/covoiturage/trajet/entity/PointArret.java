package ma.youcode.covoiturage.trajet.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "points_arret")
@Getter
@Setter
@NoArgsConstructor
public class PointArret {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trajet_id", nullable = false)
    private Trajet trajet;

    @Column(nullable = false)
    private String ville;

    @Column(nullable = false)
    private Integer ordre;

    @Column(name = "heure_passage", nullable = false)
    private LocalDateTime heurePassage;

    @Column(name = "prix_depuis_depart", nullable = false)
    private BigDecimal prixDepuisDepart;
}
