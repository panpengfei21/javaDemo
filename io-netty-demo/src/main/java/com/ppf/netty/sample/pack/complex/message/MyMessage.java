package com.ppf.netty.sample.pack.complex.message;

/**
 * 自己定义的要传输的message
 */
public class MyMessage {
    private Integer length;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "MyMessage{" +
                "length=" + length +
                ", content='" + content + '\'' +
                '}';
    }
}
