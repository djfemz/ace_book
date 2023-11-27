package africa.semicolon.acebook.models;


import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @OneToOne
    private Account sender;
    @OneToOne
    private Account recipient;

    private String text;
}
