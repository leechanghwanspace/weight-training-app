package com.example.gymapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GymActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gymactivity_main);

        // EditText 초기화 및 SharedPreferences 로드
        initEditTextValues();

        // "Show Calculator" 버튼 클릭 시 팝업 다이얼로그 표시
        ImageButton showCalculatorButton = findViewById(R.id.imageView5);
        showCalculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupDialog();
            }
        });

        // EditText1의 텍스트 변경 이벤트 처리
        EditText editText1 = findViewById(R.id.editTextText3);
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                saveTextToPrefs("editText1", charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void initEditTextValues() {
        EditText editText1 = findViewById(R.id.editTextText3);
        EditText editText2 = findViewById(R.id.editTextText4);
        EditText editText3 = findViewById(R.id.editTextText5);
        EditText editText4 = findViewById(R.id.editTextText6);
        EditText editText5 = findViewById(R.id.editTextText7);
        EditText editText6 = findViewById(R.id.editTextText8);
        EditText editText7 = findViewById(R.id.editTextText9);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        editText1.setText(prefs.getString("editText1", "[운동명 : Xkg + 세트 수 + 갯수]"));
        editText2.setText(prefs.getString("editText2", "[운동명 : Xkg + 세트 수 + 갯수]"));
        editText3.setText(prefs.getString("editText3", "[운동명 : Xkg + 세트 수 + 갯수]"));
        editText4.setText(prefs.getString("editText4", "[운동명 : Xkg + 세트 수 + 갯수]"));
        editText5.setText(prefs.getString("editText5", "[운동명 : Xkg + 세트 수 + 갯수]"));
        editText6.setText(prefs.getString("editText6", "[운동명 : Xkg + 세트 수 + 갯수]"));
        editText7.setText(prefs.getString("editText7", "[운동명 : Xkg + 세트 수 + 갯수]"));
    }

    private void saveTextToPrefs(String key, String value) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        prefs.edit().putString(key, value).apply();
    }

    private void showPopupDialog() {
        // LayoutInflater를 사용하여 XML 레이아웃을 View로 변환
        LayoutInflater inflater = LayoutInflater.from(this);
        View popupView = inflater.inflate(R.layout.popup_layout, null);

        // 팝업 다이얼로그 생성
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(popupView);

        // 팝업 다이얼로그의 뷰에서 필요한 UI 요소들 가져오기
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText editTextWeight = popupView.findViewById(R.id.editTextWeight);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText editTextPercentage = popupView.findViewById(R.id.editTextPercentage);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button btnCalculate = popupView.findViewById(R.id.btnCalculate);

        // 팝업 다이얼로그 표시
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        // "Calculate" 버튼 클릭 시 이벤트 처리
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCalculateButtonClick(editTextWeight, editTextPercentage, alertDialog);
            }
        });
    }

    private void handleCalculateButtonClick(EditText editTextWeight, EditText editTextPercentage, AlertDialog alertDialog) {

        String weightStr = editTextWeight.getText().toString();
        String percentageStr = editTextPercentage.getText().toString();


        if (!weightStr.isEmpty() && !percentageStr.isEmpty()) {
            // 계산 로직 수행
            double weight = Double.parseDouble(weightStr);
            int percentage = Integer.parseInt(percentageStr);
            double result = weight * (percentage / 100.0);

            showResultDialog(result);

            alertDialog.dismiss();
        }
    }

    private void showResultDialog(double result) {
        // 결과를 표시할 다이얼로그 생성
        AlertDialog.Builder resultDialogBuilder = new AlertDialog.Builder(this);
        resultDialogBuilder.setMessage("Result: " + result);
        resultDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // 다이얼로그 표시
        resultDialogBuilder.create().show();
    }
}
