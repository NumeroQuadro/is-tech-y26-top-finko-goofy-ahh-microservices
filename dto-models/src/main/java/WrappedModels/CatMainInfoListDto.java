package WrappedModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CatMainInfoListDto {
    private Boolean isSuccess;
    private List<CatDto> dtos;
}
