package es.gob.afirma.android;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import es.gob.afirma.R;
import es.gob.afirma.android.util.FileUtil;
import es.gob.afirma.android.util.Utils;

public class LegalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setPortraitSmartphone(this);
        setContentView(R.layout.activity_legal);

        MaterialToolbar toolbar = this.findViewById(R.id.legalToolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        WebView wvLegal =  this.findViewById(R.id.contentLegalWv);
        String legalHtml = FileUtil.readLegalFile(this, LocaleHelper.getPersistedData(this));
        String encodedHtml = Base64.encodeToString(legalHtml.getBytes(),
                Base64.NO_PADDING);
        wvLegal.loadData(encodedHtml, "text/html", "base64");

    }

}
