package io.pilot.system.enums;

public enum Error {

    FILE_UPLOADED_SUCCESSFULLY(1000, "File %s has be uploaded successfully"),
    FILE_PARSING_FAILED(1001, "Failed to parse CSV file"),
    TED_TALK_NOT_FOUND(1002, "TedTalk not found against the Id %s"),
    INVALID_FILE_FORMAT(1003, "File format is not CSV"),
    GENERAL_EXCEPTION(1004, "An exception occurred while processing batch file"),
    REQUEST_BODY_MISSING_AUTHOR(1005, "Author name is missing in the request body"),
    REQUEST_BODY_MISSING_TITLE(1006, "Title is missing in the request body"),
    REQUEST_BODY_MISSING_DATE(1007, "Date is missing in the request body"),
    REQUEST_BODY_MISSING_VIEWS(1008, "Views is missing in the request body"),
    REQUEST_BODY_MISSING_LIKES(1009, "Likes is missing in the request body"),
    REQUEST_BODY_MISSING_LINK(1010, "Link is missing in the request body"),
    REQUEST_BODY_CONTAINS_DUPLICATE_TITLE(1011, "Title can not be duplicate!"),
    REQUEST_BODY_CONTAINS_DUPLICATE_LINK(1012, "Link can not be duplicate!"),
    ;

    private final int code;
    private final String msg;

    Error(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }
}
