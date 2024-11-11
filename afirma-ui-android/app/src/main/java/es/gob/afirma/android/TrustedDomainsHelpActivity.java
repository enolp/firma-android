package es.gob.afirma.android;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.appbar.MaterialToolbar;

import es.gob.afirma.R;

public class TrustedDomainsHelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trusted_domains_help);

        MaterialToolbar toolbar = this.findViewById(R.id.trustedDomainsHelpToolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button goToConfigBtn = this.findViewById(R.id.goToConfigBtn);

        String text = getString(R.string.go_to_configuration);

        SpannableString spannableString = new SpannableString(text + " ");
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.baseline_open_in_new_white_24, getTheme());
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
            spannableString.setSpan(imageSpan, text.length(), text.length() + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        goToConfigBtn.setText(spannableString);

        goToConfigBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SignConfigurationActivity.class);
                startActivity(intent);
            }
        });
    }
}
