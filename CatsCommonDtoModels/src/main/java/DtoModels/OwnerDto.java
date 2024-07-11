package DtoModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@AllArgsConstructor
public class OwnerDto implements Dto {
    private String name;
    private LocalDate birthDate;
    private Set<CatDto> cats = new HashSet<>();
    private final Set<OwnerWithCatsDto> ownerWithCats = new HashSet<>();
    private final UserDto user;
}
