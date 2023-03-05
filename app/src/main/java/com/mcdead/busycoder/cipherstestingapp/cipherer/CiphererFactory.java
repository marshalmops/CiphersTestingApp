package com.mcdead.busycoder.cipherstestingapp.cipherer;

public class CiphererFactory {
    private static CiphererFactory s_instance = null;

    private CiphererFactory() {

    }

    public static CiphererFactory getInstance() {
        if (s_instance == null)
            s_instance = new CiphererFactory();

        return s_instance;
    }

    public CiphererAES generateCiphererAES(final Key key) {
        CiphererAES ciphererAES = null;

        try {
            ciphererAES = new CiphererAESCTR(key);

        } catch (Throwable e) {
            e.printStackTrace();

            return null;
        }

        return ciphererAES;
    }

    public CiphererBlowFish generateCiphererBlowFish(final Key key) {
        CiphererBlowFishCTR ciphererBlowFish = null;

        try {
            ciphererBlowFish = new CiphererBlowFishCTR(key);

        } catch (Throwable e) {
            e.printStackTrace();

            return null;
        }

        return ciphererBlowFish;
    }

    public CiphererChaCha20 generateCiphererChaCha20(final Key key) {
        CiphererChaCha20 ciphererChaCha20 = null;

        try {
            ciphererChaCha20 = new CiphererChaCha20(key);

        } catch (Throwable e) {
            e.printStackTrace();

            return null;
        }

        return ciphererChaCha20;
    }
}
