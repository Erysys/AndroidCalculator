package com.joelbalmes.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ConversionActivity extends BaseActivity {

  String result = "";
  String toResult = "";
  String fromResult = "";
  String fromLabelText = "";
  String toLabelText = "";
  ListView listView;
  EditText edtFromResult, edtToResult;
  TextView fromLabel, toLabel;

  Integer[][] itemNames = {
      //distance
      {R.string.inches, R.string.centimeters},
      {R.string.centimeters, R.string.inches},
      {R.string.meters, R.string.yards},
      {R.string.yards, R.string.meters},
      {R.string.feet, R.string.miles},
      {R.string.miles, R.string.feet},
      {R.string.miles, R.string.kilometers},
      {R.string.kilometers, R.string.miles},
      {R.string.feet, R.string.naut_miles},
      {R.string.naut_miles, R.string.feet},
      {R.string.miles, R.string.naut_miles},
      {R.string.naut_miles, R.string.miles},
      {R.string.kilometers, R.string.naut_miles},
      {R.string.naut_miles, R.string.kilometers},
      //weight
      {R.string.ounces, R.string.grams},
      {R.string.grams, R.string.ounces},
      {R.string.pounds, R.string.kilograms},
      {R.string.kilograms, R.string.pounds},
      {R.string.pounds, R.string.stone},
      {R.string.stone, R.string.pounds},
      {R.string.stone, R.string.us_ton},
      {R.string.us_ton, R.string.stone},
      {R.string.stone, R.string.metric_ton},
      {R.string.metric_ton, R.string.stone},
      {R.string.us_ton, R.string.metric_ton},
      {R.string.metric_ton, R.string.us_ton},
      //volume
      {R.string.quart, R.string.liter},
      {R.string.liter, R.string.quart},
      {R.string.quart, R.string.gallons},
      {R.string.gallons, R.string.quart},
      {R.string.pints, R.string.cups},
      {R.string.cups, R.string.pints},
      {R.string.fl_ounce, R.string.cups},
      {R.string.cups, R.string.fl_ounce},
      //Time
      {R.string.seconds, R.string.minutes},
      {R.string.minutes, R.string.seconds},
      {R.string.minutes, R.string.hours},
      {R.string.hours, R.string.minutes},
      {R.string.hours, R.string.days},
      {R.string.days, R.string.hours},

  };
  Double[] itemValues = {
      2.54,                   // in -> cm
      0.393701,               // cm -> in
      0.9144,                 // meter -> yard
      1.09361,                // yard -> meter
      0.000189394,            // ft -> mile
      5280.0,                 // mile -> feet
      1.60934,                // mile -> km
      0.621371,               // km -> mile
      0.000164579,            // ft -> naut. mile
      6076.14,                // naut. mil -> feet
      0.868976,               // mile -> naut. mile
      1.15078,                // naut. mile -> mile
      0.539957,               // km -> naut. mile
      1.852,                  // naut. mile -> km
      28.3495,                // oz -> gram
      0.035274,               // gram -> oz
      0.453592,               // lbs -> kg
      2.20462,                // kg -> lbs
      .0714286,               // lbs -> stone
      14.0,                   // stone -> lbs
      0.007,                  // stone -> US ton
      142.857,                // US ton -> stone
      0.00635029,             // stone -> metric ton
      157.473,                // metric ton -> stone
      0.907185,               // US ton -> metric ton
      1.10231,                // metric ton -> US ton
      0.946353,               // quart -> liter
      1.05669,                // liter -> quart
      0.25,                   // quart -> gallon
      4.0,                    // gallon -> quart
      1.97157,                // pint -> cup
      0.50721,                // cup -> pint
      0.123223,               // fl oz -> cup
      8.11537,                // cup -> fl oz
      (1.0 / 60.0),           // sec -> min
      60.0,                   // min -> sec
      (1.0 / 60.0),           // min -> hr
      60.0,                   // hr -> min
      (1.0 / 24.0),           // hr -> day
      24.0,                   // day -> hour
  };


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_conversion);
    edtFromResult = findViewById(R.id.edtFromResult);
    edtToResult = findViewById(R.id.edtToResult);
    fromLabel = findViewById(R.id.fromLabel);
    toLabel = findViewById(R.id.toLabel);

    Toolbar toolbar = findViewById(R.id.toolbar);
    toolbar.setTitle(R.string.conversion_title);
    toolbar.setTitleTextColor(Color.WHITE);
    setSupportActionBar(toolbar);

    List<String> items = new ArrayList<String>();

    for( int i = 0; i < itemNames.length; i++) {
      items.add("  " + getString(itemNames[i][0]) + " " + getString(R.string.to_lower) + " " + getString(itemNames [i][1]));
    }

    listView = findViewById(R.id.listViewConversion);
    ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.conversion_list_item, items);
    listView.setAdapter(adapter);

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        performConversion(position);
      }
    });

    Intent intent = getIntent();
    result = intent.getStringExtra( "result" );
    edtFromResult.setText( result );

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    if( edtToResult.getText().toString() == "") {
      result = edtFromResult.getText().toString();
    } else {
      result = edtToResult.getText().toString();
    }

    switch (item.getItemId()) {
      case R.id.menuCalculator:
        Intent calculatorIntent = new Intent(this, MainActivity.class);
        startActivity(calculatorIntent);
        return true;
      case R.id.menuAbout:
        Intent aboutIntent = new Intent(this, AboutActivity.class);
        startActivity(aboutIntent);
        return true;
      case R.id.menuHistory:
        Intent historyIntent = new Intent(this, HistoryActivity.class);
        startActivity(historyIntent);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    fromResult = edtFromResult.getText().toString();
    toResult = edtToResult.getText().toString();
    fromLabelText = fromLabel.getText().toString();
    toLabelText = toLabel.getText().toString();
    String[] stringVarsOut = {fromResult,
                            toResult, fromLabelText, toLabelText};

    outState.putStringArray("StringVars", stringVarsOut);

    super.onSaveInstanceState(outState);
  }

  @Override
  public void onRestoreInstanceState(Bundle savedState) {
    super.onRestoreInstanceState(savedState);
    String[] stringVarsIn = savedState.getStringArray("StringVars");

    fromResult = stringVarsIn[0];
    toResult = stringVarsIn[1];
    fromLabel.setText(stringVarsIn[2]);
    toLabel.setText(stringVarsIn[3]);
    edtFromResult.setText(fromResult);
    edtToResult.setText(toResult);
  }

  private void performConversion(int position) {
    fromLabel.setText(itemNames[position][0]);
    toLabel.setText(itemNames[position][1]);
    if(!edtFromResult.getText().toString().equals("")) {
      Double fromNum = Double.parseDouble(edtFromResult.getText().toString());
      Double toNum = fromNum * itemValues[position];
      String formatPattern = "################";
      Integer intNum = toNum.intValue();
      if (toNum - intNum != 0) {
        Integer decimalPosition = String.valueOf(intNum).length();
        StringBuffer str = new StringBuffer(formatPattern);
        str.insert(decimalPosition, ".");
        formatPattern = str.toString();
      }
      DecimalFormat df = new DecimalFormat(formatPattern);
      toResult = df.format(toNum);
      edtToResult.setText(toResult);
    }
  }


}
