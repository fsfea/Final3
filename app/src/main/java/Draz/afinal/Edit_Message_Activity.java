package Draz.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class Edit_Message_Activity extends AppCompatActivity {
    private Button btnUpdate ;
    private Button BTNCancel ;
    private SeekBar skbrImportance ;
    private TextView tvImpoertance ;
    private TextInputEditText etShortTitle ;
    private TextInputEditText etText ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_message);

        btnUpdate = findViewById(R.id.btnSave);
        BTNCancel = findViewById(R.id.  BTNCancel);
        skbrImportance = findViewById(R.id.skbrImportance);
        tvImpoertance = findViewById(R.id.tvImpoertance);
        etShortTitle = findViewById(R.id.etContact_phone);
        etText = findViewById(R.id.etText);
    }
    private void onClickUpdate(View v){
        Intent i = new Intent(Edit_Message_Activity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
    public void onClickCancel2(View v) {

        finish();
    }
}