package com.example.EasyWalletApplication.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MonnifyResponse {
    private boolean requestSuccessful;
    private Response responseBody;

}
