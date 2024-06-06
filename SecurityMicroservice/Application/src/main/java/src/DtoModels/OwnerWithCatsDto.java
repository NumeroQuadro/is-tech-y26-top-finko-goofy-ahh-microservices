package src.DtoModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import src.WrappedModels.CatDto;
import src.WrappedModels.Dto;
import src.WrappedModels.OwnerMainInfoDto;

@Getter
@AllArgsConstructor
public class OwnerWithCatsDto implements Dto {
    private OwnerMainInfoDto owner;
    private CatDto cat;

    public OwnerWithCatsDto() { }
}