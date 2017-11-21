package estudo.com.br.sourseinventory.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import estudo.com.br.sourseinventory.modelo.Dados;

/**
 * Created by mayke on 10/11/2017.
 */

public class InventarioDAO extends SQLiteOpenHelper {
    public InventarioDAO(Context context) {
        super(context,"Sourse Inventory", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       // String sql = "CREATE TABLE Usuario (id INTEGER PRIMARY KEY, email TEXT NOT NULL, senha TEXT NOT NULL);";
        String sql = "CREATE TABLE Inventario (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, tipo TEXT NOT NULL," +
                " genero TEXT NOT NULL, imdb TEXT NOT NULL, nota REAL NOT NULL, caminhoFoto TEXT);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int i1) {
        String sql = "";
        switch (oldVersion){
            case 1:
                sql = "ALTER TABLE Inventario ADD COLUMN caminhoFoto TEXT";
                sqLiteDatabase.execSQL(sql);
        }

    }

    public void insere(Dados dados) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues valores = pegaDadosDoDados(dados);

        db.insert("Inventario", null, valores);
    }

    @NonNull
    private ContentValues pegaDadosDoDados(Dados dados) {
        ContentValues valores = new ContentValues();
        valores.put("nome", dados.getNome());
        valores.put("tipo", dados.getTipo());
        valores.put("genero", dados.getGenero());
        valores.put("imdb", dados.getImdb());
        valores.put("nota", dados.getNota());
        valores.put("caminhoFoto", dados.getCaminhoFoto());
        return valores;
    }

    public List<Dados> buscaDados() {
        String sql = "SELECT * FROM Inventario";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Dados> dados = new ArrayList<Dados>();
        while (c.moveToNext()){
            Dados dado = new Dados();
            dado.setId(c.getLong(c.getColumnIndex("id")));
            dado.setNome(c.getString(c.getColumnIndex("nome")));
            dado.setTipo(c.getString(c.getColumnIndex("tipo")));
            dado.setGenero(c.getString(c.getColumnIndex("genero")));
            dado.setImdb(c.getString(c.getColumnIndex("imdb")));
            dado.setNota(c.getDouble(c.getColumnIndex("nota")));
            dado.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));

            dados.add(dado);
        }
        c.close();

        return dados;
    }

    public void exclui(Dados dados) {

        SQLiteDatabase db = getWritableDatabase();

        String[] params = {String.valueOf(dados.getId())};
        db.delete("Inventario", "id= ?", params);
    }

    public void altera(Dados dados) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues valores = pegaDadosDoDados(dados);
        String[] params = {String.valueOf(dados.getId())};
        db.update("Inventario", valores, "id= ?", params);

    }
}
