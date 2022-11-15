package rs.ac.uns.ftn.bachelor_thesis.model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "sports_facility_comments")
public class SportsFacilityComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private int rating; // 1-5 value (stars).
    private String comment;

    @ManyToOne
    @JoinColumn(name = "sportsFacility_id")
    private SportsFacility sportsFacility;
}
