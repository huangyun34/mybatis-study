package com.my.mybatis.handles;

public interface DESHandle {

    boolean support(String flag);

    String encrypt(String content);

    String decrypt(String secretContent);
}
