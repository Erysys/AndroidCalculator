package com.joelbalmes.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class HistoryActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_history);

    Toolbar toolbar = findViewById(R.id.toolbar);
    toolbar.setTitle(R.string.history_title);
    toolbar.setTitleTextColor( Color.WHITE );
    setSupportActionBar(toolbar);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    switch (item.getItemId()) {
      case R.id.menuConversion:
        Intent conversionIntent = new Intent( this, ConversionActivity.class );
        startActivity( conversionIntent );
        return true;
      case R.id.menuCalculator:
        Intent calculatorIntent = new Intent( this, MainActivity.class );
        startActivity( calculatorIntent );
        return true;
      case R.id.menuAbout:
        Intent aboutIntent = new Intent( this, AboutActivity.class );
        startActivity( aboutIntent );
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
