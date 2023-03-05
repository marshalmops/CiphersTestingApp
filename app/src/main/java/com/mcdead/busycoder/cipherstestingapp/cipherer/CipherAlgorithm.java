package com.mcdead.busycoder.cipherstestingapp.cipherer;

public enum CipherAlgorithm {
    AES("AES"),
    BlowFish("BLOWFISH"),
    ChaCha20("ChaCha20");

    private String m_algorithmString = null;

    private CipherAlgorithm(final String algorithmString) {
        m_algorithmString = algorithmString;
    }

    public String getAlgorithmString() {
        return m_algorithmString;
    }
}
