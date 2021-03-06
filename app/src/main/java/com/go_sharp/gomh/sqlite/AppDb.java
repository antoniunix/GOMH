package com.go_sharp.gomh.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.go_sharp.gomh.R;
import com.go_sharp.gomh.contextApp.ContextApp;

/**
 * Created by gnu on 22/02/17.
 */

public class AppDb extends SQLiteOpenHelper {

    private Tables tables;
    private Context context;

    public AppDb() {
        super(ContextApp.context, ContextApp.context.getString(R.string.app_db_name), null, ContextApp.context.getResources().getInteger(R.integer.app_version_db));
        tables = new Tables();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tables.TableSepomex);
        db.execSQL(tables.TABLE_PDV);
        db.execSQL(tables.TABLE_SCHEDULE);
        db.execSQL(tables.TableReportGeo);
        db.execSQL(tables.TablePolitics);
        db.execSQL(tables.TableReportReport);
        db.execSQL(tables.TableReportCheck);

        db.execSQL(tables.Table_EAEncuesta);
        db.execSQL(tables.Table_EAGrupo);
        db.execSQL(tables.Table_EAOpcionPregunta);
        db.execSQL(tables.Table_EAPregunta);
        db.execSQL(tables.Table_EARespuesta);
        db.execSQL(tables.Table_EASeccion);
        db.execSQL(tables.Table_EATipoPregunta);
        db.execSQL(tables.Table_EARespuestaRT);
        db.execSQL(tables.TableEAAnswerPdv);


        db.execSQL(tables.TablePhoto);
        db.execSQL(tables.TableImageLogin);

        //Census
        db.execSQL(tables.TableCensus);
        db.execSQL(tables.TableDownloadFile);
        //notifications
        db.execSQL(tables.TableMessage);
        db.execSQL(tables.TableTypePublicity);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        switch (oldVersion) {
            case 1:

            default:
                break;
        }

    }
}
