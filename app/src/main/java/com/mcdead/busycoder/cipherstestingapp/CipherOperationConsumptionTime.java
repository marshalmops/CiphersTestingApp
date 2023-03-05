package com.mcdead.busycoder.cipherstestingapp;

public class CipherOperationConsumptionTime {
    private long m_smallDataCipheringTime;
    private long m_smallDataDecipheringTime;
    private long m_largeDataCipheringTime;
    private long m_largeDataDecipheringTime;

    public CipherOperationConsumptionTime(
            final long smallDataCipheringTime,
            final long smallDataDecipheringTime,
            final long largeDataCipheringTime,
            final long largeDataDecipheringTime)
    {
        m_smallDataCipheringTime = smallDataCipheringTime;
        m_smallDataDecipheringTime = smallDataDecipheringTime;
        m_largeDataCipheringTime = largeDataCipheringTime;
        m_largeDataDecipheringTime = largeDataDecipheringTime;
    }

    public long getSmallDataCipheringTime() {
        return m_smallDataCipheringTime;
    }

    public long getSmallDataDecipheringTime() {
        return m_smallDataDecipheringTime;
    }

    public long getLargeDataCipheringTime() {
        return m_largeDataCipheringTime;
    }

    public long getLargeDataDecipheringTime() {
        return m_largeDataDecipheringTime;
    }
}
