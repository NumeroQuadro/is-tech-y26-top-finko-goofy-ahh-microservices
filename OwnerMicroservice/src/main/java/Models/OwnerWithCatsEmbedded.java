package Models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Embeddable
@NoArgsConstructor
public class OwnerWithCatsEmbedded implements Serializable {
    protected Integer userId;
    protected Integer catId;

    public OwnerWithCatsEmbedded(Integer userId, Integer catId) {
        this.userId = userId;
        this.catId = catId;
    }
}
