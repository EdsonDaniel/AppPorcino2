package com.example.appporcino.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class BaseDatosGranja extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "granja.db";
    private static final String SQL_CREATE_PORCINO =
            "CREATE TABLE PORCINO(" + BaseColumns._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "nombre varchar(100), fecha_nac varchar(12)," +
                    "foto_url varchar(100),genero char, raza varchar(100)," +
                    "tipo varchar(20),descripcion text, fecha_compra varchar(12)," +
                    "fecha_venta varchar(12), peso double,costo double, " +
                    "estado varchar(40),numero integer)";
    private static final String SQL_CREATE_GASTO =
            "CREATE TABLE GASTO(" + BaseColumns._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "concepto varchar(100), fecha_pago varchar(12)," +
                    "monto double, descripcion text)";
    private static final String SQL_CREATE_RECORDATORIO =
            "CREATE TABLE RECORDATORIO(" + BaseColumns._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "titulo varchar(100), fecha_record varchar(12)," +
                    "hora_record varchar(8), descripcion text)";
    private static final String SQL_CREATE_EVENTO =
            "CREATE TABLE EVENTO(" + BaseColumns._ID+" INTEGER PRIMARY KEY,"+
                    "titulo text, descripcion text, allday integer, " +
                    "fecha_inicio text, fecha_fin text, ubicacion text," +
                    "tipo text, recordatorio integer)";
    private static final String SQL_CREATE_VENTA =
            "CREATE TABLE VENTA(" + BaseColumns._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "fecha_venta varchar(12), cliente text, monto double," +
                    "notas text)";
    private static final String SQL_CREATE_DETALLE_VENTA =
            "CREATE TABLE DETALLE_VENTA(id_venta integer, monto double, id_porcino integer)";
    private static final String SQL_CREATE_MONTA_S =
            "CREATE TABLE MONTA_S(" + BaseColumns._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "fecha_monta varchar(12), cliente text, monto double, " +
                    "nat integer," +
                    "id_porcino integer, notas text, hembra text)";
    private static final String SQL_CREATE_MONTA_H =
            "CREATE TABLE MONTA_H(" + BaseColumns._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "fecha_monta varchar(12), semental text, nat integer," +
                    "id_porcino integer, notas text)";
    private static final String SQL_CREATE_PARTO =
            "CREATE TABLE PARTO(" + BaseColumns._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "fecha_parto varchar(12), fecha_monta varchar(12),fecha_destete varchar(12)," +
                    "semental text, nat integer, exitoso integer," +
                    "id_porcino integer, total_lechones integer, vivos integer, notas text)";

    private static final String SQL_CREATE_USUARIO =
            "CREATE TABLE USUARIO(" + BaseColumns._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "nombre varchar(100), email varchar(100),password varchar(100)," +
                    "int sexo)";


    private static final String SQL_DELETE_PORCINO =
            "DROP TABLE IF EXISTS PORCINO";
    private static final String SQL_DELETE_USUARIO =
            "DROP TABLE IF EXISTS USUARIO";
    private static final String SQL_DELETE_GASTO =
            "DROP TABLE IF EXISTS GASTO";
    private static final String SQL_DELETE_RECORDATORIO =
            "DROP TABLE IF EXISTS RECORDATORIO";
    private static final String SQL_DELETE_EVENTO =
            "DROP TABLE IF EXISTS EVENTO";
    private static final String SQL_DELETE_VENTA =
            "DROP TABLE IF EXISTS VENTA";
    private static final String SQL_DELETE_MONTA_S =
            "DROP TABLE IF EXISTS MONTA_S";
    private static final String SQL_DELETE_MONTA_C =
            "DROP TABLE IF EXISTS MONTA_C";
    private static final String SQL_DELETE_DETALLE_VENTA =
            "DROP TABLE IF EXISTS DETALLE_VENTA";
    private static final String SQL_DELETE_PARTO =
            "DROP TABLE IF EXISTS PARTO";

    public BaseDatosGranja(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PORCINO);
        db.execSQL(SQL_CREATE_GASTO);
        db.execSQL(SQL_CREATE_RECORDATORIO);
        db.execSQL(SQL_CREATE_EVENTO);
        db.execSQL(SQL_CREATE_VENTA);
        db.execSQL(SQL_CREATE_DETALLE_VENTA);
        db.execSQL(SQL_CREATE_MONTA_H);
        db.execSQL(SQL_CREATE_MONTA_S);
        db.execSQL(SQL_CREATE_PARTO);
        db.execSQL(SQL_CREATE_USUARIO);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PORCINO);
        db.execSQL(SQL_DELETE_GASTO);
        db.execSQL(SQL_DELETE_RECORDATORIO);
        db.execSQL(SQL_DELETE_EVENTO);
        db.execSQL(SQL_DELETE_VENTA);
        db.execSQL(SQL_DELETE_DETALLE_VENTA);
        db.execSQL(SQL_DELETE_MONTA_C);
        db.execSQL(SQL_DELETE_MONTA_S);
        db.execSQL(SQL_DELETE_PARTO);
        db.execSQL(SQL_DELETE_USUARIO);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}