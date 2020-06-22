package com.joelbalmes.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setContentView(R.layout.activity_base);
  }

  public void toastIt(String msg) {
    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
  }
}
