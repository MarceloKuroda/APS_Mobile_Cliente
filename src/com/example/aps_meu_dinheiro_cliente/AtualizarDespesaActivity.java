package com.example.aps_meu_dinheiro_cliente;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.aps_meu_dinheiro_cliente.R;
import com.example.aps_meu_dinheiro_cliente.R.id;
import com.example.aps_meu_dinheiro_cliente.R.layout;
import com.example.aps_meu_dinheiro_cliente.R.menu;
import com.example.aps_meu_dinheiro_cliente.entidade.Conta;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AtualizarDespesaActivity extends ActionBarActivity {

	long id;
	double valor;
	String descricao, dataPagamento;
	Boolean pago;
	EditText ed1, ed2, ed3;
	Button b1, b2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_atualizar_despesa);
		
		id = (long) getIntent().getLongExtra("id", 0);
		descricao = (String) getIntent().getStringExtra("descricao");
		valor = (double) getIntent().getDoubleExtra("valor", 0);
		dataPagamento = (String) getIntent().getStringExtra("dataPagamento");
		pago = (Boolean) getIntent().getBooleanExtra("pago", false);
		
		ed1 = (EditText) findViewById(R.id.editText1);
		ed2 = (EditText) findViewById(R.id.editText2);
		ed3 = (EditText) findViewById(R.id.editText3);
		
		ed1.setText(descricao);
		ed2.setText(String.valueOf(valor));
		ed3.setText(String.valueOf(dataPagamento));
		
		b1 = (Button) findViewById(R.id.button1);
		b1.setOnClickListener(b1ClickListener);
		
		b2 = (Button) findViewById(R.id.button2);
		b2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AtualizarDespesaActivity.this.finish();
			}
		});
	}
	
	OnClickListener b1ClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {				
			String tDescricao = ed1.getText().toString();
			Double tValor = Double.parseDouble(ed2.getText().toString());
			String tData = ed3.getText().toString();
			
			String desc = "";
			for(int i = 0; i < tDescricao.length(); i++){
				if (!String.valueOf(tDescricao.charAt(i)).equals(" ")){
					desc += String.valueOf(tDescricao.charAt(i));
				}
				if (String.valueOf(tDescricao.charAt(i)).equals(" ")){
					desc += String.valueOf("_");
				}
			}
			
			String d = "";
			for(int i = 0; i < tData.length(); i++){
				if (!String.valueOf(tData.charAt(i)).equals("/")){
					d += String.valueOf(tData.charAt(i));
				}
			}
			
			
			String resourceURL = "http://10.0.2.2:2000/conta/" + id + "/update2/" + desc + "/" + tValor + "/" + d;
			
			AsyncHttpClient httpClient = new AsyncHttpClient();
			httpClient.put(resourceURL, new TextHttpResponseHandler() {
				@Override
				public void onSuccess(int status, Header[] headers, String responseString) {
					if(responseString != null && responseString.equals("conta atualizada")) {				
						
						Toast.makeText(AtualizarDespesaActivity.this, "Despesa atualizada com sucesso!", Toast.LENGTH_SHORT).show();
						Intent i = new Intent();
						setResult(RESULT_OK, i);
						AtualizarDespesaActivity.this.finish();
					}
					else {
						Toast.makeText(AtualizarDespesaActivity.this, "Erro ao atualizar despesa!", Toast.LENGTH_SHORT).show();						
					}
				}
				
				@Override
				public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
					// TODO Auto-generated method stub
				}
			});

		}
	};
	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.atualizar_despesa, menu);
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
