package com.mcdead.busycoder.cipherstestingapp.cipherer;

import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

public class CiphererChaCha20 extends CiphererStream {
    private static final int C_NONCE_BYTE_COUNT = 12;

    public CiphererChaCha20(Key key)
            throws NoSuchPaddingException, NoSuchAlgorithmException
    {
        super(CipherAlgorithm.ChaCha20.getAlgorithmString(), Modes.NONE, Padding.NoPadding, key);
    }

    @Override
    public CipherAlgorithm getAlgorithm() {
        return CipherAlgorithm.ChaCha20;
    }

    @Override
    public int getIVSize() {
        return C_NONCE_BYTE_COUNT;
    }
}
