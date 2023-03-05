package com.mcdead.busycoder.cipherstestingapp.cipherer;

import com.mcdead.busycoder.cipherstestingapp.cipherstester.KeyHexGenerator;

import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public abstract class CiphererBase {
    protected Cipher m_cipher = null;
    protected Key m_key = null;

    public CiphererBase(final String algo,
                        final CiphererBlock.Modes mode,
                        final CiphererBlock.Padding padding,
                        final Key key)
            throws NoSuchPaddingException, NoSuchAlgorithmException
    {
        m_cipher = Cipher.getInstance(algo + "/" + mode + "/" + padding);
        m_key = key;
    }

    public abstract CipherAlgorithm getAlgorithm();
    public abstract int getIVSize();

    public KeyHexGenerator.KeySize getKeySize() {
        return m_key.getKeySize();
    }

    protected boolean init(final boolean isEncryption,
                           final AlgorithmParameters params)
    {
        byte[] rawKey = m_key.getKeyBytes();

        if (rawKey.length <= 0) return false;

        SecretKeySpec encodedKeySpec = new SecretKeySpec(rawKey, getAlgorithm().getAlgorithmString());

        try {
            if (isEncryption)
                m_cipher.init(Cipher.ENCRYPT_MODE, encodedKeySpec);
            else
                m_cipher.init(Cipher.ENCRYPT_MODE, encodedKeySpec, params);

        } catch (Throwable e) {
            e.printStackTrace();

            return false;
        }

        return true;
    }

    public byte[] encrypt(byte[] dataToEncrypt)
    {
        if (dataToEncrypt.length <= 0)
            return null;

        if (!init(true,  null))
            return null;

        byte[] cipheredBytes = null;

        try {
            byte[] cipheredBytesBuffer = m_cipher.doFinal(dataToEncrypt);
            byte[] ivBytes = m_cipher.getIV();

            if (cipheredBytesBuffer == null)
                return null;
            if (cipheredBytesBuffer.length < dataToEncrypt.length)
                return null;

            cipheredBytes = new byte[ivBytes.length + cipheredBytesBuffer.length];

            System.arraycopy(ivBytes, 0, cipheredBytes, 0, ivBytes.length);
            System.arraycopy(cipheredBytesBuffer, 0, cipheredBytes, ivBytes.length, cipheredBytesBuffer.length);

        } catch (Throwable e) {
            e.printStackTrace();

            return null;
        }

        return cipheredBytes;
    }

    public byte[] decrypt(byte[] dataToDecrypt) {
        if (dataToDecrypt.length <= 0)
            return null;

        int ivBytesCount = getIVSize();

        byte[] ivBytesReceived = Arrays.copyOfRange(dataToDecrypt, 0, ivBytesCount);
        byte[] bytesToDecipher = Arrays.copyOfRange(dataToDecrypt, ivBytesCount, dataToDecrypt.length);

        byte[] decipheredBytes = null;

        try {
            AlgorithmParameters params = AlgorithmParameters.getInstance(getAlgorithm().getAlgorithmString());

            params.init(new IvParameterSpec(ivBytesReceived));

            if (!init(false,  params))
                return null;

            decipheredBytes = m_cipher.doFinal(bytesToDecipher);

        } catch (Throwable e) {
            e.printStackTrace();

            return null;
        }

        return decipheredBytes;
    }

    public enum Modes {
        CTR("CTR"), CBC("CBC"), NONE("NONE");

        private String m_modeAsString;

        private Modes(final String modeAsString) {
            m_modeAsString = modeAsString;
        }

        public String getModeAsString() {
            return m_modeAsString;
        }
    }

    public enum Padding {
        NoPadding("NoPadding"), PKCS5Padding("PKCS5Padding");

        private String m_paddingAsString;

        private Padding(final String paddingAsString) {
            m_paddingAsString = paddingAsString;
        }

        public String getPaddingAsString() {
            return m_paddingAsString;
        }
    }
}
