package ma.youcode.covoiturage.trajet.service;

import ma.youcode.covoiturage.common.exception.BusinessRuleException;
import ma.youcode.covoiturage.common.exception.ResourceNotFoundException;

import ma.youcode.covoiturage.conducteur.entity.Conducteur;
import ma.youcode.covoiturage.conducteur.repository.ConducteurRepository;

import ma.youcode.covoiturage.vehicule.entity.Vehicule;
import ma.youcode.covoiturage.vehicule.repository.VehiculeRepository;

import ma.youcode.covoiturage.trajet.dto.request.CreateTrajetRequest;
import ma.youcode.covoiturage.trajet.dto.request.CreatePointArretRequest;
import ma.youcode.covoiturage.trajet.entity.PointArret;
import ma.youcode.covoiturage.trajet.entity.Trajet;
import ma.youcode.covoiturage.trajet.enums.StatutTrajet;
import ma.youcode.covoiturage.trajet.mapper.TrajetMapper;
import ma.youcode.covoiturage.trajet.repository.TrajetRepository;
import ma.youcode.covoiturage.trajet.service.impl.TrajetServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrajetServiceImplTest {

    @Mock
    private TrajetRepository trajetRepository;

    @Mock
    private ConducteurRepository conducteurRepository;

    @Mock
    private VehiculeRepository vehiculeRepository;

    @Mock
    private TrajetMapper trajetMapper;

    @InjectMocks
    private TrajetServiceImpl trajetService;

    private Conducteur conducteur;
    private Vehicule vehicule;
    private CreateTrajetRequest request;

    private LocalDateTime dateDepart;

    @BeforeEach
    void setUp() {

        dateDepart = LocalDateTime.now().plusDays(5);

        conducteur = new Conducteur();
        conducteur.setId(1L);

        vehicule = new Vehicule();
        vehicule.setId(1L);
        vehicule.setCapacite(3);
        vehicule.setConducteur(conducteur);

        CreatePointArretRequest pointOujda = new CreatePointArretRequest("Oujda", 0, dateDepart, BigDecimal.ZERO);

        CreatePointArretRequest pointBerkane = new CreatePointArretRequest("Berkane", 1, dateDepart.plusHours(1),
                new BigDecimal("30.00"));

        CreatePointArretRequest pointNador = new CreatePointArretRequest("Nador", 2, dateDepart.plusHours(2),
                new BigDecimal("60.00"));

        request = new CreateTrajetRequest(1L, 1L, "Oujda", "Nador", dateDepart, 3,
                List.of(pointOujda, pointBerkane, pointNador));
    }

    @Test
    void publierTrajet_shouldPublishTrajet_whenRequestIsValid() {

        // Given
        when(conducteurRepository.findById(1L)).thenReturn(Optional.of(conducteur));

        when(vehiculeRepository.findById(1L)).thenReturn(Optional.of(vehicule));

        when(trajetRepository.countPlanningConflicts(eq(1L), eq(1L), any(LocalDateTime.class), any(LocalDateTime.class),
                anySet())).thenReturn(0L);

        Trajet trajet = new Trajet();

        when(trajetMapper.toEntity(request)).thenReturn(trajet);

        when(trajetMapper.toPointArret(any(CreatePointArretRequest.class))).thenAnswer(invocation -> {
            CreatePointArretRequest pointRequest = invocation.getArgument(0);
            PointArret point = new PointArret();
            point.setVille(pointRequest.ville());
            point.setOrdre(pointRequest.ordre());
            point.setHeurePassage(pointRequest.heurePassage());
            point.setPrixDepuisDepart(pointRequest.prixDepuisDepart());
            return point;
        });

        when(trajetRepository.save(any(Trajet.class))).thenAnswer(invocation -> {
            Trajet saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        // When
        trajetService.publierTrajet(request);

        // Then
        verify(conducteurRepository).findById(1L);
        verify(vehiculeRepository).findById(1L);

        verify(trajetRepository).countPlanningConflicts(eq(1L), eq(1L), any(LocalDateTime.class),
                any(LocalDateTime.class), anySet());

        verify(trajetRepository).save(any(Trajet.class));
    }

    @Test
    void publierTrajet_shouldThrowException_whenConducteurNotFound() {

        // Given
        when(conducteurRepository.findById(1L))
                .thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> trajetService.publierTrajet(request))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(trajetRepository, never()).save(any());
        verify(vehiculeRepository, never()).findById(any());
    }

    @Test
    void publierTrajet_shouldThrowException_whenVehiculeNotFound() {

        // Given
        when(conducteurRepository.findById(1L))
                .thenReturn(Optional.of(conducteur));

        when(vehiculeRepository.findById(1L))
                .thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> trajetService.publierTrajet(request))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(trajetRepository, never()).save(any());
    }

}
