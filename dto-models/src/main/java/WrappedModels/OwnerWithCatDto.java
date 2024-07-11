package WrappedModels;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OwnerWithCatDto implements Serializable {
    private Integer userId;
    private Integer catId;
}