package com.mcdead.busycoder.cipherstestingapp.cipherer;

import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

public class CiphererAESCTR extends CiphererAES {

    public CiphererAESCTR(final Key key)
            throws NoSuchPaddingException, NoSuchAlgorithmException
    {
        super(Modes.CTR, Padding.NoPadding, key);
    }
}
