package com.example.gymapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subactivity_main);

        editTextName = findViewById(R.id.editTextText);
        findViewById(R.id.next).setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.next) {
            // 사용자가 입력한 이름 가져오기
            String userName = editTextName.getText().toString();
            // 팝업 창 띄우기
            showWelcomePopup(userName);
        }
    }

    private void showWelcomePopup(String userName) {
        new AlertDialog.Builder(this)
                .setTitle("환영합니다 🖐️ ")
                .setMessage(userName + "님.\n GymAPP을 사용해주셔서 감사합니다.")
                .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int sumthin) {
                        // 팝업 창 닫기
                        dlg.dismiss();
        Intent intent = new Intent(SubActivity.this, SubActivity2.class);
        startActivity(intent);
    }
})
                .show();

        }
}
