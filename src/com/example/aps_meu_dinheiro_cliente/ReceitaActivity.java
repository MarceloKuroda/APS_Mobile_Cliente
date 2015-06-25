package com.example.aps_meu_dinheiro_cliente;

import com.example.aps_meu_dinheiro_cliente.R;
import com.example.aps_meu_dinheiro_cliente.R.id;
import com.example.aps_meu_dinheiro_cliente.R.layout;
import com.example.aps_meu_dinheiro_cliente.R.menu;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ReceitaActivity extends ActionBarActivity {

	long idusuario;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_receita);
		
		idusuario = getIntent().getLongExtra("idusuario", 0);
		
		Button b1 = (Button) findViewById(R.id.button1);
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(ReceitaActivity.this,RegistrarReceitaActivity.class);
				i.putExtra("idusuario", idusuario);
				startActivity(i);
			}
		});
		
		Button b2 = (Button) findViewById(R.id.button2);
		b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(ReceitaActivity.this, VisualizarReceitaFuturaActivity.class);
				i.putExtra("idusuario", idusuario);
				startActivity(i);
			}
		});
		
		Button b3 = (Button) findViewById(R.id.button3);
		b3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(ReceitaActivity.this, VisualizarReceitaActivity.class);
				i.putExtra("idusuario", idusuario);
				startActivity(i);
			}
		});
		
		Button b4 = (Button) findViewById(R.id.button4);
		b4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ReceitaActivity.this.finish();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registrar_receita_futura, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
