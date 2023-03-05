package com.mcdead.busycoder.cipherstestingapp.cipherer;

import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

public abstract class CiphererAES extends CiphererBlock {
    public CiphererAES(final Modes mode,
                       final Padding padding,
                       final Key key)
            throws NoSuchPaddingException, NoSuchAlgorithmException
    {
        super(CipherAlgorithm.AES.getAlgorithmString(), mode, padding, key);
    }

    @Override
    public CipherAlgorithm getAlgorithm() {
        return CipherAlgorithm.AES;
    }
}
