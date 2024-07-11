package WrappedModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CatMainInfoDto implements Serializable {
    private String name;
    private LocalDate birthDate;
    private String breed;
    private CatColor color;
}