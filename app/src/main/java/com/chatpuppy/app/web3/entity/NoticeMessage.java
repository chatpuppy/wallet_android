package com.chatpuppy.app.web3.entity;

public class NoticeMessage {

    public final boolean isShow;
    public final String noticeMsg;
    public final String noticeTitle;
    public final int count;

    public NoticeMessage(boolean isShow, String noticeMsg, String noticeTitle, int count) {
        this.isShow = isShow;
        this.noticeMsg = noticeMsg;
        this.noticeTitle = noticeTitle;
        this.count = count;
    }
}
