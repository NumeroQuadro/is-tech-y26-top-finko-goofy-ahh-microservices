package WrappedModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OwnerMainInfoDto implements Serializable {
    private Integer id;
    private String name;
    private LocalDate birthDate;
}
