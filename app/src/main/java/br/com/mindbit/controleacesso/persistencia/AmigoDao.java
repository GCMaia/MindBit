package br.com.mindbit.controleacesso.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.mindbit.controleacesso.dominio.Amigo;
import br.com.mindbit.controleacesso.negocio.SessaoUsuario;
import br.com.mindbit.infra.gui.MindbitException;

/**
 * Classe do banco de amigo
 */
public class AmigoDao {
    private static DatabaseHelper databaseHelper;
    private static AmigoDao instanciaAmigoDao = new AmigoDao();
    private SessaoUsuario sessaoUsuario = SessaoUsuario.getInstancia();

    private AmigoDao(){}
    /* singleton */
    public static AmigoDao getInstancia(Context contexto) {
        AmigoDao.databaseHelper = new DatabaseHelper(contexto);
        return instanciaAmigoDao;
    }

    /**
     * metodo utilizado para criar o objeto amigo
     *
     * @param cursor cursor a ser usado na criacao do objeto amigo
     * @return  objeto amigo preenchido
     */
    private Amigo criarAmigo(Cursor cursor){
        Amigo amigo = new Amigo();
        amigo.setId(cursor.getInt(0));
        amigo.setNome(cursor.getString(1));
        amigo.setEmail(cursor.getString(2));
        return amigo;
    }

    /**
     * metodo utilizado para adicionar o objeto amigo ao banco
     *
     * @param amigo amigo que sera adicionado
     */
    public void addAmigo(Amigo amigo){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.AMIGO_NOME, amigo.getNome());
        values.put(DatabaseHelper.AMIGO_EMAIL, amigo.getEmail());
        int idPessoaUsuario = SessaoUsuario.getInstancia().getPessoaLogada().getId();
        values.put(DatabaseHelper.ID_PESSOA_USUARIO, idPessoaUsuario);

        db.insert(DatabaseHelper.TABELA_AMIGO, null, values);
        db.close();
    }


    /**
     * metodo utilizado para fazer a busca do objeto amigo no banco
     *
     * @param email email que sera utilizado na busca
     * @return      amigo encontrado quanto ao email fornecido
     * @throws MindbitException
     */
    public Amigo buscarAmigoPorEmail(String email) throws MindbitException {
        int idPessoaLogada = sessaoUsuario.getPessoaLogada().getId();
        Amigo amigo = null;
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABELA_AMIGO +
                " WHERE " + DatabaseHelper.AMIGO_EMAIL + " =? AND " + databaseHelper.ID_PESSOA_USUARIO + " =?", new String[]{email, String.valueOf(idPessoaLogada)});
        if (cursor.moveToFirst()) {
            amigo = criarAmigo(cursor);
        }
        db.close();
        cursor.close();
        return amigo;
    }

    /**
     * metodo utilizado para pegar o id do usuario em sessao e listar os amigos desse usuario
     *
     * @param id    id do usuario que tera os amigos listados
     * @return      lista com os amigos do usuario
     * @throws MindbitException
     */
    public List<Amigo> listarAmigos(int id) throws MindbitException {
        Amigo amigo = null;
        List<Amigo> listaAmigos = new ArrayList<Amigo>();

        SQLiteDatabase db=databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ databaseHelper.TABELA_AMIGO +" WHERE " +
                databaseHelper.ID_PESSOA_USUARIO+ " =?", new String[]{String.valueOf(id)});

        while (cursor.moveToNext()){
            amigo = criarAmigo(cursor);
            listaAmigos.add(amigo);
        }

        db.close();
        cursor.close();
        return listaAmigos;
    }
}
