package Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "owners")
public class Owner {
    @Id
    @Column(name = "user_id", nullable = false)
    private Integer id;
    @Column(name = "owner_name", nullable = false, unique = true)
    private String name;
    @Column(name = "owner_birthday", nullable = false)
    private LocalDate birthDate;

    public Owner(Integer id, String name, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }
}
