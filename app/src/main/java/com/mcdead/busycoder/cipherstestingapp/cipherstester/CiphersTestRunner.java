package com.mcdead.busycoder.cipherstestingapp.cipherstester;

import android.util.Log;
import android.util.Pair;

import com.mcdead.busycoder.cipherstestingapp.CipherOperationConsumptionTime;
import com.mcdead.busycoder.cipherstestingapp.CiphersTestCompletedEvent;
import com.mcdead.busycoder.cipherstestingapp.cipherer.CipherAlgorithm;
import com.mcdead.busycoder.cipherstestingapp.cipherer.CiphererBase;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

public class CiphersTestRunner implements Runnable {
    private CiphersTestDataStorage m_data = null;
    private List<CiphererBase> m_cipherers = null;

    public CiphersTestRunner(final CiphersTestDataStorage data,
                             final List<CiphererBase> cipherers)
    {
        m_data = data;
        m_cipherers = cipherers;
    }

    @Override
    public void run() {
        if (m_data == null || m_cipherers == null) return;
        if (!m_data.isInitialised() || m_cipherers.isEmpty()) return;

        HashMap<Pair<CipherAlgorithm, KeyHexGenerator.KeySize>, CipherOperationConsumptionTime> results = new HashMap<>();
        boolean errorOccuredFlag = false;

        for (final CiphererBase cipherer : m_cipherers) {
            LongWrapper endSmallBytesCipherTimeConsumed = new LongWrapper();
            byte[] cipheredSmallBytes = null;

            if ((cipheredSmallBytes = testCipher(cipherer, true, m_data.getSmallDataBytes(), endSmallBytesCipherTimeConsumed)).length <= 0) {
                errorOccuredFlag = true;

                break;
            }

            LongWrapper endSmallBytesDecipherTimeConsumed = new LongWrapper(0);
            byte[] depheredSmallBytes = null;

            if ((depheredSmallBytes = testCipher(cipherer, false, cipheredSmallBytes, endSmallBytesDecipherTimeConsumed)).length <= 0) {
                errorOccuredFlag = true;

                break;
            }

            LongWrapper endLargeBytesCipherTimeConsumed = new LongWrapper(0);
            byte[] cipheredLargeBytes = null;

            if ((cipheredLargeBytes = testCipher(cipherer, true, m_data.getLargeDataBytes(), endLargeBytesCipherTimeConsumed)).length <= 0) {
                errorOccuredFlag = true;

                break;
            }

            LongWrapper endLargeBytesDecipherTimeConsumed = new LongWrapper(0);
            byte[] depheredLargeBytes = null;

            if ((depheredLargeBytes = testCipher(cipherer, false, cipheredLargeBytes, endLargeBytesDecipherTimeConsumed)).length <= 0) {
                errorOccuredFlag = true;

                break;
            }

            results.put(new Pair<>(cipherer.getAlgorithm(), cipherer.getKeySize()),
                    new CipherOperationConsumptionTime(
                            endSmallBytesCipherTimeConsumed.getValue(),
                            endSmallBytesDecipherTimeConsumed.getValue(),
                            endLargeBytesCipherTimeConsumed.getValue(),
                            endLargeBytesDecipherTimeConsumed.getValue()));


        }

        if (errorOccuredFlag) {
            Log.d(getClass().getName(), "Ciphering operation execution has been failed!");

            return;
        }

        EventBus.getDefault().post(new CiphersTestCompletedEvent(results));
    }

    private byte[] testCipher(final CiphererBase cipherer,
                               final boolean isEncrypt,
                               final byte[] bytes,
                               LongWrapper timeConsumed)
    {
        if (bytes.length <= 0) return null;

        byte[] smallBytes = null;
        long startBytesCipherTime = System.currentTimeMillis();

        if (isEncrypt)
            smallBytes = cipherer.encrypt(bytes);
        else
            smallBytes = cipherer.decrypt(bytes);

        timeConsumed.setValue(System.currentTimeMillis() - startBytesCipherTime);

        if (smallBytes == null) return null;
        if (smallBytes.length <= 0) return null;

        return smallBytes;
    }

    private static class LongWrapper {
        private long m_value;

        public LongWrapper(final long  value) {
            m_value = value;
        }

        public LongWrapper() {
            m_value = 0;
        }

        public void setValue(final long value) {
            m_value = value;
        }

        public long getValue() {
            return m_value;
        }
    }
}
