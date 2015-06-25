package com.example.aps_meu_dinheiro_cliente;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
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
import android.support.v7.widget.PopupMenu;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class VisualizarDespesaFuturaActivity extends ActionBarActivity {

	Button b;
	ListView contaListView;
	ArrayList<Conta> conta = new ArrayList<Conta>();
	ArrayAdapter<Conta> adapter;
	int currentPosition;
	long idusuario;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visualizar_despesa_futura);
		
		idusuario = getIntent().getLongExtra("idusuario", 0);
		
		contaListView = (ListView) findViewById(R.id.listView1);
		contaListView.setOnItemClickListener(onItemListener);
		adapter = new ArrayAdapter<Conta>(VisualizarDespesaFuturaActivity.this,
				android.R.layout.simple_list_item_1, conta);
		contaListView.setAdapter(adapter);

		b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				VisualizarDespesaFuturaActivity.this.finish();
			}
		});

		updateListView();
		
	}
	
	private void updateListView() {
		String resourceURL = "http://10.0.2.2:2000/conta/Despesa/false/"+idusuario;
		AsyncHttpClient httpClient = new AsyncHttpClient();

		httpClient.get(resourceURL, new JsonHttpResponseHandler() {
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray jsonArray) {

				conta.clear();

				for (int i = 0; i < jsonArray.length(); i++) {
					try {
						JSONObject obj = jsonArray.getJSONObject(i);
						Conta a = new Conta(obj.getLong("id"), obj.getLong("idusuario"), obj.getString("tipo"), obj.getString("descricao"), obj.getDouble("valor"), obj.getString("data"), obj.getBoolean("pago"));
						conta.add(a);

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				adapter.notifyDataSetChanged();
			}
		});
	}

	private OnItemClickListener onItemListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			mostrarPopupMenu(view, position);
		}
	};

	private void mostrarPopupMenu(View v, final int position) {
		PopupMenu popup = new PopupMenu(this, v);

		popup.getMenuInflater().inflate(R.menu.despesa_futura_menu, popup.getMenu());

		popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
				case R.id.item1:
					Conta ac = conta.get(position);
					Intent i = new Intent(VisualizarDespesaFuturaActivity.this,	AtualizarDespesaActivity.class);
					i.putExtra("id", ac.getId());
					i.putExtra("descricao", ac.getDescricao());
					i.putExtra("valor", ac.getValor());
					i.putExtra("dataPagamento", ac.getData());
					i.putExtra("pago", ac.getPago());
					startActivityForResult(i, 2);
					return true;

				case R.id.item2:
					Conta m = conta.get(position);
					long id = m.getId();
					String resourceURL = "http://10.0.2.2:2000/conta/" + id;

					AsyncHttpClient httpClient = new AsyncHttpClient();
					httpClient.delete(resourceURL,
							new TextHttpResponseHandler() {
								@Override
								public void onSuccess(int status,
										Header[] headers, String responseString) {
									if (responseString != null
											&& responseString
													.equals("conta removida")) {
										Toast.makeText(
												VisualizarDespesaFuturaActivity.this,
												"Despesa removida com sucesso!",
												Toast.LENGTH_SHORT).show();
										updateListView();
									} else {
										Toast.makeText(
												VisualizarDespesaFuturaActivity.this,
												"Erro ao remover a despesa: "
														+ responseString,
												Toast.LENGTH_SHORT).show();
									}
								}

								@Override
								public void onFailure(int arg0, Header[] arg1,
										String arg2, Throwable arg3) {
									// TODO Auto-generated method stub
								}
							});
					return true;

				case R.id.item3:
					Conta d = conta.get(position);
					long id2 = d.getId();
					
					// create Json object
					String resourceURL2 = "http://10.0.2.2:2000/conta/"+ id2 + "/update/true";
					AsyncHttpClient httpClient2 = new AsyncHttpClient();
					httpClient2.put(resourceURL2, new TextHttpResponseHandler() {
						@Override
						public void onSuccess(int status, Header[] headers, String responseString) {
							if(responseString != null && responseString.equals("lista atualizada")) {
								updateListView();
								Toast.makeText(VisualizarDespesaFuturaActivity.this, "Despesa paga com sucesso!", Toast.LENGTH_SHORT).show();
							}
							else {
								Toast.makeText(VisualizarDespesaFuturaActivity.this, "Erro ao pagar!", Toast.LENGTH_SHORT).show();						
							}
						}
						
						@Override
						public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
							// TODO Auto-generated method stub
						}
					});
					return true;

				default:
					return false;
				}
			}
		});

		popup.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 2 && resultCode == RESULT_OK) {
			String ok = (String) data.getSerializableExtra("ok");
		}
		updateListView();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.visualizar_despesa_futura, menu);
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
