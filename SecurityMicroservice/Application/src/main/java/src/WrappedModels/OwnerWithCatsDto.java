package src.WrappedModels;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OwnerWithCatsDto implements Dto {
    private OwnerMainInfoDto owner;
    private CatDto cat;

    public OwnerWithCatsDto() { }
}