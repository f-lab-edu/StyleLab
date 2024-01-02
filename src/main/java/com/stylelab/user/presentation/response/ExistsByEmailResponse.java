package com.stylelab.user.presentation.response;

public record ExistsByEmailResponse(boolean duplicate) {

    public static ExistsByEmailResponse createResponse(boolean duplicate) {
        return new ExistsByEmailResponse(duplicate);
    }
}
