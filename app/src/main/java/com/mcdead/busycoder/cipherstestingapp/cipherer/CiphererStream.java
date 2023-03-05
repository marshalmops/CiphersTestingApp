package com.mcdead.busycoder.cipherstestingapp.cipherer;

import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

public abstract class CiphererStream extends CiphererBase {


    public CiphererStream(String algo,
                          Modes mode,
                          Padding padding,
                          Key key)
            throws NoSuchPaddingException, NoSuchAlgorithmException
    {
        super(algo, mode, padding, key);
    }
}
