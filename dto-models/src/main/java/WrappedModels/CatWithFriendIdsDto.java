package WrappedModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CatWithFriendIdsDto {
    private Integer targetCatId;
    private Integer friendCatId;
}
