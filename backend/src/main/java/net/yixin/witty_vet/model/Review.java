package net.yixin.witty_vet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int stars;
    private String feedback;

    @ManyToOne
    @JoinColumn(name = "veterinarian_id")
    private User veterinarian;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;

    public void removeRelationshipWithAllUsers() {
        Optional.ofNullable(veterinarian).ifPresent(vet -> vet.getReviews().remove(this));
        Optional.ofNullable(patient).ifPresent(patient -> patient.getReviews().remove(this));
    }
}
