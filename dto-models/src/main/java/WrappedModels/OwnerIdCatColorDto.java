package WrappedModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OwnerIdCatColorDto {
    Integer ownerId;
    CatColor catColor;
}
