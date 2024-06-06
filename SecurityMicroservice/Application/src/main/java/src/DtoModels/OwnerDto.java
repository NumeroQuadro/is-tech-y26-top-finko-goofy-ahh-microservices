package src.DtoModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import src.WrappedModels.CatDto;
import src.WrappedModels.Dto;
import src.WrappedModels.OwnerWithCatsDto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@AllArgsConstructor
public class OwnerDto implements Dto {
    private Integer id;
    private String name;
    private LocalDate birthDate;
}
