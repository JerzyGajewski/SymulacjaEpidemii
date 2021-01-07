package pl.jerzygajewski.symulator.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "sym")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty(message = "Name (N) can't be empty")
    private String N;
    @Min(value = 0, message = "Population size (P) must be greater than or equal to 0")
    private long P;
    @Min(value = 0, message = "Number of sick (I) people must be greater than or equal to 0")
    private long I;
    @Min(value = 0, message = "Minimum value of R is 0%")
    @Max(value = 100, message = "R can't be greater than 100%")
    private int R;
    @Min(value = 0, message = "Mortality rate (M) must be greater than or equal to 0")
    private double M;
    @Min(value = 0, message = "Days after people cures (Ti) must be greater than or equal to 0")
    private int Ti;
    @Min(value = 0, message = "Days after people dies (Tm) must be greater than or equal to 0")
    private int Tm;
    @Min(value = 0, message = "Simulation days (Ts) must be greater than or equal to 0")
    private int Ts;

}
