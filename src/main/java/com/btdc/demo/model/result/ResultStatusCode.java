package com.btdc.demo.model.result;


public enum ResultStatusCode {
    OK(0,"OK"),
    SYSTEM_ERR(30001,"System error");

    private int errcode;
    private String errmsg;

    ResultStatusCode(Integer errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
