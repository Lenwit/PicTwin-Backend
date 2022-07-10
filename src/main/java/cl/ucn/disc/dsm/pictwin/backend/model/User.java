package cl.ucn.disc.dsm.pictwin.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * The User of PicTwin.
 *
 * @author Mack Díaz Vilches.
 */
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class User {
    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    /**
     * The Email.
     */
    @Getter
    @NonNull
    @NotBlank
    @Column(unique = true)
    private String email;

    /**
     * The Password.
     */
    @Getter
    @Setter
    private String password;

    /**
     * The number of strikes.
     */
    @Getter
    private Integer strikes;

    /**
     * The State.
     */
    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Getter
    @Setter
    private State state = State.ACTIVE;

    /**
     * The Twins.
     */
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    @Builder.Default
    @Getter
    @JsonManagedReference
    private List<Twin> twins = new ArrayList<>();

    /**
     * Increment the strikes.
     *
     * @return the number of strikes.
     */
    public Integer incrementStrickes() {
        this.strikes++;
        return this.strikes;
    }

    /**
     * Insert a Twin in the list.
     *
     * @param twin to add.
     */
    public void add(final Twin twin){
        this.twins.add(twin);
    }
}
