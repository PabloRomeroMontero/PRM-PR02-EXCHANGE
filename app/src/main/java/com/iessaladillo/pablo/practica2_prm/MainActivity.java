package com.iessaladillo.pablo.practica2_prm;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import static android.view.View.*;

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
        euroOri = findViewById(R.id.main_orgExc_Eur);
        euroFnl = findViewById(R.id.main_fnlExc_Eur);
        poundOri = findViewById(R.id.main_orgExc_Pnds);
        poundFnl = findViewById(R.id.main_fnlExc_Pnds);
        dolarOri = findViewById(R.id.main_orgExc_dlr);
        dolarFnl = findViewById(R.id.main_fnlExc_dlr);
        editQty = ActivityCompat.requireViewById(this,R.id.main_EditQty);
        imgOrg = findViewById(R.id.main_orgExc_ImgView);
        imgFnl = findViewById(R.id.main_fnlExc_ImgView);
        bttnChg = findViewById(R.id.main_button);
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
            case R.id.main_orgExc_Eur:
                euroFnl.setEnabled(false);
                dolarFnl.setEnabled(true);
                poundFnl.setEnabled(true);
                imgOrg.setImageResource(R.drawable.ic_iconmonstr_euro);
                break;
            case R.id.main_orgExc_dlr:
                euroFnl.setEnabled(true);
                dolarFnl.setEnabled(false);
                poundFnl.setEnabled(true);
                imgOrg.setImageResource(R.drawable.ic_iconmonstr_dolar);
                break;
            case R.id.main_orgExc_Pnds:
                euroFnl.setEnabled(true);
                dolarFnl.setEnabled(true);
                poundFnl.setEnabled(false);
                imgOrg.setImageResource(R.drawable.ic_iconmonstr_pound);
                break;
            case R.id.main_fnlExc_Pnds:
                euroOri.setEnabled(true);
                dolarOri.setEnabled(true);
                poundOri.setEnabled(false);
                imgFnl.setImageResource(R.drawable.ic_iconmonstr_pound);
                break;
            case R.id.main_fnlExc_dlr:
                dolarOri.setEnabled(false);
                euroOri.setEnabled(true);
                poundOri.setEnabled(true);
                imgFnl.setImageResource(R.drawable.ic_iconmonstr_dolar);
                break;
            case R.id.main_fnlExc_Eur:
                euroOri.setEnabled(false);
                dolarOri.setEnabled(true);
                poundOri.setEnabled(true);
                imgFnl.setImageResource(R.drawable.ic_iconmonstr_euro);
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
        float euroToDolar = 1.13f;
        float euroToPound = 0.89f;

        if(!String.valueOf(editQty.getText().toString()).equals(".")&&!String.valueOf(editQty.getText().toString()).equals("")&&Float.parseFloat(String.valueOf(editQty.getText().toString()))!=0) {
            amount = Float.parseFloat(String.valueOf(editQty.getText().toString()));
            switch (oriIsChecked()) {
                case R.id.main_orgExc_dlr:
                    ori = getString(R.string.coin_dolar);
                    switch (fnlIsChecked()) {
                        case R.id.main_fnlExc_Eur:
                            fnl = getString(R.string.coin_euro);
                            result = amount * dolarToEuro;
                            break;
                        case R.id.main_fnlExc_Pnds:
                            result = amount * dolarToPound;
                            fnl = getString(R.string.coin_pound);
                            break;
                    }
                    break;
                case R.id.main_orgExc_Pnds:
                    ori = getString(R.string.coin_pound);
                    switch (fnlIsChecked()) {
                        case R.id.main_fnlExc_Eur:
                            result = amount * poundToEuro;
                            fnl = getString(R.string.coin_euro);

                            break;
                        case R.id.main_fnlExc_dlr:
                            result = amount * poundToDolar;
                            fnl = getString(R.string.coin_dolar);
                            break;
                    }
                    break;
                case R.id.main_orgExc_Eur:
                    ori = getString(R.string.coin_euro);
                    switch (fnlIsChecked()) {
                        case R.id.main_fnlExc_dlr:
                            result = amount * euroToDolar;
                            fnl = getString(R.string.coin_dolar);

                            break;
                        case R.id.main_fnlExc_Pnds:
                            result = amount * euroToPound;
                            fnl = getString(R.string.coin_pound);
                            break;
                    }
                    break;

            }
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
            id=R.id.main_orgExc_dlr;
        }else if(poundOri.isChecked()){
            id=R.id.main_orgExc_Pnds;
        }else{
            id=R.id.main_orgExc_Eur;
        }

        return id;
    }
    private int fnlIsChecked(){
        int id;

        if(dolarFnl.isChecked()){
            id=R.id.main_fnlExc_dlr;
        }else if(poundFnl.isChecked()){
            id=R.id.main_fnlExc_Pnds;
        }else{
            id=R.id.main_fnlExc_Eur;
        }

        return id;
    }
}
