package com.yao.module;

/**
 * Created by yaozb on 15-4-11.
 */
public class RandomMsg extends BaseMsg {
    public RandomMsg() {
        super();
        setType(MsgType.RANDOM);
    }
    private ReplyBody body;

    public ReplyBody getBody() {
        return body;
    }

    public void setBody(ReplyBody body) {
        this.body = body;
    }
}
