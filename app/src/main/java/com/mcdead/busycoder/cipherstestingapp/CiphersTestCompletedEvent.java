package com.mcdead.busycoder.cipherstestingapp;

import android.util.Pair;

import com.mcdead.busycoder.cipherstestingapp.cipherer.CipherAlgorithm;
import com.mcdead.busycoder.cipherstestingapp.cipherstester.KeyHexGenerator;

import java.util.HashMap;

public class CiphersTestCompletedEvent {
    private HashMap<Pair<CipherAlgorithm, KeyHexGenerator.KeySize>, CipherOperationConsumptionTime> m_results;

    public CiphersTestCompletedEvent(HashMap<Pair<CipherAlgorithm, KeyHexGenerator.KeySize>, CipherOperationConsumptionTime> results) {
        m_results = results;
    }

    public HashMap<Pair<CipherAlgorithm, KeyHexGenerator.KeySize>, CipherOperationConsumptionTime> getResults() {
        return m_results;
    }
}
