package ResultTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class OwnerOperationResult implements Serializable {
    private final String message;
    private final Boolean isSuccess;

    public OwnerOperationResult(String message, boolean isSuccess) {
        this.message = message;
        this.isSuccess = isSuccess;
    }

    public boolean getIsSuccess() {
        return isSuccess;
    }
}