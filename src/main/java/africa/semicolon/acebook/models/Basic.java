package africa.semicolon.acebook.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Getter
@Setter
public class Basic {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    @Embedded
    private AccountDetails accountDetails;
    @ManyToOne
    private Basic friends;
}
