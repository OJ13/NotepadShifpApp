package br.com.osmarjunior.notepadshifpapp.api;

import br.com.osmarjunior.notepadshifpapp.model.Nota;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NotaApi {

    @GET("/nota/titulo/{titulo}")
    Call<Nota> buscarNota(@Path(value = "titulo") String titulo);

    @POST("/nota")
    Call<Void> salvar(@Body Nota nota);
}
