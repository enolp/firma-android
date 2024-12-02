package es.gob.afirma.android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import es.gob.afirma.R;
import es.gob.afirma.android.gui.AppConfig;

public class ConditionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conditions);

        Spinner spinner =  this.findViewById(R.id.spinner_languages);
        List<String> spinnerValues = List.of(getString(R.string.espanol), getString(R.string.english), getString(R.string.catala), getString(R.string.galego), getString(R.string.euskera), getString(R.string.valenciano));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.textview_spinner_selected, spinnerValues);
        adapter.setDropDownViewResource(R.layout.textview_spinner_selected);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*switch (position) {
                    case 1:
                        changeLang("en");
                        break;
                    case 2:
                        changeLang("ca");
                        break;
                    case 3:
                        changeLang("ga");
                        break;
                    case 4:
                        changeLang("eu");
                        break;
                    case 5:
                        changeLang("va");
                        break;
                    default:
                        changeLang("es");
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        TextView tvChild =  this.findViewById(R.id.contentConditionsTv);
        tvChild.setText(Html.fromHtml(getString(R.string.privacy_policy_text_1)
                        + getString(R.string.privacy_policy_title_2)
                        + getString(R.string.privacy_policy_text_2)
                        + getString(R.string.privacy_policy_title_3)
                        + getString(R.string.privacy_policy_text_3)
                        + getString(R.string.privacy_policy_title_4)
                        + getString(R.string.privacy_policy_text_4_1)
                        + getString(R.string.privacy_policy_text_4_2)
                        + getString(R.string.privacy_policy_title_5)
                        + getString(R.string.privacy_policy_text_5)
                        + getString(R.string.privacy_policy_title_6)
                        + getString(R.string.privacy_policy_text_6)
                        + getString(R.string.privacy_policy_title_7)
                        + getString(R.string.privacy_policy_text_7)
                        + getString(R.string.privacy_policy_title_8)
                        + getString(R.string.privacy_policy_text_8_1)
                        + getString(R.string.privacy_policy_text_8_2)
                        + getString(R.string.privacy_policy_text_8_3)
                        + getString(R.string.privacy_policy_text_8_4)
                        + getString(R.string.privacy_policy_text_8_5)
                        + getString(R.string.privacy_policy_title_9)
                        + getString(R.string.privacy_policy_text_9_1)
                        + getString(R.string.privacy_policy_text_9_2)
                        + getString(R.string.privacy_policy_text_9_3)
                        + getString(R.string.privacy_policy_title_10)
                        + getString(R.string.privacy_policy_text_10)
        ));

        Button acceptConditionsBtn = this.findViewById(R.id.acceptConditionsBtn);
        acceptConditionsBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AppConfig.setSkipConditionsScreen(true);
                Intent intent = new Intent(ConditionsActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        CheckBox readAndAcceptPrivacyChk = this.findViewById(R.id.readAndAcceptPrivacyChk);
        CheckBox readAndAcceptLegalChk = this.findViewById(R.id.readAndAcceptLegalChk);

        readAndAcceptPrivacyChk.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkButtonState(readAndAcceptPrivacyChk, readAndAcceptLegalChk, acceptConditionsBtn);
            }
        });

        readAndAcceptLegalChk.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkButtonState(readAndAcceptPrivacyChk, readAndAcceptLegalChk, acceptConditionsBtn);
            }
        });

    }

    private void checkButtonState(final CheckBox readAndAcceptPrivacyChk, final CheckBox readAndAcceptLegalChk, final Button acceptConditionsBtn) {
        if (readAndAcceptPrivacyChk.isChecked() && readAndAcceptLegalChk.isChecked()) {
            acceptConditionsBtn.setEnabled(true);
            acceptConditionsBtn.setTextColor(Color.WHITE);
        } else {
            acceptConditionsBtn.setEnabled(false);
            acceptConditionsBtn.setTextColor(Color.parseColor("#767676"));
        }
    }

    public void changeLang(String lang) {
        LocaleHelper.setLocale(this, lang);
        this.recreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    public void onBackPressed() {
        // No hace nada
    }

}
