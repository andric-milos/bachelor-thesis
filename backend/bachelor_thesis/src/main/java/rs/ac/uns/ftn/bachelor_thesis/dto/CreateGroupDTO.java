package rs.ac.uns.ftn.bachelor_thesis.dto;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreateGroupDTO {
    private String name;
    private Set<String> playersEmails;
}
