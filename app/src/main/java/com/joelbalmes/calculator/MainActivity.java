package com.joelbalmes.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

public class MainActivity extends BaseActivity {

  Button btnCalculate;
  Button btnClear;
  EditText edtResult;
  String result = "";
  String displayResult = "";
  String leftExpression = "";
  String rightExpression = "";
  String currentOperator = "";
  boolean hasDecimal = false;
  boolean isFirstExpression = true;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Toolbar toolbar = findViewById(R.id.toolbar);
    toolbar.setTitle(R.string.calculator_title);
    toolbar.setTitleTextColor( Color.WHITE );

    setSupportActionBar(toolbar);

    btnCalculate = findViewById(R.id.btnCalculate);
    btnClear = findViewById(R.id.btnClear);
    edtResult = findViewById(R.id.edtResult);

    updateDisplay(displayResult);

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    result = edtResult.getText().toString();
    switch (item.getItemId()) {
      case R.id.menuConversion:
        Intent conversionIntent = new Intent( this, ConversionActivity.class );
        conversionIntent.putExtra("result", result);
        startActivity( conversionIntent );
        return true;
      case R.id.menuAbout:
        Intent aboutIntent = new Intent( this, AboutActivity.class );
        startActivity( aboutIntent );
        return true;
      case R.id.menuHistory:
        Intent historyIntent = new Intent( this, HistoryActivity.class );
        startActivity( historyIntent );
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    String[] stringVarsOut = {displayResult,
        leftExpression,
        rightExpression,
        currentOperator};
    boolean[] boolVarsOut = {hasDecimal,
        isFirstExpression};
    outState.putStringArray("StringVars", stringVarsOut);
    outState.putBooleanArray("BoolVars", boolVarsOut);

    super.onSaveInstanceState(outState);
  }

  @Override
  public void onRestoreInstanceState(Bundle savedState) {
    super.onRestoreInstanceState(savedState);
    String[] stringVarsIn = savedState.getStringArray("StringVars");
    boolean[] boolVarsIn = savedState.getBooleanArray("BoolVars");

    displayResult = stringVarsIn[0];
    leftExpression = stringVarsIn[1];
    rightExpression = stringVarsIn[2];
    currentOperator = stringVarsIn[3];
    hasDecimal = boolVarsIn[0];
    isFirstExpression = boolVarsIn[1];
    updateDisplay(displayResult);
  }

  public void numBtnOnClick(View v) {
    String btnName = ((Button) v).getText().toString();

    if (btnName.equals(".")) {
      if (!hasDecimal && displayResult.equals("")) {
        displayResult += "0" + btnName;
        hasDecimal = true;
      } else if (!hasDecimal) {
        displayResult += btnName;
        hasDecimal = true;
      }
    } else {
      displayResult += btnName;
    }
    edtResult.setText(displayResult);
  }

  public void opBtnOnClick(View v) {
    String btnName = ((Button) v).getText().toString();

    if (!displayResult.equals("")) {
      if (isFirstExpression) {
        leftExpression = displayResult;
        displayResult = "";
        hasDecimal = false;
        isFirstExpression = false;
      } else {
        rightExpression = displayResult;
        displayResult = calculate(leftExpression, currentOperator, rightExpression);
        updateDisplay(displayResult);
        leftExpression = displayResult;
        displayResult = "";
        hasDecimal = false;
      }
    }
    currentOperator = btnName;
  }

  public void clearBtnOnClick(View v) {
    displayResult = "";
    leftExpression = "";
    rightExpression = "";
    currentOperator = "";
    hasDecimal = false;
    isFirstExpression = true;
    updateDisplay(displayResult);
  }

  public void calculateBtnOnClick(View v) {
    if (!isFirstExpression && !currentOperator.equals("")) {

      if (!displayResult.equals("")) {
        rightExpression = displayResult;
      }
      displayResult = calculate(leftExpression, currentOperator, rightExpression);
      updateDisplay(displayResult);
      leftExpression = displayResult;
      displayResult = "";
      hasDecimal = false;
    }

  }

  public void singleOpsBtnOnClick(View v) {
    if (displayResult.equals("") && !leftExpression.equals("")) {
      displayResult = leftExpression;
      leftExpression = "";
      rightExpression = "";
      currentOperator = "";
      isFirstExpression = true;
    }

    if (!displayResult.equals("")) {
      String btnName = ((Button) v).getText().toString();
      Double num = Double.parseDouble(displayResult);
      Double result;

      switch (btnName) {
        case "%":
          result = num / 100;
          displayResult = result.toString();
          break;
        case "SIN":
          result = Math.sin(num);
          displayResult = result.toString();
          break;
        case "COS":
          result = Math.cos(num);
          displayResult = result.toString();
          break;
        case "TAN":
          result = Math.tan(num);
          displayResult = result.toString();
          break;
        case "√":
          result = Math.sqrt(num);
          displayResult = result.toString();
          break;
        case "x²":
          result = num * num;
          displayResult = result.toString();
          break;
        default:
          break;
      }
    }
    updateDisplay(displayResult);
  }

  public void insertNumBtnOnClick(View v) {
    Double result;
    switch (v.getId()) {
      case R.id.btn_random:
        Integer randomInt = (int) (Math.random() * 10);
        displayResult = randomInt.toString();
        break;
      case R.id.btn_pi:
        result = Math.PI;
        displayResult = result.toString();
        break;
      default:
        break;
    }
    updateDisplay(displayResult);
  }

  public void backspaceBtnOnClick(View v) {
    if (!displayResult.equals("")) {
      displayResult = displayResult.substring(0, displayResult.length() - 1);
    }

    if (displayResult.equals("-")) {
      displayResult = "";
    }

    updateDisplay(displayResult);
  }

  public void posNegBtnOnClick(View v) {
    if (displayResult.equals("") && !leftExpression.equals("")) {
      displayResult = leftExpression;
      leftExpression = "";
      rightExpression = "";
      currentOperator = "";
      isFirstExpression = true;
    }
    if (!displayResult.equals("")) {

      Double dblNum = Double.valueOf(displayResult);
      Integer intNum = dblNum.intValue();
      if (dblNum - intNum == 0) {
        displayResult = String.valueOf(intNum * (-1));
      } else {
        displayResult = String.valueOf(dblNum * (-1));
      }
    }
    updateDisplay(displayResult);
  }

  private String calculate(String leftExpression, String operator, String rightExpression) {

    Double leftNum = Double.parseDouble(leftExpression);
    Double rightNum = Double.parseDouble(rightExpression);
    Double result;
    boolean hasError = false;
    switch (operator) {
      case "+":
        result = (leftNum + rightNum);
        break;
      case "-":
        result = (leftNum - rightNum);
        break;
      case "x":
        result = (leftNum * rightNum);
        break;
      case "÷":
        result = (leftNum / rightNum);
        break;
      default:
        result = 0.0;
        hasError = true;
        break;
    }
    if (hasError) {
      return getString(R.string.str_error);
    } else {
      return String.valueOf(result);
    }
  }

  private void updateDisplay(String result) {
    if (!result.equals("")) {
      Double dblNum = Double.valueOf(result);
      Integer intNum = dblNum.intValue();
      if (dblNum - intNum == 0) {
        displayResult = intNum.toString();
      } else {
        displayResult = dblNum.toString();
      }
    } else {
      displayResult = result;
    }
    edtResult.setText(displayResult);
  }
}
