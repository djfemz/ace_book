package africa.semicolon.acebook.models;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Business {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Embedded
    private AccountDetails accountDetails;
}
