package com.tracy.competition.domain.enums;

/**
 * 成功信息
 *
 * @author Tracy
 * @date 2020/11/4 17:09
 */
public enum SuccessEnum {

    /**
     * 成功信息
     */
    S_LOGIN_SUCCESS("1", "登录成功"),
    S_LOGINED("1", "用户已登录，无需重复登录");


    /**
     * 成功代码
     */
    private String successCode;
    /**
     * 成功信息
     */
    private String successMsg;

    SuccessEnum(String successCode, String successMsg) {
        this.successCode = successCode;
        this.successMsg = successMsg;
    }

    public String getSuccessCode() {
        return successCode;
    }

    public String getSuccessMsg() {
        return successMsg;
    }
}
