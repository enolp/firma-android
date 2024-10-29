package es.gob.afirma.android;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import es.gob.afirma.R;
import es.gob.afirma.android.gui.AppConfig;

public class SignConfigurationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_configuration);

        if (!NfcHelper.isNfcServiceAvailable(this)) {
            this.findViewById(R.id.allowDnieWithNFCSwitch).setEnabled(false);
        } else {
            ((Switch) this.findViewById(R.id.allowDnieWithNFCSwitch)).setChecked(
                    NfcHelper.isNfcPreferredConnection(this)
            );
        }

        final Switch switchNFC = this.findViewById(R.id.allowDnieWithNFCSwitch);
        switchNFC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NfcHelper.configureNfcAsPreferredConnection(switchNFC.isChecked());
            }
        });

        final Switch switchPadesVisibleSignature = this.findViewById(R.id.padesVisibleSignatureSwitch);
        switchPadesVisibleSignature.setChecked(AppConfig.isPadesVisibleSignature(this));
        switchPadesVisibleSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfig.setPadesVisibleSignature(switchPadesVisibleSignature.isChecked());
            }
        });

        final Switch switchObfuscateCertInfo = this.findViewById(R.id.obfuscateUserCertInfoSwitch);
        switchObfuscateCertInfo.setChecked(AppConfig.isPadesObfuscateCertInfo(this));
        switchObfuscateCertInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfig.setPadesObfuscateCertInfo(switchObfuscateCertInfo.isChecked());
            }
        });

        MaterialToolbar toolbar = findViewById(R.id.signConfigToolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
