package cl.ucn.disc.dsm.pictwin.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Twin of Pic.
 *
 * @author Mack Díaz Vilches.
 */
@Entity
@Table(name = "twins")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Twin {
    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    /**
     * The dislike.
     */
    @Getter
    @Setter
    @Builder.Default
    private Boolean dislike = Boolean.FALSE;

    /**
     * The Pic.
     */
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @Getter
    private Pic my;

    /**
     * The Pic.
     */
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @Getter
    private Pic yours;

    /**
     * The Owner
     */
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @Getter
    @JsonBackReference
    private User owner;
}
