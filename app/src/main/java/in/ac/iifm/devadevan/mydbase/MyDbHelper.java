package in.ac.iifm.devadevan.mydbase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Devadevan on 29-11-2016.
 */

public class MyDbHelper extends SQLiteOpenHelper {

    private static final String DB = "iifm.db";
    private static final String TABLE_NAME="employee";
    private static final String COL_1="ID";
    private static final String COL_2="ename";
    private static final String COL_3="basic";
    private static final String COL_4="gradepay";

    public MyDbHelper(Context context) {
        super(context, DB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+" ("+COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT , "+COL_2+" TEXT NOT NULL , "+COL_3+" INTEGER NOT NULL , "+COL_4+" INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public long insertData(String ename, int basic, int gp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2, ename);
        cv.put(COL_3, basic);
        cv.put(COL_4, gp);
        long result= db.insert(TABLE_NAME,null,cv);
        return result;
    }

    public Cursor show_data() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c=db.rawQuery("select * from "+TABLE_NAME, null);
        return c;
    }

    public int updateData(String id, String ename, int basic, int gp){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_1, id);
        cv.put(COL_2, ename);
        cv.put(COL_3, basic);
        cv.put(COL_4, gp);
        int status=db.update(TABLE_NAME, cv ,"ID = ?",new String[] { id } );
        return status;
    }

    public int deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        int status=db.delete(TABLE_NAME,"id = ?", new String[] {id});
        return status;
    }
}
