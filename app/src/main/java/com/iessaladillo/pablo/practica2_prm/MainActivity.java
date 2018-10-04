package com.iessaladillo.pablo.practica2_prm;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    private RadioButton euroOri;
    private RadioButton euroFnl;
    private RadioButton poundOri;
    private RadioButton poundFnl;
    private RadioButton dolarOri;
    private RadioButton dolarFnl;
    private EditText editQty;
    private ImageView imgOrg;
    private ImageView imgFnl;
    // DEFÍNELAS COMO VARIABLES LOCALES AL MÉTODO QUE LAS USA.
    private Button bttnChg;
    private RadioGroup rdGrpOrg;
    private RadioGroup rdGrpFnl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        rdGrpOrg = findViewById(R.id.main_orgExc);
        euroOri = findViewById(R.id.rbFromEuro);
        euroFnl = findViewById(R.id.rbToEuro);
        poundOri = findViewById(R.id.rbFromPound);
        poundFnl = findViewById(R.id.rbToPound);
        dolarOri = findViewById(R.id.rbFromDollar);
        dolarFnl = findViewById(R.id.rbToDollar);
        editQty = ActivityCompat.requireViewById(this,R.id.txtAmount);
        imgOrg = findViewById(R.id.imgFrom);
        imgFnl = findViewById(R.id.imgTo);
        bttnChg = findViewById(R.id.btnExchange);
        rdGrpOrg = findViewById(R.id.main_orgExc);
        rdGrpFnl = findViewById(R.id.main_fnlExc);

        bttnChg.setOnClickListener(v -> changeCoin());
        rdGrpOrg.setOnCheckedChangeListener((group, checkedId) -> changeRadioButton(checkedId));
        rdGrpFnl.setOnCheckedChangeListener((group, checkedId) -> changeRadioButton(checkedId));
        euroOri.performClick();
        dolarFnl.performClick();
    }

    private void changeRadioButton(int checkedId) {
        switch (checkedId){
            case R.id.rbFromEuro:
                euroFnl.setEnabled(false);
                dolarFnl.setEnabled(true);
                poundFnl.setEnabled(true);
                imgOrg.setImageResource(R.drawable.ic_euro);
                imgOrg.setTag(R.drawable.ic_euro);
                break;
            case R.id.rbFromDollar:
                euroFnl.setEnabled(true);
                dolarFnl.setEnabled(false);
                poundFnl.setEnabled(true);
                imgOrg.setImageResource(R.drawable.ic_dollar);
                imgOrg.setTag(R.drawable.ic_dollar);
                break;
            case R.id.rbFromPound:
                euroFnl.setEnabled(true);
                dolarFnl.setEnabled(true);
                poundFnl.setEnabled(false);
                imgOrg.setImageResource(R.drawable.ic_pound);
                imgOrg.setTag(R.drawable.ic_pound);
                break;
            case R.id.rbToPound:
                euroOri.setEnabled(true);
                dolarOri.setEnabled(true);
                poundOri.setEnabled(false);
                imgFnl.setImageResource(R.drawable.ic_pound);
                imgFnl.setTag(R.drawable.ic_pound);
                break;
            case R.id.rbToDollar:
                dolarOri.setEnabled(false);
                euroOri.setEnabled(true);
                poundOri.setEnabled(true);
                imgFnl.setImageResource(R.drawable.ic_dollar);
                imgFnl.setTag(R.drawable.ic_dollar);
                break;
            case R.id.rbToEuro:
                euroOri.setEnabled(false);
                dolarOri.setEnabled(true);
                poundOri.setEnabled(true);
                imgFnl.setImageResource(R.drawable.ic_euro);
                imgFnl.setTag(R.drawable.ic_euro);
                break;
        }
    }


    private void changeCoin() {
        float amount;
        float result = 0;
        String ori = "";
        String fnl = "";
        float dolarToPound= 0.77f;
        float dolarToEuro= 0.86f;
        float poundToEuro=  1.13f;
        float poundToDolar = 1.31f;
        float euroToDolar = 1.17f;
        float euroToPound = 0.89f;

        if(!String.valueOf(editQty.getText().toString()).equals(".")&&!String.valueOf(editQty.getText().toString()).equals("")&&Float.parseFloat(String.valueOf(editQty.getText().toString()))!=0) {
            amount = Float.parseFloat(String.valueOf(editQty.getText().toString()));
            switch (oriIsChecked()) {
                case R.id.rbFromDollar:
                    ori = getString(R.string.coin_dolar);
                    switch (fnlIsChecked()) {
                        case R.id.rbToEuro:
                            fnl = getString(R.string.coin_euro);
                            result = amount * dolarToEuro;
                            break;
                        case R.id.rbToPound:
                            result = amount * dolarToPound;
                            fnl = getString(R.string.coin_pound);
                            break;
                    }
                    break;
                case R.id.rbFromPound:
                    ori = getString(R.string.coin_pound);
                    switch (fnlIsChecked()) {
                        case R.id.rbToEuro:
                            result = amount * poundToEuro;
                            fnl = getString(R.string.coin_euro);

                            break;
                        case R.id.rbToDollar:
                            result = amount * poundToDolar;
                            fnl = getString(R.string.coin_dolar);
                            break;
                    }
                    break;
                case R.id.rbFromEuro:
                    ori = getString(R.string.coin_euro);
                    switch (fnlIsChecked()) {
                        case R.id.rbToDollar:
                            result = amount * euroToDolar;
                            fnl = getString(R.string.coin_dolar);

                            break;
                        case R.id.rbToPound:
                            result = amount * euroToPound;
                            fnl = getString(R.string.coin_pound);
                            break;
                    }
                    break;

            }
            // EN VEZ DE String.format USA getString() DIRECTAMENTE
            Toast.makeText(this, String.format(getString(R.string.main_message_toast), amount, ori, result, fnl), Toast.LENGTH_SHORT).show();
        }
        reset();
    }

    private void reset(){
        editQty.setText(R.string.main_editText_text);
    }

    private int oriIsChecked(){
        int id;
        if(dolarOri.isChecked()){
            id=R.id.rbFromDollar;
        }else if(poundOri.isChecked()){
            id=R.id.rbFromPound;
        }else{
            id=R.id.rbFromEuro;
        }

        return id;
    }
    private int fnlIsChecked(){
        int id;

        if(dolarFnl.isChecked()){
            id=R.id.rbToDollar;
        }else if(poundFnl.isChecked()){
            id=R.id.rbToPound;
        }else{
            id=R.id.rbToEuro;
        }

        return id;
    }
}
