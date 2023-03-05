package com.mcdead.busycoder.cipherstestingapp.cipherer;

import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

public class CiphererBlowFishCTR extends CiphererBlowFish {

    public CiphererBlowFishCTR(final Key key)
            throws NoSuchPaddingException, NoSuchAlgorithmException
    {
        super(Modes.CTR, Padding.NoPadding, key);
    }
}
