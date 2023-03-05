package com.mcdead.busycoder.cipherstestingapp.cipherstester;

public class FileData {
    private String m_filename;
    private byte[] m_dataBytes;

    public FileData(final String filename,
                    final byte[] dataBytes)
    {
        m_filename = filename;
        m_dataBytes = dataBytes;
    }

    public String getFilename() {
        return m_filename;
    }

    public byte[] getDataBytes() {
        return m_dataBytes;
    }
}
