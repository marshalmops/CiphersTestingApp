package com.mcdead.busycoder.cipherstestingapp.cipherstester;

import android.content.res.AssetManager;

import org.greenrobot.eventbus.EventBus;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CiphersTestDataLoader implements Runnable {
    private AssetManager m_assetManager = null;
    private List<String> m_fileDataFilenameList = null;

    public CiphersTestDataLoader(
            AssetManager assetManager,
            final String... fileDataFilenameList)
    {
        m_assetManager = assetManager;
        m_fileDataFilenameList = new ArrayList<String>(Arrays.asList(fileDataFilenameList));
    }

    @Override
    public void run() {
        if (m_assetManager == null || m_fileDataFilenameList == null)
            return;
        if (m_fileDataFilenameList.isEmpty()) return;

        List<FileData> fileDataList = new ArrayList<>();

        for (final String filepath : m_fileDataFilenameList) {
            try (InputStream stream = m_assetManager.open(filepath)) {
                int size = stream.available();
                byte[] fileBytes = new byte[size];

                stream.read(fileBytes, 0, size);

                fileDataList.add(new FileData(filepath, fileBytes));

            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        CiphersTestDataLoadedEvent loadedEvent = new CiphersTestDataLoadedEvent(fileDataList);

        EventBus.getDefault().post(loadedEvent);
    }
}
