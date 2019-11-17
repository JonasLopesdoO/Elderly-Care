package com.ufc.quixada.elderlycare.Configuracao;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferencias {
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String NOME_ARQUIVO = "TipoCodigoUsuario";
    private final String CHAVE_TIPO_USUARIO = "tipoUsuario";
    private final String CHAVE_CODIGO = "codigoIdoso";

    public Preferencias(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(NOME_ARQUIVO, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void salvarUsuarioPreferencias(String tipoUsuario, Integer codigoIdoso){
        editor.putString(CHAVE_TIPO_USUARIO, tipoUsuario);
        editor.putInt(CHAVE_CODIGO, codigoIdoso);
        editor.commit();
    }

    public String getTipoUsuario(){
        return preferences.getString(CHAVE_TIPO_USUARIO, null);
    }

    public Integer getCodigoIdoso(){
        return preferences.getInt(CHAVE_CODIGO, 0);
    }
}
