package com.mcdead.busycoder.cipherstestingapp.cipherer;

import com.mcdead.busycoder.cipherstestingapp.cipherstester.KeyHexGenerator;

public class Key {
    private KeyHexGenerator.KeySize m_size;
    private byte[] m_bytes;

    public Key(final byte[] bytes)
    {
        m_size = KeyHexGenerator.KeySize.getKeySizeByInt(bytes.length * Byte.SIZE);
        m_bytes = bytes;
    }

    public byte[] getKeyBytes() {
        return m_bytes;
    }

    public KeyHexGenerator.KeySize getKeySize() {
        return m_size;
    }
}
