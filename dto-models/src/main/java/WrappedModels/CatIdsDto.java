package WrappedModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CatIdsDto implements Serializable {
    private Boolean isSuccess;
    private List<Integer> catIds;
}
