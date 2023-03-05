package com.mcdead.busycoder.cipherstestingapp.cipherstester;

import java.util.List;

public class CiphersTestDataLoadedEvent {
    private List<FileData> m_loadedData;

    public CiphersTestDataLoadedEvent(List<FileData> loadedData) {
        m_loadedData = loadedData;
    }

    public List<FileData> getLoadedData() {
        return m_loadedData;
    }
}
