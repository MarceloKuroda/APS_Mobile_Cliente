package com.example.aps_meu_dinheiro_cliente;

import org.apache.http.Header;

import com.example.aps_meu_dinheiro_cliente.entidade.Conta;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends ActionBarActivity {

	String login, senha;
	long idusuario;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		login = getIntent().getStringExtra("login");
		idusuario = getIntent().getLongExtra("idusuario", 0);
		senha = getIntent().getStringExtra("senha");

		
		Button b1 = (Button) findViewById(R.id.button1);
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MenuActivity.this, DespesaActivity.class);
				i.putExtra("idusuario", idusuario);
				startActivity(i);
			}
		});
		
		Button b2 = (Button) findViewById(R.id.button2);
		b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MenuActivity.this, ReceitaActivity.class);
				i.putExtra("idusuario", idusuario);
				startActivity(i);
			}
		});
		
		Button b3 = (Button) findViewById(R.id.button3);
		b3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MenuActivity.this, SaldoActivity.class);
				i.putExtra("idusuario", idusuario);
				startActivity(i);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.item1) {
			Intent i = new Intent(MenuActivity.this, EditarUsuarioActivity.class);
			i.putExtra("idusuario", idusuario);
			i.putExtra("login", login);
			i.putExtra("senha", senha);
			startActivityForResult(i, 1);
			return true;
		}
		else if (id == R.id.item2) {
			excluirUsuario();
			MenuActivity.this.finish();			
			return true;
		}
		
		else if (id == R.id.item3) {			
			MenuActivity.this.finish();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	public void excluirUsuario(){
		String resourceURL = "http://10.0.2.2:2000/usuario/" + idusuario;
		AsyncHttpClient httpClient = new AsyncHttpClient();
		httpClient.delete(resourceURL,
				new TextHttpResponseHandler() {
					@Override
					public void onSuccess(int status,
							Header[] headers, String responseString) {
						if (responseString != null	&& responseString.equals("usuario removido")) {
							Toast.makeText(MenuActivity.this, "Usuario removido com sucesso!", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(MenuActivity.this, "Erro ao remover usuario: " + responseString,	Toast.LENGTH_SHORT).show();
						}
					}
					@Override
					public void onFailure(int arg0, Header[] arg1,
							String arg2, Throwable arg3) {
						// TODO Auto-generated method stub
					}
				});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 1 && resultCode == RESULT_OK) {
			login = (String) data.getSerializableExtra("login");
			senha = (String) data.getSerializableExtra("senha");
		}
	}
}
