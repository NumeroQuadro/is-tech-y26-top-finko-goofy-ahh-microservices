package Models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "owners_with_cats")
public class OwnerWithCats {
    @EmbeddedId
    private OwnerWithCatsEmbedded id;

    public OwnerWithCats(Integer userId, Integer catId) {
        this.id = new OwnerWithCatsEmbedded(userId, catId);
    }
}
