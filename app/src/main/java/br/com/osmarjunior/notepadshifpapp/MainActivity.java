package br.com.osmarjunior.notepadshifpapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.squareup.picasso.Picasso;

import br.com.osmarjunior.notepadshifpapp.api.NotaApi;
import br.com.osmarjunior.notepadshifpapp.model.Nota;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText etTitulo;
    private EditText etTexto;
    private ImageView ivLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);

        etTitulo = (EditText) findViewById(R.id.etTitulo);
        etTexto = (EditText) findViewById(R.id.etTexto);
        ivLogo = (ImageView) findViewById(R.id.ivLogo);

        Picasso.with(this)
                .load("http://s3.amazonaws.com/design_images/images/1129/Lobo.png?1346204175")
                .placeholder(R.drawable.loading)
                .error(R.drawable.erro)
                .into(ivLogo);
    }
    public void pesquisar(View v){
        NotaApi api = getRetrofit().create(NotaApi.class);
        api.buscarNota(etTitulo.getText().toString())
                .enqueue(new Callback<Nota>() {
                    @Override
                    public void onResponse(Call<Nota> call, Response<Nota> response) {
                        etTexto.setText(response.body().getDescricao());
                    }

                    @Override
                    public void onFailure(Call<Nota> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Ops! Deu ruim", Toast.LENGTH_LONG).show();
                    }
                });
    }
    public void salvar(final View v){
        final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "", "Aguarde! Estamos gravando os seus dados", true);

        dialog.show();

        NotaApi api = getRetrofit().create(NotaApi.class);
        Nota nota = new  Nota();
        nota.setTitulo(etTitulo.getText().toString());
        nota.setDescricao(etTexto.getText().toString());

        api.salvar(nota)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(MainActivity.this, "Nota Cadastrada com Sucesso!", Toast.LENGTH_LONG).show();
                        limpar();
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Ops! Deu ruim ao Cadastrar!", Toast.LENGTH_LONG).show();
                    }
                });
    }
    public void limpar(View v){
        limpar();
    }

    private void limpar() {
        etTitulo.setText("");
        etTexto.setText("");
    }
    private Retrofit getRetrofit(){
        OkHttpClient client = new OkHttpClient.Builder()
                                    .addNetworkInterceptor(new StethoInterceptor())
                                    .build();

        return new Retrofit.Builder()
                .baseUrl("https://notepadcloudshiftosmarjunior.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}
