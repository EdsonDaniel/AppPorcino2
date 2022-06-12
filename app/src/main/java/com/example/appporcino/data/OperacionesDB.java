package com.example.appporcino.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.appporcino.Modelo.Detalle_venta;
import com.example.appporcino.Modelo.Evento;
import com.example.appporcino.Modelo.Gasto;
import com.example.appporcino.Modelo.MontaS;
import com.example.appporcino.Modelo.MontasH;
import com.example.appporcino.Modelo.Parto;
import com.example.appporcino.Modelo.Porcino;
import com.example.appporcino.Modelo.Recordatorio;
import com.example.appporcino.Modelo.Venta;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public final class OperacionesDB {
    private static BaseDatosGranja database;
    private static OperacionesDB instancia = new OperacionesDB();
    private static String ID = BaseColumns._ID;
    private static String NOMBRE = "nombre";
    private static String FECHA_NAC = "fecha_nac";
    private static String FOTO = "foto_url";
    private static String GENERO = "genero";
    private static String RAZA = "raza";
    private static String FECHA_COMPRA = "fecha_compra";
    private static String FECHA_VENTA = "fecha_venta";
    private static String FECHA_RECORD = "fecha_record";
    private static String HORA_RECORD = "hora_record";
    private static String PESO = "peso";
    private static String ESTADO = "estado";
    private static String COSTO = "costo";
    private static String DESCRIPCION = "descripcion";
    private static String NUM_MONTAS = "num_montas";
    private static String NUM_CRIAS = "num_crias";
    private static String TIPO = "tipo";
    private static String NUMERO = "numero";
    private static String CONCEPTO = "concepto";
    private static String FECHA_PAGO = "fecha_pago";
    private static String MONTO = "monto";
    private static String TITULO = "titulo";
    private static String ALLDAY = "allday";
    private static String FECHA_INICIO = "fecha_inicio";
    private static String FECHA_FIN = "fecha_fin";
    private static String FECHA_MONTA = "fecha_monta";
    private static String FECHA_PARTO = "fecha_parto";
    private static String FECHA_DESTETE = "fecha_destete";
    private static String UBICACION = "ubicacion";
    private static String RECORDATORIO = "recordatorio";
    private static String CLIENTE = "cliente";
    private static String NOTAS = "notas";
    private static String ID_VENTA = "id_venta";
    private static String ID_PORCINO = "id_porcino";
    private static String NAT = "nat";
    private static String SEMENTAL = "semental";
    private static String HEMBRA = "hembra";
    private static String EXITOSO = "exitoso";
    private static String TOTAL_LECHONES = "total_lechones";
    private static String VIVOS = "vivos";

    private static Context context;

    private OperacionesDB(){}
    public static OperacionesDB getInstancia(Context contexto) {
        context = contexto;
        if (database == null) {
            database = new BaseDatosGranja(contexto);
        }
        return instancia;
    }

    public int getCountEjemplares(int id_venta){
        SQLiteDatabase db = database.getWritableDatabase();
        Cursor mCount= db.rawQuery("select count(*) from DETALLE_VENTA where id_venta=" + id_venta, null);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();
        return count;
    }
    public ArrayList<Porcino> getVendidos(int id_venta){
        SQLiteDatabase db = database.getWritableDatabase();
        ArrayList<Detalle_venta> detalle = selectDetalleVenta(id_venta);
        ArrayList<Porcino> porkis = new ArrayList<Porcino>();
        for(int i = 0; i<detalle.size();i++){
            Porcino p = buscarPorcino(detalle.get(i).getIdporcino());
            porkis.add(p);
        }
        return porkis;
    }
    public Porcino buscarPorcino(int id){
        SQLiteDatabase db = database.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                NOMBRE, FECHA_NAC,FOTO,GENERO,RAZA, TIPO, DESCRIPCION, FECHA_COMPRA,
                FECHA_VENTA, PESO, COSTO, ESTADO, NUMERO
        };
        String selection = ID + " = ?";
        String[] selectionArgs = {""+id};
        Cursor cursor = db.query("PORCINO",projection,selection,selectionArgs,
                null,null, null);
        //Porcino p = new ArrayList<>();
        cursor.moveToFirst();
        long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
        String nombre = cursor.getString(cursor.getColumnIndexOrThrow(NOMBRE));
        String fech_n = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_NAC));
        String foto   = cursor.getString(cursor.getColumnIndexOrThrow(FOTO));
        String genero = cursor.getString(cursor.getColumnIndexOrThrow(GENERO));
        String raza   = cursor.getString(cursor.getColumnIndexOrThrow(RAZA));
        String tipo = cursor.getString(cursor.getColumnIndexOrThrow(TIPO));
        String descrip = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPCION));
        String fech_c = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_COMPRA));
        String fech_v = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_VENTA));
        double peso = cursor.getDouble(cursor.getColumnIndexOrThrow(PESO));
        double costo = cursor.getDouble(cursor.getColumnIndexOrThrow(COSTO));
        String estado = cursor.getString(cursor.getColumnIndexOrThrow(ESTADO));
        int    numero = cursor.getInt(cursor.getColumnIndexOrThrow(NUMERO));
        Porcino p = new Porcino(nombre,fech_n,tipo,foto,genero,raza,
                descrip,fech_c,fech_v,peso,costo,estado,numero,itemId);
        return p;
    }
    public long insertarPorcino(Porcino p){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOMBRE, p.getNombre());
        values.put(FECHA_NAC, p.getFecha_nac());
        values.put(FOTO, p.getFoto_url());
        values.put(GENERO, p.getGenero());
        values.put(RAZA, p.getRaza());
        values.put(TIPO,p.getTipo());
        values.put(DESCRIPCION, p.getDescripcion());
        values.put(FECHA_COMPRA, p.getFecha_compra());
        values.put(FECHA_VENTA, p.getFecha_venta());
        values.put(PESO, p.getPeso());
        values.put(COSTO, p.getCosto());
        values.put(ESTADO, p.getEstado());
        values.put(NUMERO, p.getNumero());
        return db.insert("PORCINO", null, values);
    }
    public ArrayList<Porcino> selectPorcino(){
        SQLiteDatabase db = database.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                NOMBRE, FECHA_NAC,FOTO,GENERO,RAZA, TIPO, DESCRIPCION, FECHA_COMPRA,
                FECHA_VENTA, PESO, COSTO, ESTADO, NUMERO
        };
        String sortOrder = NOMBRE + " ASC";
        Cursor cursor = db.query("PORCINO",projection,null,null,
                null,null, sortOrder);
        ArrayList<Porcino> porcinos = new ArrayList<>();
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
            String nombre = cursor.getString(cursor.getColumnIndexOrThrow(NOMBRE));
            String fech_n = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_NAC));
            String foto   = cursor.getString(cursor.getColumnIndexOrThrow(FOTO));
            String genero = cursor.getString(cursor.getColumnIndexOrThrow(GENERO));
            String raza   = cursor.getString(cursor.getColumnIndexOrThrow(RAZA));
            String tipo = cursor.getString(cursor.getColumnIndexOrThrow(TIPO));
            String descrip = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPCION));
            String fech_c = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_COMPRA));
            String fech_v = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_VENTA));
            double peso = cursor.getDouble(cursor.getColumnIndexOrThrow(PESO));
            double costo = cursor.getDouble(cursor.getColumnIndexOrThrow(COSTO));
            String estado = cursor.getString(cursor.getColumnIndexOrThrow(ESTADO));
            int    numero = cursor.getInt(cursor.getColumnIndexOrThrow(NUMERO));

            Porcino p = new Porcino(nombre,fech_n,tipo,foto,genero,raza,
                    descrip,fech_c,fech_v,peso,costo,estado,numero,itemId);
            p.calcularEstado(context);
            porcinos.add(p);
        }
        cursor.close();
        return porcinos;
    }
    public boolean updatePorcino(Porcino p){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOMBRE, p.getNombre());
        values.put(FECHA_NAC, p.getFecha_nac());
        values.put(FOTO, p.getFoto_url());
        values.put(GENERO, p.getGenero());
        values.put(RAZA, p.getRaza());
        values.put(TIPO,p.getTipo());
        values.put(DESCRIPCION, p.getDescripcion());
        values.put(FECHA_COMPRA, p.getFecha_compra());
        values.put(FECHA_VENTA, p.getFecha_venta());
        values.put(PESO, p.getPeso());
        values.put(COSTO, p.getCosto());
        values.put(ESTADO, p.getEstado());
        values.put(NUMERO, p.getNumero());

        String whereClause = String.format("%s=?", ID);
        String[] whereArgs = {""+p.getId()};

        int resultado = db.update("PORCINO", values, whereClause, whereArgs);
        return resultado > 0;
    }
    public boolean deletePorcino(Porcino p){
        SQLiteDatabase db = database.getWritableDatabase();
        String whereClause = String.format("%s=?", ID);
        String[] whereArgs = {""+p.getId()};
        // Issue SQL statement.
        int deletedRows = db.delete("PORCINO", whereClause, whereArgs);
        return deletedRows>0;
    }
    public ArrayList<Porcino> selectPorcinoVentas(){
        SQLiteDatabase db = database.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                NOMBRE, FECHA_NAC,FOTO,GENERO,RAZA, TIPO, DESCRIPCION, FECHA_COMPRA,
                FECHA_VENTA, PESO, COSTO, ESTADO, NUMERO
        };
        String sortOrder = NOMBRE + " ASC";
        Cursor cursor = db.query("PORCINO",projection,null,null,
                null,null, sortOrder);
        ArrayList<Porcino> porcinos = new ArrayList<>();
        //ArrayList<CharSequence> names = new ArrayList<>();
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
            String nombre = cursor.getString(cursor.getColumnIndexOrThrow(NOMBRE));
            String fech_n = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_NAC));
            String foto   = cursor.getString(cursor.getColumnIndexOrThrow(FOTO));
            String genero = cursor.getString(cursor.getColumnIndexOrThrow(GENERO));
            String raza   = cursor.getString(cursor.getColumnIndexOrThrow(RAZA));
            String tipo = cursor.getString(cursor.getColumnIndexOrThrow(TIPO));
            String descrip = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPCION));
            String fech_c = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_COMPRA));
            String fech_v = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_VENTA));
            double peso = cursor.getDouble(cursor.getColumnIndexOrThrow(PESO));
            double costo = cursor.getDouble(cursor.getColumnIndexOrThrow(COSTO));
            String estado = cursor.getString(cursor.getColumnIndexOrThrow(ESTADO));
            int    numero = cursor.getInt(cursor.getColumnIndexOrThrow(NUMERO));
            Porcino p = new Porcino(nombre,fech_n,tipo,foto,genero,raza,
                    descrip,fech_c,fech_v,peso,costo,estado,numero,itemId);
            p.calcularEstado(context);
            if(!estado.equalsIgnoreCase("VENDIDO") && p.getEdadDias()>50){
                porcinos.add(p);
            }
        }
        cursor.close();
        return porcinos;
    }
    public ArrayList<Porcino> countEnGranja(){
        SQLiteDatabase db = database.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                NOMBRE, FECHA_NAC,FOTO,GENERO,RAZA, TIPO, DESCRIPCION, FECHA_COMPRA,
                FECHA_VENTA, PESO, COSTO, ESTADO, NUMERO
        };
        String selection = ESTADO + " != ?";
        String[] selectionArgs = {"VENDIDO"};
        Cursor cursor = db.query("PORCINO",projection,selection,selectionArgs,
                null,null, null);
        ArrayList<Porcino> porcinos = new ArrayList<>();
        //ArrayList<CharSequence> names = new ArrayList<>();
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
            String nombre = cursor.getString(cursor.getColumnIndexOrThrow(NOMBRE));
            String fech_n = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_NAC));
            String foto   = cursor.getString(cursor.getColumnIndexOrThrow(FOTO));
            String genero = cursor.getString(cursor.getColumnIndexOrThrow(GENERO));
            String raza   = cursor.getString(cursor.getColumnIndexOrThrow(RAZA));
            String tipo = cursor.getString(cursor.getColumnIndexOrThrow(TIPO));
            String descrip = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPCION));
            String fech_c = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_COMPRA));
            String fech_v = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_VENTA));
            double peso = cursor.getDouble(cursor.getColumnIndexOrThrow(PESO));
            double costo = cursor.getDouble(cursor.getColumnIndexOrThrow(COSTO));
            String estado = cursor.getString(cursor.getColumnIndexOrThrow(ESTADO));
            int    numero = cursor.getInt(cursor.getColumnIndexOrThrow(NUMERO));
            Porcino p = new Porcino(nombre,fech_n,tipo,foto,genero,raza,
                    descrip,fech_c,fech_v,peso,costo,estado,numero,itemId);
        }
        cursor.close();
        return porcinos;
    }

    public ArrayList<Object> notificaciones(){
        ArrayList<Evento> evento = selectEventos();
        ArrayList<Recordatorio> record = selectRecord();
        ArrayList<Object> datos = new ArrayList<>();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        for(int i = 0; i<evento.size();i++){
            if (fecha_reciente(evento.get(i).getFecha_inicio(),now,calendar)){
                datos.add(evento.get(i));
            }
        }
        for(int i = 0; i<record.size();i++){
            if (fecha_reciente(record.get(i).getFecha(),now,calendar)){
                datos.add(record.get(i));
            }
        }
     return datos;
    }
    private boolean fecha_reciente(String fecha, Date now, Calendar calendar){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date a_comparar;
        Date max;
        try{
            a_comparar = formatter.parse(fecha);
        } catch (ParseException e) {
            return false;
        }
        if(a_comparar.before(now)){
            return false;
        }
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_YEAR,8);
        max = calendar.getTime();
        if(a_comparar.before(max)){
            return true;
        }
        return false;
    }
    public ArrayList<ArrayList<Porcino>> getConjuto(){
        ArrayList<ArrayList<Porcino>> conjuntos = new ArrayList<ArrayList<Porcino>>();
        ArrayList<Porcino> todos = selectPorcino();
        ArrayList<Porcino> hembras = new ArrayList<Porcino>();
        ArrayList<Porcino> sementales = new ArrayList<Porcino>();
        ArrayList<Porcino> engorda = new ArrayList<Porcino>();
        ArrayList<Porcino> vendidos = new ArrayList<Porcino>();
        for (int i = 0; i<todos.size();i++){
            if(todos.get(i).getEstado().equalsIgnoreCase("VENDIDO")){
                vendidos.add(todos.get(i));
            }else {
                switch(todos.get(i).getTipo()) {
                    case "Engorda":
                        engorda.add(todos.get(i));
                        break;
                    case "Semental":
                        sementales.add(todos.get(i));
                        break;
                    case "Cría":
                        hembras.add(todos.get(i));
                        break;
                    default:
                        break;
                }
            }
        }
        conjuntos.add(hembras);
        conjuntos.add(engorda);
        conjuntos.add(sementales);
        conjuntos.add(vendidos);
        return conjuntos;
    }
    public double montoTotalVentas(){
        ArrayList<Venta> ventas = selectVenta();
        double total = 0;
        for (int i = 0; i<ventas.size();i++){
            total += ventas.get(i).getMonto();
        }
        return total;
    }
    public double montoCruzas(){
        ArrayList<MontaS> montas = selectMontaS();
        double total = 0;
        for (int i = 0; i<montas.size();i++){
            total += montas.get(i).getMonto();
        }
        return total;
    }
    
    public ArrayList<Gasto> selectGasto(){
        SQLiteDatabase db = database.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                CONCEPTO, FECHA_PAGO, MONTO, DESCRIPCION
        };
        String sortOrder = FECHA_PAGO + " DESC";
        Cursor cursor = db.query("GASTO",projection,null,null,
                null,null, sortOrder);
        ArrayList<Gasto> gastos = new ArrayList<>();
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
            String concepto = cursor.getString(cursor.getColumnIndexOrThrow(CONCEPTO));
            String fech_pago = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_PAGO));
            double monto = cursor.getDouble(cursor.getColumnIndexOrThrow(MONTO));
            String descrip = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPCION));

            Gasto p = new Gasto(itemId,concepto,fech_pago,monto,descrip);
            gastos.add(p);
        }
        cursor.close();
        return gastos;
    }
    public long insertarGasto(Gasto p){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CONCEPTO, p.getConcepto());
        values.put(FECHA_PAGO, p.getFecha_pago());
        values.put(MONTO, p.getMonto());
        values.put(DESCRIPCION, p.getDescripcion());
        return db.insert("GASTO", null, values);
    }
    public boolean updateGasto(Gasto p){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CONCEPTO, p.getConcepto());
        values.put(FECHA_PAGO, p.getFecha_pago());
        values.put(MONTO, p.getMonto());
        values.put(DESCRIPCION, p.getDescripcion());

        String whereClause = String.format("%s=?", ID);
        String[] whereArgs = {""+p.getId()};

        int resultado = db.update("GASTO", values, whereClause, whereArgs);
        return resultado > 0;
    }
    public boolean deleteGasto(Gasto p){
        SQLiteDatabase db = database.getWritableDatabase();
        String whereClause = String.format("%s=?", ID);
        String[] whereArgs = {""+p.getId()};
        // Issue SQL statement.
        int deletedRows = db.delete("GASTO", whereClause, whereArgs);
        return deletedRows>0;
    }

    public long insertarRecordatorio(Recordatorio p){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITULO, p.getTitulo());
        values.put(FECHA_RECORD, p.getFecha());
        values.put(HORA_RECORD, p.getHora());
        values.put(DESCRIPCION, p.getDescripcion());
        return db.insert("RECORDATORIO", null, values);
    }
    public boolean updateRecord(Recordatorio p){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITULO, p.getTitulo());
        values.put(FECHA_RECORD, p.getFecha());
        values.put(HORA_RECORD, p.getHora());
        values.put(DESCRIPCION, p.getDescripcion());

        String whereClause = String.format("%s=?", ID);
        String[] whereArgs = {""+p.getId()};

        int resultado = db.update("RECORDATORIO", values, whereClause, whereArgs);
        return resultado > 0;
    }
    public boolean deleteRecord(Gasto p){
        SQLiteDatabase db = database.getWritableDatabase();
        String whereClause = String.format("%s=?", ID);
        String[] whereArgs = {""+p.getId()};
        // Issue SQL statement.
        int deletedRows = db.delete("RECORDATORIO", whereClause, whereArgs);
        return deletedRows>0;
    }
    public ArrayList<Recordatorio> selectRecord(){
        SQLiteDatabase db = database.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                TITULO, FECHA_RECORD, HORA_RECORD, DESCRIPCION
        };
        String sortOrder = FECHA_RECORD + " DESC";
        Cursor cursor = db.query("RECORDATORIO",projection,null,null,
                null,null, sortOrder);
        ArrayList<Recordatorio> recordatorios = new ArrayList<>();
        while(cursor.moveToNext()) {
            int itemId = (int)cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
            String titulo = cursor.getString(cursor.getColumnIndexOrThrow(TITULO));
            String fecha = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_RECORD));
            String hora = cursor.getString(cursor.getColumnIndexOrThrow(HORA_RECORD));
            String descrip = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPCION));

            Recordatorio r = new Recordatorio(itemId,fecha,titulo,descrip,hora);
            recordatorios.add(r);
        }
        cursor.close();
        return recordatorios;
    }

    public long insertarEvento(Evento p){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITULO, p.getTitulo());
        values.put(DESCRIPCION, p.getDescripcion());
        values.put(ALLDAY, p.getAllday());
        values.put(FECHA_INICIO, p.getFecha_inicio());
        values.put(FECHA_FIN, p.getFecha_fin());
        values.put(UBICACION, p.getUbicacion());
        values.put(TIPO, p.getTipo());
        values.put(RECORDATORIO, p.getRecordatorio());
        values.put(ID, p.getId());
        return db.insert("EVENTO", null, values);
    }
    public boolean updateEvento(Evento p){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITULO, p.getTitulo());
        values.put(DESCRIPCION, p.getDescripcion());
        values.put(ALLDAY, p.getAllday());
        values.put(FECHA_INICIO, p.getFecha_inicio());
        values.put(FECHA_FIN, p.getFecha_fin());
        values.put(UBICACION, p.getUbicacion());
        values.put(TIPO, p.getTipo());
        values.put(RECORDATORIO, p.getRecordatorio());
        String whereClause = String.format("%s=?", ID);
        String[] whereArgs = {""+p.getId()};

        int resultado = db.update("EVENTO", values, whereClause, whereArgs);
        return resultado > 0;
    }
    public boolean deleteEvento(Evento p){
        SQLiteDatabase db = database.getWritableDatabase();
        String whereClause = String.format("%s=?", ID);
        String[] whereArgs = {""+p.getId()};
        // Issue SQL statement.
        int deletedRows = db.delete("EVENTO", whereClause, whereArgs);
        return deletedRows>0;
    }
    public ArrayList<Evento> selectEventos(){
        SQLiteDatabase db = database.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                TITULO, DESCRIPCION,ALLDAY,FECHA_INICIO, FECHA_FIN, UBICACION,TIPO,RECORDATORIO
        };
        String sortOrder = FECHA_INICIO + " DESC";
        Cursor cursor = db.query("EVENTO",projection,null,null,
                null,null, sortOrder);
        ArrayList<Evento> eventos = new ArrayList<>();
        while(cursor.moveToNext()) {
            int itemId = (int)cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
            String titulo = cursor.getString(cursor.getColumnIndexOrThrow(TITULO));
            String fecha_i = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_INICIO));
            String fecha_f = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_FIN));
            String ubi = cursor.getString(cursor.getColumnIndexOrThrow(UBICACION));
            String tipo = cursor.getString(cursor.getColumnIndexOrThrow(TIPO));
            int allday = cursor.getInt(cursor.getColumnIndexOrThrow(ALLDAY));
            int recorda = cursor.getInt(cursor.getColumnIndexOrThrow(RECORDATORIO));
            String descrip = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPCION));

            Evento r = new Evento(itemId,fecha_i,fecha_f,allday,titulo,descrip,ubi,tipo,""+recorda);
            eventos.add(r);
        }
        cursor.close();
        return eventos;
    }

    public long insertarVenta(Venta p){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FECHA_VENTA, p.getFecha_venta());
        values.put(CLIENTE, p.getCliente());
        values.put(MONTO, p.getMonto());
        values.put(NOTAS, p.getNotas());
        return db.insert("VENTA", null, values);
    }
    public boolean updateVenta(Venta p){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FECHA_VENTA, p.getFecha_venta());
        values.put(CLIENTE, p.getCliente());
        values.put(MONTO, p.getMonto());
        values.put(NOTAS, p.getNotas());
        String whereClause = String.format("%s=?", ID);
        String[] whereArgs = {""+p.getId()};

        int resultado = db.update("VENTA", values, whereClause, whereArgs);
        return resultado > 0;
    }
    public boolean deleteVenta(Venta p){
        SQLiteDatabase db = database.getWritableDatabase();
        String whereClause = String.format("%s=?", ID);
        String[] whereArgs = {""+p.getId()};
        // Issue SQL statement.
        int deletedRows = db.delete("VENTA", whereClause, whereArgs);
        return deletedRows>0;
    }
    public ArrayList<Venta> selectVenta(){
        SQLiteDatabase db = database.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                FECHA_VENTA, CLIENTE,MONTO, NOTAS
        };
        String sortOrder = FECHA_VENTA + " DESC";
        Cursor cursor = db.query("VENTA",projection,null,null,
                null,null, sortOrder);
        ArrayList<Venta> ventas = new ArrayList<>();
        while(cursor.moveToNext()) {
            int itemId = (int)cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
            String fecha_i = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_VENTA));
            String cliente = cursor.getString(cursor.getColumnIndexOrThrow(CLIENTE));
            double monto = cursor.getDouble(cursor.getColumnIndexOrThrow(MONTO));
            String descrip = cursor.getString(cursor.getColumnIndexOrThrow(NOTAS));
            int ejem = getCountEjemplares(itemId);
            Venta r = new Venta(itemId,fecha_i,cliente,monto,descrip,ejem);
            ventas.add(r);
        }
        cursor.close();
        return ventas;
    }

    public long insertarVentaDetalle(Detalle_venta p){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_VENTA, p.getIdventa());
        values.put(MONTO, p.getMonto());
        values.put(ID_PORCINO, p.getIdporcino());
        return db.insert("DETALLE_VENTA", null, values);
    }
    /*public boolean updateVentaDetalle(Detalle_venta p){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_VENTA, p.getIdventa());
        values.put(MONTO, p.getMonto());
        values.put(ID_PORCINO, p.getIdporcino());
        String whereClause = String.format("%s=?", ID);
        String[] whereArgs = {""+p.getId()};

        int resultado = db.update("VENTA", values, whereClause, whereArgs);
        return resultado > 0;
    }
    public boolean deleteVentaDetalles(Detalle_venta p){
        SQLiteDatabase db = database.getWritableDatabase();
        String whereClause = String.format("%s=?", ID);
        String[] whereArgs = {""+p.getId()};
        // Issue SQL statement.
        int deletedRows = db.delete("VENTA", whereClause, whereArgs);
        return deletedRows>0;
    }*/
    public ArrayList<Detalle_venta> selectVentaDetalle(){
        SQLiteDatabase db = database.getReadableDatabase();
        String[] projection = {
                ID_VENTA, MONTO, ID_PORCINO
        };
        String sortOrder = MONTO + " DESC";
        Cursor cursor = db.query("DETALLE_VENTA",projection,null,null,
                null,null, sortOrder);
        ArrayList<Detalle_venta> ventas = new ArrayList<>();
        while(cursor.moveToNext()) {
            int idventa = cursor.getInt(cursor.getColumnIndexOrThrow(ID_VENTA));
            int idporcino = cursor.getInt(cursor.getColumnIndexOrThrow(ID_PORCINO));
            double monto = cursor.getDouble(cursor.getColumnIndexOrThrow(MONTO));
            Detalle_venta r = new Detalle_venta(idventa,monto,idporcino);
            ventas.add(r);
        }
        cursor.close();
        return ventas;
    }

    public ArrayList<Detalle_venta> selectDetalleVenta(int id){
        SQLiteDatabase db = database.getReadableDatabase();
        String[] projection = {
                ID_VENTA, MONTO, ID_PORCINO
        };
        String selection = ID_VENTA + " = ?";
        String[] selectionArgs = {""+id};
        Cursor cursor = db.query("DETALLE_VENTA",projection,selection,selectionArgs,
                null,null, null);
        ArrayList<Detalle_venta> ventas = new ArrayList<>();
        while(cursor.moveToNext()) {
            int idventa = cursor.getInt(cursor.getColumnIndexOrThrow(ID_VENTA));
            int idporcino = cursor.getInt(cursor.getColumnIndexOrThrow(ID_PORCINO));
            double monto = cursor.getDouble(cursor.getColumnIndexOrThrow(MONTO));
            Detalle_venta r = new Detalle_venta(idventa,monto,idporcino);
            ventas.add(r);
        }
        cursor.close();
        return ventas;
    }


    public long insertarMontaS(MontaS p){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FECHA_MONTA, p.getFecha_monta());
        values.put(CLIENTE, p.getCliente());
        values.put(MONTO, p.getMonto());
        values.put(NAT, p.getNatural());
        values.put(ID_PORCINO, p.getIddemental());
        values.put(NOTAS, p.getNotas());
        values.put(HEMBRA, p.getHembra());

        return db.insert("MONTA_S", null, values);
    }
    public boolean updateMontaS(MontaS p){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FECHA_MONTA, p.getFecha_monta());
        values.put(CLIENTE, p.getCliente());
        values.put(MONTO, p.getMonto());
        values.put(NAT, p.getNatural());
        values.put(ID_PORCINO, p.getIddemental());
        values.put(NOTAS, p.getNotas());
        values.put(HEMBRA, p.getHembra());
        String whereClause = String.format("%s=?", ID);
        String[] whereArgs = {""+p.getId()};

        int resultado = db.update("MONTA_S", values, whereClause, whereArgs);
        return resultado > 0;
    }
    public boolean deleteMontaS(MontaS p){
        SQLiteDatabase db = database.getWritableDatabase();
        String whereClause = String.format("%s=?", ID);
        String[] whereArgs = {""+p.getId()};
        // Issue SQL statement.
        int deletedRows = db.delete("MONTA_S", whereClause, whereArgs);
        return deletedRows>0;
    }
    public ArrayList<MontaS> selectMontaS(){
        SQLiteDatabase db = database.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                FECHA_MONTA,CLIENTE,MONTO,NAT,ID_PORCINO, NOTAS,HEMBRA
        };
        String sortOrder = ID + " DESC";
        Cursor cursor = db.query("MONTA_S",projection,null,null,
                null,null, sortOrder);
        ArrayList<MontaS> montas = new ArrayList<>();
        while(cursor.moveToNext()) {
            int itemId = (int)cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
            String fecha_i = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_MONTA));
            String cliente = cursor.getString(cursor.getColumnIndexOrThrow(CLIENTE));
            double monto = cursor.getDouble(cursor.getColumnIndexOrThrow(MONTO));
            int nat = cursor.getInt(cursor.getColumnIndexOrThrow(NAT));
            int idporcino = cursor.getInt(cursor.getColumnIndexOrThrow(ID_PORCINO));
            String descrip = cursor.getString(cursor.getColumnIndexOrThrow(NOTAS));
            String hembra = cursor.getString(cursor.getColumnIndexOrThrow(HEMBRA));
            MontaS r = new MontaS(itemId,idporcino,fecha_i,cliente,monto,nat,descrip,hembra);
            montas.add(r);
        }
        cursor.close();
        return montas;
    }
    public ArrayList<MontaS> selectMontaS(int id){
        SQLiteDatabase db = database.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                FECHA_MONTA,CLIENTE,MONTO,NAT,ID_PORCINO, NOTAS,HEMBRA
        };
        // Filter results WHERE "title" = 'My Title'
        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = {""+id};

        String sortOrder = ID + " DESC";
        Cursor cursor = db.query("MONTA_S",projection,selection,selectionArgs,
                null,null, sortOrder);
        ArrayList<MontaS> montas = new ArrayList<>();
        while(cursor.moveToNext()) {
            int itemId = (int)cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
            String fecha_i = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_MONTA));
            String cliente = cursor.getString(cursor.getColumnIndexOrThrow(CLIENTE));
            double monto = cursor.getDouble(cursor.getColumnIndexOrThrow(MONTO));
            int nat = cursor.getInt(cursor.getColumnIndexOrThrow(NAT));
            int idporcino = cursor.getInt(cursor.getColumnIndexOrThrow(ID_PORCINO));
            String descrip = cursor.getString(cursor.getColumnIndexOrThrow(NOTAS));
            String hembra = cursor.getString(cursor.getColumnIndexOrThrow(HEMBRA));
            MontaS r = new MontaS(itemId,idporcino,fecha_i,cliente,monto,nat,descrip,hembra);
            montas.add(r);
        }
        cursor.close();
        return montas;
    }

    public long insertarMontaH(MontasH p){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FECHA_MONTA, p.getFecha_monta());
        values.put(SEMENTAL, p.getSemental());
        values.put(NAT, p.getNatural());
        values.put(ID_PORCINO, p.getIdhembra());
        values.put(NOTAS, p.getNotas());

        return db.insert("MONTA_H", null, values);
    }
    public boolean updateMontaH(MontasH p){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FECHA_MONTA, p.getFecha_monta());
        values.put(SEMENTAL, p.getSemental());
        values.put(NAT, p.getNatural());
        values.put(ID_PORCINO, p.getIdhembra());
        values.put(NOTAS, p.getNotas());
        String whereClause = String.format("%s=?", ID);
        String[] whereArgs = {""+p.getId()};

        int resultado = db.update("MONTA_H", values, whereClause, whereArgs);
        return resultado > 0;
    }
    public boolean deleteMontaH(MontasH p){
        SQLiteDatabase db = database.getWritableDatabase();
        String whereClause = String.format("%s=?", ID);
        String[] whereArgs = {""+p.getId()};
        // Issue SQL statement.
        int deletedRows = db.delete("MONTA_H", whereClause, whereArgs);
        return deletedRows>0;
    }
    public ArrayList<MontasH> selectMontaH(){
        SQLiteDatabase db = database.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                FECHA_MONTA,SEMENTAL,NAT,ID_PORCINO,NOTAS
        };
        String sortOrder = ID + " DESC";
        Cursor cursor = db.query("MONTA_H",projection,null,null,
                null,null, sortOrder);
        ArrayList<MontasH> montas = new ArrayList<>();
        while(cursor.moveToNext()) {
            int itemId = (int)cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
            String fecha_i = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_MONTA));
            String semental = cursor.getString(cursor.getColumnIndexOrThrow(SEMENTAL));
            int nat = cursor.getInt(cursor.getColumnIndexOrThrow(NAT));
            int idporcino = cursor.getInt(cursor.getColumnIndexOrThrow(ID_PORCINO));
            String descrip = cursor.getString(cursor.getColumnIndexOrThrow(NOTAS));
            MontasH r = new MontasH(itemId,idporcino,fecha_i,semental,nat,descrip);
            montas.add(r);
        }
        cursor.close();
        return montas;
    }
    public ArrayList<MontasH> selectMontaH(int id){
        SQLiteDatabase db = database.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                FECHA_MONTA,SEMENTAL,NAT,ID_PORCINO,NOTAS
        };
        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = {""+id};
        String sortOrder = ID + " DESC";
        Cursor cursor = db.query("MONTA_H",projection,selection,selectionArgs,
                null,null, sortOrder);
        ArrayList<MontasH> montas = new ArrayList<>();
        while(cursor.moveToNext()) {
            int itemId = (int)cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
            String fecha_i = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_MONTA));
            String semental = cursor.getString(cursor.getColumnIndexOrThrow(SEMENTAL));
            int nat = cursor.getInt(cursor.getColumnIndexOrThrow(NAT));
            int idporcino = cursor.getInt(cursor.getColumnIndexOrThrow(ID_PORCINO));
            String descrip = cursor.getString(cursor.getColumnIndexOrThrow(NOTAS));
            MontasH r = new MontasH(itemId,idporcino,fecha_i,semental,nat,descrip);
            montas.add(r);
        }
        cursor.close();
        return montas;
    }

    public long insertarParto(Parto p){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FECHA_PARTO, p.getFecha_parto());
        values.put(FECHA_MONTA, p.getFecha_cruza());
        values.put(FECHA_DESTETE, p.getFecha_destete());
        values.put(SEMENTAL, p.getSemental());
        values.put(NAT, p.getNatural());
        values.put(EXITOSO, p.getExitoso());
        values.put(ID_PORCINO, p.getIdhembra());
        values.put(TOTAL_LECHONES, p.getLechones());
        values.put(VIVOS, p.getVivos());
        values.put(NOTAS, p.getNotas());

        return db.insert("PARTO", null, values);
    }
    public boolean updateParto(Parto p){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FECHA_PARTO, p.getFecha_parto());
        values.put(FECHA_MONTA, p.getFecha_cruza());
        values.put(FECHA_DESTETE, p.getFecha_destete());
        values.put(SEMENTAL, p.getSemental());
        values.put(NAT, p.getNatural());
        values.put(EXITOSO, p.getExitoso());
        values.put(ID_PORCINO, p.getIdhembra());
        values.put(TOTAL_LECHONES, p.getLechones());
        values.put(VIVOS, p.getVivos());
        values.put(NOTAS, p.getNotas());
        String whereClause = String.format("%s=?", ID);
        String[] whereArgs = {""+p.getId()};

        int resultado = db.update("PARTO", values, whereClause, whereArgs);
        return resultado > 0;
    }
    public boolean deleteParto(Parto p){
        SQLiteDatabase db = database.getWritableDatabase();
        String whereClause = String.format("%s=?", ID);
        String[] whereArgs = {""+p.getId()};
        // Issue SQL statement.
        int deletedRows = db.delete("PARTO", whereClause, whereArgs);
        return deletedRows>0;
    }
    public ArrayList<Parto> selectParto(){
        SQLiteDatabase db = database.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                FECHA_PARTO,FECHA_MONTA,FECHA_DESTETE,SEMENTAL,NAT,EXITOSO,
                ID_PORCINO,TOTAL_LECHONES,VIVOS,NOTAS
        };
        String sortOrder = ID + " DESC";
        Cursor cursor = db.query("PARTO",projection,null,null,
                null,null, sortOrder);
        ArrayList<Parto> partos = new ArrayList<>();
        while(cursor.moveToNext()) {
            int itemId = (int)cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
            String fecha_p = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_PARTO));
            String fecha_m = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_MONTA));
            String fecha_d = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_DESTETE));
            String semental = cursor.getString(cursor.getColumnIndexOrThrow(SEMENTAL));
            int nat = cursor.getInt(cursor.getColumnIndexOrThrow(NAT));
            int exito = cursor.getInt(cursor.getColumnIndexOrThrow(EXITOSO));
            int idporcino = cursor.getInt(cursor.getColumnIndexOrThrow(ID_PORCINO));
            int total = cursor.getInt(cursor.getColumnIndexOrThrow(TOTAL_LECHONES));
            int vivos = cursor.getInt(cursor.getColumnIndexOrThrow(VIVOS));
            String descrip = cursor.getString(cursor.getColumnIndexOrThrow(NOTAS));
            Parto r = new Parto(itemId,fecha_p,fecha_m,fecha_d,semental,nat,exito,idporcino,total,vivos,descrip);
            partos.add(r);
        }
        cursor.close();
        return partos;
    }
    public ArrayList<Parto> selectParto(int id){
        SQLiteDatabase db = database.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                FECHA_PARTO,FECHA_MONTA,FECHA_DESTETE,SEMENTAL,NAT,EXITOSO,
                ID_PORCINO,TOTAL_LECHONES,VIVOS,NOTAS
        };
        String sortOrder = ID + " DESC";
        String selection = ID_PORCINO + " = ?";
        String[] selectionArgs = {""+id};
        Cursor cursor = db.query("PARTO",projection,selection,selectionArgs,
                null,null, null);
        ArrayList<Parto> partos = new ArrayList<>();
        while(cursor.moveToNext()) {
            int itemId = (int)cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
            String fecha_p = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_PARTO));
            String fecha_m = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_MONTA));
            String fecha_d = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_DESTETE));
            String semental = cursor.getString(cursor.getColumnIndexOrThrow(SEMENTAL));
            int nat = cursor.getInt(cursor.getColumnIndexOrThrow(NAT));
            int exito = cursor.getInt(cursor.getColumnIndexOrThrow(EXITOSO));
            int idporcino = cursor.getInt(cursor.getColumnIndexOrThrow(ID_PORCINO));
            int total = cursor.getInt(cursor.getColumnIndexOrThrow(TOTAL_LECHONES));
            int vivos = cursor.getInt(cursor.getColumnIndexOrThrow(VIVOS));
            String descrip = cursor.getString(cursor.getColumnIndexOrThrow(NOTAS));
            Parto r = new Parto(itemId,fecha_p,fecha_m,fecha_d,semental,nat,exito,idporcino,total,vivos,descrip);
            partos.add(r);
        }
        cursor.close();
        return partos;
    }
}
