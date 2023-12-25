package com.stylelab.user.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExistsByNicknameResponse {

    private boolean duplicate;
}
