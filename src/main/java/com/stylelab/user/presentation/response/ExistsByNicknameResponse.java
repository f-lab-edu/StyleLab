package com.stylelab.user.presentation.response;

public record ExistsByNicknameResponse(boolean duplicate) {

    public static ExistsByNicknameResponse createResponse(boolean duplicate) {
        return new ExistsByNicknameResponse(duplicate);
    }
}
