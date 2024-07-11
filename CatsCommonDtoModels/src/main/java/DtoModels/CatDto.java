package DtoModels;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CatDto implements Dto {
    private Integer id;
    private String name;
    private LocalDate birthDate;
    private String breed;
    private OwnerDto owner;
    private CatColor color;
    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
