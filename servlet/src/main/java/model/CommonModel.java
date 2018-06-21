package model;

import constant.ConstantUtil;

public class CommonModel {
    //code 0 请求成功,有数据返回 1：借口请求成功，无数据返回
    private int code;
    //msg
    private String msg;
    //可以单个对象或List
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    public void setSuccess(){
        setCode(ConstantUtil.CODE_SUCCESS);
        setMsg(ConstantUtil.MSG_SUCCESS);
    }
    public void setFail(){
        setCode(ConstantUtil.CODE_FAIL);
        setMsg(ConstantUtil.MSG_FAIL);
    }
}
