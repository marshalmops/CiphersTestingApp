package com.mcdead.busycoder.cipherstestingapp.cipherer;

import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

public abstract class CiphererBlock extends CiphererBase {

    public CiphererBlock(final String algo,
                         final Modes mode,
                         final Padding padding,
                         final Key key)
            throws NoSuchPaddingException, NoSuchAlgorithmException
    {
        super(algo, mode, padding, key);
    }

    @Override
    public int getIVSize() {
        return m_cipher.getBlockSize();
    }
}
