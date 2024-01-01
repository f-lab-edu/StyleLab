package com.stylelab.user.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExistsByEmailResponse {

    private boolean duplicate;

    public static ExistsByEmailResponse createResponse(boolean duplicate) {
        return new ExistsByEmailResponse(duplicate);
    }
}
