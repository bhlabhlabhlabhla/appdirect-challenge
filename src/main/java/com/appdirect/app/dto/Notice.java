package com.appdirect.app.dto;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Notice {

    private NoticeType type;
    private String message;

    public Notice() {
    }

    public NoticeType getType() {
        return type;
    }

    public void setType(NoticeType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "type=" + type +
                ", message='" + message + '\'' +
                '}';
    }
}
