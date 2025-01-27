package es.gob.afirma.android;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import es.gob.afirma.R;
import es.gob.afirma.android.util.Utils;

public class SignsRecordActivity extends AppCompatActivity {

    private static final String ES_GOB_AFIRMA = "es.gob.afirma";

    private ListView signRecordLV;
    private SignsRecordAdapter adapter;
    private ArrayList<SignRecord> recordsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setPortraitSmartphone(this);
        setContentView(R.layout.activity_signs_record);

        MaterialToolbar toolbar = findViewById(R.id.signRecordToolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        signRecordLV = this.findViewById(R.id.signsRecordLV);
        loadDataFromRecordsFile();
        // Se ordenan las fechas de recientes a antiguas
        sortDates();
        loadDataToAdapter();

        if (recordsList.isEmpty()) {
            TextView noRecordsTv = findViewById(R.id.noRecordsTitle);
            noRecordsTv.setVisibility(View.VISIBLE);
        }

    }

    private void loadDataToAdapter() {
        adapter = new SignsRecordAdapter(recordsList, this);
        signRecordLV.setAdapter(adapter);
    }

    private void loadDataFromRecordsFile() {
        File directory = getFilesDir();
        String signsRecordFileName = "signsRecord.txt";
        File signRecordFile = new File(directory, signsRecordFileName);
        if (signRecordFile.exists()) {
            try (FileReader fr = new FileReader(signRecordFile)) {
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(";");
                    recordsList.add(new SignRecord(parts[0], parts[1], parts[2], parts[3], parts[4]));
                }
            } catch (Exception e) {
                Logger.e(ES_GOB_AFIRMA, "Error al leer datos del archivo de registro de firmas: " + e, e); //$NON-NLS-1$
            }
        }
    }

    private void sortDates() {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Collections.sort(recordsList, new Comparator<SignRecord>() {
            @Override
            public int compare(SignRecord reg1, SignRecord reg2) {
                try {
                    Date date1 = formatoFecha.parse(reg1.getSignDate());
                    Date date2 = formatoFecha.parse(reg2.getSignDate());

                    return date2.compareTo(date1);
                } catch (ParseException e) {
                    Logger.e(ES_GOB_AFIRMA, "Error al leer datos del archivo de registro de firmas: " + e, e); //$NON-NLS-1$
                }
                return 0;
            }
        });
    }

}
