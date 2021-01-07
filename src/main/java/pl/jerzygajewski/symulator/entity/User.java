package pl.jerzygajewski.symulator.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "sym")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String N;
    private long P;
    private long I;
    private int R;
    private double M;
    private int Ti;
    private int Tm;
    private int Ts;

}
