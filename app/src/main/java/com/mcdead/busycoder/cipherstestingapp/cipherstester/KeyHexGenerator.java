package com.mcdead.busycoder.cipherstestingapp.cipherstester;

import com.mcdead.busycoder.cipherstestingapp.cipherer.Key;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.EnumSet;
import java.util.HashMap;

public class KeyHexGenerator {
    public static Key generateKey(final KeySize size,
                                  final long seed)
    {
        HashAlgorithm algorithm = HashAlgorithm.getAlgorithmBySize(size.getBitCount());
        Key resultKey = null;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm.getName());
            byte[] seedBytes = ByteBuffer.allocate(Long.BYTES).putLong(seed).array();
            Key newKey = new Key(messageDigest.digest(seedBytes));

            resultKey = newKey;

        } catch (Throwable e) {
            e.printStackTrace();

            return null;
        }

        return resultKey;
    }

    public enum KeySize {
        KEY128(128), KEY256(256);

        private int m_size;

        private KeySize(final int size) {
            m_size = size;
        }

        public int getBitCount() {
            return m_size;
        }

        public static KeySize getKeySizeByInt(final int size) {
            KeySize[] allKeySizes = KeySize.class.getEnumConstants();

            for (final KeySize keySize : allKeySizes) {
                if (keySize.getBitCount() == size)
                    return keySize;
            }

            return null;
        }
    }

    public enum HashAlgorithm {
        SHA256("SHA256"), MD5("MD5"), NONE("NONE");

        private static final HashMap<Integer, HashAlgorithm> s_sizeAlgoHashMap;

        private String m_name;

        static {
            s_sizeAlgoHashMap = new HashMap<Integer, HashAlgorithm>() {
                {
                    put(128, MD5);
                    put(256, SHA256);
                }
            };
        }

        private HashAlgorithm(final String name) {
            m_name = name;
        }

        public static HashAlgorithm getAlgorithmBySize(final int size) {
            HashAlgorithm algorithm = s_sizeAlgoHashMap.get(size);

            return (algorithm == null ? NONE : algorithm);
        }

        public String getName() {
            return m_name;
        }
    }
}
