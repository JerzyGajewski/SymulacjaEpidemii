package pl.jerzygajewski.symulator.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "sym")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String N;
    private Long P;
    private Long I;
    private Integer R;
    private Integer M;
    private Integer Ti;
    private Integer Tm;
    private Integer Ts;

}
