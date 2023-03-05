package com.mcdead.busycoder.cipherstestingapp.cipherstester;

public class CiphersTestDataStorage {
    private static CiphersTestDataStorage s_instance = null;

    private byte[] m_smallDataBytes = null;
    private byte[] m_largeDataBytes = null;

    public static CiphersTestDataStorage getInstance() {
        if (s_instance == null) {
            s_instance = new CiphersTestDataStorage();
        }

        return s_instance;
    }

    public boolean initialise(final byte[] smallDataBytes,
                        final byte[] largeDataBytes)
    {
        if (smallDataBytes.length <= 0 || largeDataBytes.length <= 0)
            return false;

        m_smallDataBytes = smallDataBytes;
        m_largeDataBytes = largeDataBytes;

        return true;
    }

    public boolean isInitialised() {
        if (m_smallDataBytes == null || m_largeDataBytes == null) return false;

        return (m_smallDataBytes.length > 0 && m_largeDataBytes.length > 0);
    }

    private CiphersTestDataStorage() {
        m_smallDataBytes = null;
        m_largeDataBytes = null;
    }

    private CiphersTestDataStorage(final byte[] smallDataBytes,
                                   final byte[] largeDataBytes)
    {
        m_smallDataBytes = smallDataBytes;
        m_largeDataBytes = largeDataBytes;
    }

    public byte[] getSmallDataBytes() {
        return m_smallDataBytes;
    }

    public byte[] getLargeDataBytes() {
        return m_largeDataBytes;
    }
}
