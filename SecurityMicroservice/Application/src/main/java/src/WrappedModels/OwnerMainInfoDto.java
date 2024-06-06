package src.WrappedModels;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
public class OwnerMainInfoDto implements Dto {
    private String name;
    private LocalDate birthDate;
}
