package com.newtonacademic.newtontutors.login;

/**
 * @author laiyongyang
 * @date 2020-05-01
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class LoginResult {
    /**
     * The Result Type.ResponseResult.ResponseCode
     */
    int resultCode;

    /**
     * The Result type message.
     */
    String resultTypeMessage;

    static LoginResult newResult(int resultCode, String resultTypeMessage) {
        LoginResult result = new LoginResult();
        result.resultCode = resultCode;
        result.resultTypeMessage = resultTypeMessage;
        return result;
    }
}
