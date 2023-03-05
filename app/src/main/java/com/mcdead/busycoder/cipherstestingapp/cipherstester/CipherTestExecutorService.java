package com.mcdead.busycoder.cipherstestingapp.cipherstester;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.mcdead.busycoder.cipherstestingapp.cipherer.CiphererBase;
import com.mcdead.busycoder.cipherstestingapp.cipherer.CiphererFactory;
import com.mcdead.busycoder.cipherstestingapp.cipherer.Key;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CipherTestExecutorService extends Service {
    private static final String C_SMALL_DATA_STRING = "Hello there! This message is meant to be tested.";
    private static final String C_LARGE_DATA_FILENAME = "test.txt";

    private Thread m_cipherTestRunnerThread = null;
    private Thread m_cipherTestDataLoaderThread = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (m_cipherTestRunnerThread != null) {
            if (m_cipherTestRunnerThread.isAlive()) return START_NOT_STICKY;
        }

        CiphersTestDataStorage dataStorage = CiphersTestDataStorage.getInstance();

        if (!dataStorage.isInitialised())
            startCiphersTesterDataLoader();
        else
            startCiphersTester(dataStorage);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        if (m_cipherTestRunnerThread != null)
            if (m_cipherTestRunnerThread.isAlive())
                m_cipherTestRunnerThread.interrupt();
        if (m_cipherTestDataLoaderThread != null)
            if (m_cipherTestRunnerThread.isAlive())
                m_cipherTestDataLoaderThread.interrupt();

        EventBus.getDefault().unregister(this);

        super.onDestroy();
    }

    private void startCiphersTester(final CiphersTestDataStorage data) {
        Key key128 = KeyHexGenerator.generateKey(KeyHexGenerator.KeySize.KEY128, System.currentTimeMillis());
        Key key256 = KeyHexGenerator.generateKey(KeyHexGenerator.KeySize.KEY256, System.currentTimeMillis());
        List<CiphererBase> cipherers = new ArrayList<>();
        CiphererFactory ciphererFactory = CiphererFactory.getInstance();

        cipherers.add(ciphererFactory.generateCiphererAES(key128));
        cipherers.add(ciphererFactory.generateCiphererAES(key256));
        cipherers.add(ciphererFactory.generateCiphererBlowFish(key128));
        cipherers.add(ciphererFactory.generateCiphererBlowFish(key256));
        cipherers.add(ciphererFactory.generateCiphererChaCha20(key256));

        (m_cipherTestRunnerThread = new Thread(new CiphersTestRunner(data, cipherers))).start();
    }

    private void startCiphersTesterDataLoader() {
        (m_cipherTestDataLoaderThread = new Thread(
                new CiphersTestDataLoader(getAssets(), C_LARGE_DATA_FILENAME)))
                .start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCiphersTestDataPrepared(CiphersTestDataLoadedEvent event) {
        List<FileData> loadedData = event.getLoadedData();

        if (loadedData.size() <= 0) return;

        byte[] smallData = C_SMALL_DATA_STRING.getBytes(StandardCharsets.UTF_8);
        byte[] largeData = loadedData.get(0).getDataBytes();

        CiphersTestDataStorage instance = CiphersTestDataStorage.getInstance();

        if (!instance.initialise(smallData, largeData)) {
            Log.d(getClass().getName(), "Data storage initialisation failed!");

            return;
        }

        startCiphersTester(instance);
    }
}
