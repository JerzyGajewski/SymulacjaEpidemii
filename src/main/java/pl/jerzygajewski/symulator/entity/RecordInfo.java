package pl.jerzygajewski.symulator.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "info")
public class RecordInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long Pi;
    private long Pv;
    private long Pm;
    private long Pr;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
