package br.com.osmarjunior.notepadshifpapp.model;

import com.google.gson.annotations.SerializedName;

public class Nota {

    private String id;

    private String titulo;

    @SerializedName(value = "texto")
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
