package com.mcdead.busycoder.cipherstestingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mcdead.busycoder.cipherstestingapp.cipherer.CipherAlgorithm;
import com.mcdead.busycoder.cipherstestingapp.cipherstester.CipherTestExecutorService;
import com.mcdead.busycoder.cipherstestingapp.cipherstester.KeyHexGenerator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private TextView m_aes128SmallCipherTimeResultView = null;
    private TextView m_aes256SmallCipherTimeResultView = null;
    private TextView m_blowfish128SmallCipherTimeResultView = null;
    private TextView m_blowfish256SmallCipherTimeResultView = null;
    private TextView m_chacha20SmallCipherTimeResultView = null;

    private TextView m_aes128SmallDecipherTimeResultView = null;
    private TextView m_aes256SmallDecipherTimeResultView = null;
    private TextView m_blowfish128SmallDecipherTimeResultView = null;
    private TextView m_blowfish256SmallDecipherTimeResultView = null;
    private TextView m_chacha20SmallDecipherTimeResultView = null;

    private TextView m_aes128LargeTimeCipherResultView = null;
    private TextView m_aes256LargeTimeCipherResultView = null;
    private TextView m_blowfish128LargeTimeCipherResultView = null;
    private TextView m_blowfish256LargeTimeCipherResultView = null;
    private TextView m_chacha20LargeTimeCipherResultView = null;

    private TextView m_aes128LargeTimeDecipherResultView = null;
    private TextView m_aes256LargeTimeDecipherResultView = null;
    private TextView m_blowfish128LargeTimeDecipherResultView = null;
    private TextView m_blowfish256LargeTimeDecipherResultView = null;
    private TextView m_chacha20LargeTimeDecipherResultView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        m_aes128SmallCipherTimeResultView = findViewById(R.id.aes_128_small_time_cipher_result);
        m_aes256SmallCipherTimeResultView = findViewById(R.id.aes_256_small_time_cipher_result);
        m_blowfish128SmallCipherTimeResultView = findViewById(R.id.blowfish_128_small_time_cipher_result);
        m_blowfish256SmallCipherTimeResultView = findViewById(R.id.blowfish_256_small_time_cipher_result);
        m_chacha20SmallCipherTimeResultView = findViewById(R.id.chacha20_small_time_cipher_result);

        m_aes128SmallDecipherTimeResultView = findViewById(R.id.aes_128_small_time_decipher_result);
        m_aes256SmallDecipherTimeResultView = findViewById(R.id.aes_256_small_time_decipher_result);
        m_blowfish128SmallDecipherTimeResultView = findViewById(R.id.blowfish_128_small_time_decipher_result);
        m_blowfish256SmallDecipherTimeResultView = findViewById(R.id.blowfish_256_small_time_decipher_result);
        m_chacha20SmallDecipherTimeResultView = findViewById(R.id.chacha20_small_time_decipher_result);

        m_aes128LargeTimeCipherResultView = findViewById(R.id.aes_128_large_time_cipher_result);
        m_aes256LargeTimeCipherResultView = findViewById(R.id.aes_256_large_time_cipher_result);
        m_blowfish128LargeTimeCipherResultView = findViewById(R.id.blowfish_128_large_time_cipher_result);
        m_blowfish256LargeTimeCipherResultView = findViewById(R.id.blowfish_256_large_time_cipher_result);
        m_chacha20LargeTimeCipherResultView = findViewById(R.id.chacha20_large_time_cipher_result);

        m_aes128LargeTimeDecipherResultView = findViewById(R.id.aes_128_large_time_decipher_result);
        m_aes256LargeTimeDecipherResultView = findViewById(R.id.aes_256_large_time_decipher_result);
        m_blowfish128LargeTimeDecipherResultView = findViewById(R.id.blowfish_128_large_time_decipher_result);
        m_blowfish256LargeTimeDecipherResultView = findViewById(R.id.blowfish_256_large_time_decipher_result);
        m_chacha20LargeTimeDecipherResultView = findViewById(R.id.chacha20_large_time_decipher_result);

        Button button = findViewById(R.id.start_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCiphersTest();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);

        super.onStop();
    }

    private void startCiphersTest() {
        Intent ciphersTestIntent = new Intent(getApplicationContext(), CipherTestExecutorService.class);

        startService(ciphersTestIntent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCiphersTestCompleted(CiphersTestCompletedEvent event) {
        HashMap<Pair<CipherAlgorithm, KeyHexGenerator.KeySize>, CipherOperationConsumptionTime> results = event.getResults();

        CipherOperationConsumptionTime aes128 = results.get(new Pair<>(CipherAlgorithm.AES, KeyHexGenerator.KeySize.KEY128));
        CipherOperationConsumptionTime aes256 = results.get(new Pair<>(CipherAlgorithm.AES, KeyHexGenerator.KeySize.KEY256));
        CipherOperationConsumptionTime blowfish128 = results.get(new Pair<>(CipherAlgorithm.BlowFish, KeyHexGenerator.KeySize.KEY128));
        CipherOperationConsumptionTime blowfish256 = results.get(new Pair<>(CipherAlgorithm.BlowFish, KeyHexGenerator.KeySize.KEY256));
        CipherOperationConsumptionTime chacha20256 = results.get(new Pair<>(CipherAlgorithm.ChaCha20, KeyHexGenerator.KeySize.KEY256));

        m_aes128SmallCipherTimeResultView.setText(String.valueOf(aes128.getSmallDataCipheringTime()) + "ms");
        m_aes256SmallCipherTimeResultView.setText(String.valueOf(aes256.getSmallDataCipheringTime()) + "ms");
        m_blowfish128SmallCipherTimeResultView.setText(String.valueOf(blowfish128.getSmallDataCipheringTime()) + "ms");
        m_blowfish256SmallCipherTimeResultView.setText(String.valueOf(blowfish256.getSmallDataCipheringTime()) + "ms");
        m_chacha20SmallCipherTimeResultView.setText(String.valueOf(chacha20256.getSmallDataCipheringTime()) + "ms");

        m_aes128SmallDecipherTimeResultView.setText(String.valueOf(aes128.getSmallDataDecipheringTime()) + "ms");
        m_aes256SmallDecipherTimeResultView.setText(String.valueOf(aes256.getSmallDataDecipheringTime()) + "ms");
        m_blowfish128SmallDecipherTimeResultView.setText(String.valueOf(blowfish128.getSmallDataDecipheringTime()) + "ms");
        m_blowfish256SmallDecipherTimeResultView.setText(String.valueOf(blowfish256.getSmallDataDecipheringTime()) + "ms");
        m_chacha20SmallDecipherTimeResultView.setText(String.valueOf(chacha20256.getSmallDataDecipheringTime()) + "ms");

        m_aes128LargeTimeCipherResultView.setText(String.valueOf(aes128.getLargeDataCipheringTime()) + "ms");
        m_aes256LargeTimeCipherResultView.setText(String.valueOf(aes256.getLargeDataCipheringTime()) + "ms");
        m_blowfish128LargeTimeCipherResultView.setText(String.valueOf(blowfish128.getLargeDataCipheringTime()) + "ms");
        m_blowfish256LargeTimeCipherResultView.setText(String.valueOf(blowfish256.getLargeDataCipheringTime()) + "ms");
        m_chacha20LargeTimeCipherResultView.setText(String.valueOf(chacha20256.getLargeDataCipheringTime()) + "ms");

        m_aes128LargeTimeDecipherResultView.setText(String.valueOf(aes128.getLargeDataDecipheringTime()) + "ms");
        m_aes256LargeTimeDecipherResultView.setText(String.valueOf(aes256.getLargeDataDecipheringTime()) + "ms");
        m_blowfish128LargeTimeDecipherResultView.setText(String.valueOf(blowfish128.getLargeDataDecipheringTime()) + "ms");
        m_blowfish256LargeTimeDecipherResultView.setText(String.valueOf(blowfish256.getLargeDataDecipheringTime()) + "ms");
        m_chacha20LargeTimeDecipherResultView.setText(String.valueOf(chacha20256.getLargeDataDecipheringTime()) + "ms");
    }
}