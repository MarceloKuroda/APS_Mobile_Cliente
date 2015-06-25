package com.example.aps_meu_dinheiro_cliente;



import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SaldoActivity extends ActionBarActivity {

	Button b;
	double despesaFutura, despesaPaga, receitaFutura, receitaRecebida, saldoAtual, saldoTotal;
	TextView tv1, tv2, tv3, tv4, tv5, tv6;
	long idusuario;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saldo);
		
		idusuario = getIntent().getLongExtra("idusuario", 0);
		
		b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SaldoActivity.this.finish();
			}
		});
		
		tv1 = (TextView) findViewById(R.id.receita_futura);
		tv2 = (TextView) findViewById(R.id.receita_recebida);
		tv3 = (TextView) findViewById(R.id.despesa_futura);
		tv4 = (TextView) findViewById(R.id.despesa_paga);
		tv5 = (TextView) findViewById(R.id.saldo_atual);
		tv6 = (TextView) findViewById(R.id.saldo_total);

		saldoAtual = 0.d;
		saldoTotal = 0.d;
		
		somarSaldo();
		
	}
	
	private void somarSaldo() {
		String resourceURL = "http://10.0.2.2:2000/conta/"+idusuario;
		AsyncHttpClient httpClient = new AsyncHttpClient();

		httpClient.get(resourceURL, new JsonHttpResponseHandler() {
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray jsonArray) {
	 
				 receitaFutura = 0.0;
				 receitaRecebida = 0.0;
				 despesaFutura = 0.0;
				 despesaPaga = 0.0;
								
				for (int i = 0; i < jsonArray.length(); i++) {
					try {
						JSONObject obj = jsonArray.getJSONObject(i);
						if(obj.getString("tipo").equals("Receita") && obj.getBoolean("pago") == false){
							receitaFutura += obj.getDouble("valor");
						} else if(obj.getString("tipo").equals("Receita") && obj.getBoolean("pago") == true){
							receitaRecebida += obj.getDouble("valor");
						} else if(obj.getString("tipo").equals("Despesa") && obj.getBoolean("pago") == false){
							despesaFutura += obj.getDouble("valor");
						} else if(obj.getString("tipo").equals("Despesa") && obj.getBoolean("pago") == true){
							despesaPaga += obj.getDouble("valor");
						}
						
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				saldoAtual = receitaRecebida - despesaPaga;
				saldoTotal = saldoAtual + receitaFutura - despesaFutura;
				tv1.setText(String.valueOf(receitaFutura));
				tv2.setText(String.valueOf(receitaRecebida));
				tv3.setText(String.valueOf(despesaFutura));
				tv4.setText(String.valueOf(despesaPaga));
				tv5.setText(String.valueOf(saldoAtual));
				tv6.setText(String.valueOf(saldoTotal));
			}
		});
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.saldo, menu);
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
