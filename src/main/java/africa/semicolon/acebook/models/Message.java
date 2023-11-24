package africa.semicolon.acebook.models;


import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @OneToOne
    private Basic sender;
    @OneToOne
    private Basic recipient;

    private String text;
}
