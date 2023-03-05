package com.mcdead.busycoder.cipherstestingapp.cipherer;

import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

public abstract class CiphererBlowFish extends CiphererBlock {
    public CiphererBlowFish(final Modes mode,
                            final Padding padding,
                            final Key key)
            throws NoSuchPaddingException, NoSuchAlgorithmException
    {
        super(CipherAlgorithm.BlowFish.getAlgorithmString(), mode, padding, key);
    }

    @Override
    public CipherAlgorithm getAlgorithm() {
        return CipherAlgorithm.BlowFish;
    }
}
