package com.example.aps_meu_dinheiro_cliente;

import java.io.UnsupportedEncodingException;
import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.aps_meu_dinheiro_cliente.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrarDespesaActivity extends ActionBarActivity {

	EditText ed1, ed2, ed3;
	Button b1, b2, b3;
	long idusuario;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registrar_despesa);

		idusuario = getIntent().getLongExtra("idusuario", 0);
		
		ed1 = (EditText) findViewById(R.id.editText1);
		ed2 = (EditText) findViewById(R.id.editText2);
		ed3 = (EditText) findViewById(R.id.editText3);
		b1 = (Button) findViewById(R.id.button1);
		b1.setOnClickListener(b1ClickListener);
		b2 = (Button) findViewById(R.id.button2);
		b2.setOnClickListener(b2ClickListener);
		b3 = (Button) findViewById(R.id.button3);
		b3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				RegistrarDespesaActivity.this.finish();
			}
		});

	}
	
	@SuppressLint("ShowToast")
	OnClickListener b1ClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String tDescricao = ed1.getText().toString();
			Double tValor = Double.parseDouble(ed2.getText().toString());
			String tData = ed3.getText().toString();

			String resourceURL = "http://10.0.2.2:2000/conta";
			AsyncHttpClient httpClient = new AsyncHttpClient();

			// create Json object
			JSONObject params = new JSONObject();
			try {
				params.put("descricao", tDescricao);
				params.put("valor", tValor);
				params.put("data", tData);
				params.put("pago", false);
				params.put("tipo", "Despesa");
				params.put("idusuario", idusuario);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}

			StringEntity entity = null;
			try {
				entity = new StringEntity(params.toString());
				// indicate that the message sent is a json file
				entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			httpClient.post(RegistrarDespesaActivity.this, resourceURL, entity,
					"application/json", new JsonHttpResponseHandler() {
						public void onSuccess(int statusCode, Header[] headers,
								JSONObject response) {
							
							showSuccess(response);
						};
					});
		}
	};

	public void showSuccess(JSONObject response) {
		ed1.setText("");
		ed2.setText("");
		ed3.setText("");

		/*AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(RegistrarDespesaActivity.this);
		dialogBuilder.setMessage("Despesa Registrada com Sucesso!");
		dialogBuilder.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//RegistrarDespesaActivity.this.finish();
					}
				});
		dialogBuilder.show();*/
	}
	
	OnClickListener b2ClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String tDescricao = ed1.getText().toString();
			Double tValor = Double.parseDouble(ed2.getText().toString());
			String tData = ed3.getText().toString();

			String resourceURL = "http://10.0.2.2:2000/conta";
			AsyncHttpClient httpClient = new AsyncHttpClient();

			// create Json object
			JSONObject params = new JSONObject();
			try {
				params.put("descricao", tDescricao);
				params.put("valor", tValor);
				params.put("data", tData);
				params.put("pago", true);
				params.put("tipo", "Despesa");
				params.put("idusuario", idusuario);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}

			StringEntity entity = null;
			try {
				entity = new StringEntity(params.toString());
				// indicate that the message sent is a json file
				entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			httpClient.post(RegistrarDespesaActivity.this, resourceURL, entity,
					"application/json", new JsonHttpResponseHandler() {
						public void onSuccess(int statusCode, Header[] headers,
								JSONObject response) {
							
							showSuccess2(response);
						};
					});
			
		}
	};

	public void showSuccess2(JSONObject response) {		
		ed1.setText("");
		ed2.setText("");
		ed3.setText("");

		/*AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(RegistrarDespesaActivity.this);
		dialogBuilder.setMessage("Despesa Paga com Sucesso!");
		dialogBuilder.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//RegistrarDespesaActivity.this.finish();
					}
				});
		dialogBuilder.show();*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registrar_despesa, menu);
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
