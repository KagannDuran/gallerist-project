package com.kagan.starter.exception;

import lombok.Getter;

@Getter
public enum MessageType {

    NO_RECORD_EXIST("1004","Kayıt bulunamadı"),
    TOKEN_IS_EXPORED("1005","Tokenın süresi bitmiştir"),
    USERNAME_NOT_FOUND("1006","Username bulunmadı"),
    USERNAME_OR_PASSWORD_INVALID("1007","Kullanıcı adı veya şifre hatalı"),
    ALREADY_EXISTS("1008","Bu kullanıcı adı daha önce alınmış"),
    REFRESH_TOKEN_NOT_FOUND("1009","Refresh token bulunamadı"),
    REFRESH_TOKEN_IS_EXPIRED("1010","Refresh tokenın süresi bitmiştir"),
    CUSTOMER_PRICE_IS_NOT_ENOUGH("1011","Müşterinin parası yeterli değil"),
    CAR_STATUS_IS_ALREADY_SALED("1012","Araba satılmış göründüğü için satılamaz"),
    GENERAL_EXCEPTION("9999","Genel bir hata oluştu");

    private String code;
    private String message;

    MessageType(String code,String message)
    {
        this.message = message;
        this.code = code;
    }
}
