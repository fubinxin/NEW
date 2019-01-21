package com.mahindra;

import java.io.IOException;

/**
 * Created by Administrator on 2018/4/16.
 */
public class SimpleException extends IOException {
    public SimpleException(String msg)
    {
        super(msg);
    }
}
