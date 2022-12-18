package rs.ac.uns.ftn.bachelor_thesis.model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Manager extends User {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sports_facility_id")
    private SportsFacility sportsFacility;
}
