package com.example.appporcino.data;

import android.widget.AutoCompleteTextView;

import com.google.android.material.textfield.TextInputEditText;

import org.threeten.bp.LocalDate;
import org.threeten.bp.chrono.ChronoLocalDate;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.ChronoUnit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class Validacion {
    public static String ESTADO_DESARROLLO = "En desarrollo";
    public static String ESTADO_CRECIMIENTO = "En crecimiento";
    public static String ESTADO_LISTO_PARA_VENTA= "Listo para venta";
    public static String ESTADO_LISTO_PARA_MONTA = "Listo para nueva monta";
    public static String ESTADO_GESTACION= "En gestación";
    public static String ESTADO_VENDIDO= "Vendido";
    public static String ESTADO_ESPERA_GESTACION= "Por confirmar preño";
    public static String ESTADO_ESPERA_CELO= "Esperando nuevo celo";
    public static String ESTADO_ESPERA_PRIMER_CELO= "Esperando primer celo";
    public static String ESTADO_PROXIMA_A_CRIAR= "Próxima a criar";
    public static String ESTADO_LACTANCIA= "En Lactancia";
    public static String ESTADO_EN_CELO= "En celo";
    public static String ESTADO_DESTETE= "Lista para destete";
    public static String PROPOSITO_ENGORDA= "Engorda";
    public static String PROPOSITO_SEMENTAL= "Semental";
    public static String PROPOSITO_CRIA= "Cría";

    public boolean validaNombre(String n, TextInputEditText editText){
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if(n.equalsIgnoreCase("")){
            editText.setError("Nombre vacío");
            editText.requestFocus();
            return false;
        }
        if (n.length() > 30) {
            editText.setError("Nombre debe tener menos de 30 caracteres");
            editText.requestFocus();
            return false;
        }if(!Character.isLetter(n.charAt(0))){
            editText.setError("Nombre debe iniciar con una letra");
            editText.requestFocus();
            return false;
        }else {
            editText.setError(null);
            return true;
        }
    }
    public boolean validaFechaN(String fecha, TextInputEditText editText){
        if(fecha.equalsIgnoreCase("")){
            editText.setError("Debe seleccionar una fecha");
            editText.requestFocus();
            return false;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date;
        Date min;
        Date max = new Date();
        try{
            min = formatter.parse("31/12/1999");
            date = formatter.parse(fecha);
        } catch (ParseException e) {
            editText.setError("Hubo un error en la fecha");
            editText.requestFocus();
            return false;
        }
        //assert date != null;
        if(date.before(min)){
            editText.setError("Fecha demasiado antigua");
            editText.requestFocus();
            return false;
        }
        if(date.after(max)){
            editText.setError("Fecha no debe ser futura");
            editText.requestFocus();
            return false;
        }
        editText.setError(null);
        return true;
    }
    public boolean validaRaza(String raza, AutoCompleteTextView editText){
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if(!raza.equalsIgnoreCase("") && !patron.matcher(raza).matches()) {
            editText.setError("Raza no debe contener números");
            editText.requestFocus();
            return false;
        }
        editText.setError(null);
        return true;
    }
    public boolean validaProposito(String prop, AutoCompleteTextView editText){
        if(prop.equalsIgnoreCase("")){
            editText.setError("Debe seleccionar un Propósito");
            editText.requestFocus();
            return false;
        }
        editText.setError(null);
        return true;
    }
    public boolean validaFechaC(String fecha, String fechaN, TextInputEditText editText){
        if(fecha.equalsIgnoreCase("")){
            editText.setError("Debe seleccionar una fecha");
            editText.requestFocus();
            return false;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date;
        Date min;
        Date max = new Date();
        try{
            min = formatter.parse(fechaN);
            date = formatter.parse(fecha);
        } catch (ParseException e) {
            editText.setError("Hubo un error en la fecha");
            editText.requestFocus();
            return false;
        }
        //min.setDate(min.getDate()-1);
        if(date.compareTo(min)<0){
            editText.setError("Fecha debe ser mayor a Fecha Nacimiento");
            editText.requestFocus();
            return false;
        }
        if(date.after(max)){
            editText.setError("Fecha no debe ser futura");
            editText.requestFocus();
            return false;
        }
        editText.setError(null);
        return true;
    }
    public boolean validaPeso(String peso, TextInputEditText editText){
        double pesoo;
        if(peso.equalsIgnoreCase("")){
            editText.setError(null);
            return true;
        }
        try{
            pesoo = Double.parseDouble(peso);
        } catch (NumberFormatException e) {
            editText.setError("Formato de número no válido");
            editText.requestFocus();
            return false;
        }
        if(pesoo<5){
            editText.setError("Peso demasiado bajo");
            editText.requestFocus();
            return false;
        }
        if(pesoo>500){
            editText.setError("Peso demasiado alto");
            editText.requestFocus();
            return false;
        }
        editText.setError(null);
        return true;
    }
    public boolean validaNum(String numero,int max, TextInputEditText editText, String tipo){
        if(tipo.equalsIgnoreCase("engorda") && numero.equalsIgnoreCase("")){
            editText.setError(null);
            return true;
        }
        int n;
        try{
            n = Integer.parseInt(numero);
        } catch (NumberFormatException e) {
            editText.setError("Debe insertar un número");
            editText.requestFocus();
            return false;
        }
        if(n<0){
            editText.setError("Sólo números positivos");
            editText.requestFocus();
            return false;
        }
        if(n>max){
            editText.setError("Valor demasiado grande");
            editText.requestFocus();
            return false;
        }
        editText.setError(null);
        return true;
    }
    public boolean validaCosto(String costo, TextInputEditText editText){
        double costoo;
        if(costo.equalsIgnoreCase("")){
            editText.setError(null);
            return true;
        }
        try{
            costoo = Double.parseDouble(costo);
        } catch (NumberFormatException e) {
            editText.setError("Formato de número no válido");
            editText.requestFocus();
            return false;
        }
        if(costoo<0){
            editText.setError("Costo no debe ser negativo");
            editText.requestFocus();
            return false;
        }
        if(costoo>=100000){
            editText.setError("Costo demasiado alto");
            editText.requestFocus();
            return false;
        }
        editText.setError(null);
        return true;
    }

    public boolean validaCampos(TextInputEditText nombre,TextInputEditText fechaN,TextInputEditText fechaC,
                                AutoCompleteTextView raza,AutoCompleteTextView prop, TextInputEditText costo,
                                TextInputEditText num,String sexo){
        String nom = nombre.getText().toString();
        String fecha_nac = fechaN.getText().toString();
        String razas = raza.getText().toString();
        String fecha_compra = fechaC.getText().toString();
        String costoo = costo.getText().toString();
        String proposito = prop.getText().toString();
        return validaNombre(nom,nombre) && validaRaza(razas,raza) &&
                validaProposito(proposito,prop) && validaFechaN(fecha_nac,fechaN) &&
                validaFechaC(fecha_compra,fecha_nac,fechaC) && validaCosto(costoo,costo)  &&
                validaSexo(proposito,sexo,prop);
    }
    public boolean validaSexo(String tipo, String genero, AutoCompleteTextView editText){
        if(tipo.equalsIgnoreCase("")){
            editText.setError("Debe seleccionar un propósito");
            editText.requestFocus();
            return false;
        }
        if(tipo.equalsIgnoreCase("Semental")){
            if(genero.equalsIgnoreCase("H")){
                editText.setError("Un semental no puede ser hembra");
                editText.requestFocus();
                return false;
            }
            else{
                editText.setError(null);
                return true;
            }
        }
        if(tipo.equalsIgnoreCase("Cría")){
            if(genero.equalsIgnoreCase("M")){
                editText.setError("Un macho no puede ser para cría");
                editText.requestFocus();
                return false;
            }
            else{
                editText.setError(null);
                return true;
            }
        }
        return true;
    }
    public static String validaEstado(String fecha_n,String estado,String tipo,String fecha_m){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy",
                Locale.getDefault());
        LocalDate now = LocalDate.now();
        ChronoLocalDate from = ChronoLocalDate.from(formatter.parse(fecha_n));
        ChronoLocalDate to = ChronoLocalDate.from(now);

        long days = ChronoUnit.DAYS.between(from, to);
        if(estado.equalsIgnoreCase(ESTADO_CRECIMIENTO) && days>90){
            return ESTADO_DESARROLLO;
        }
        //Estados para engorda
        if(tipo.equalsIgnoreCase(PROPOSITO_ENGORDA)&&estado.equalsIgnoreCase(ESTADO_DESARROLLO) && days>180){
            return ESTADO_LISTO_PARA_VENTA;
        }
        //EStados para cria
        if(tipo.equalsIgnoreCase(PROPOSITO_CRIA)&&estado.equalsIgnoreCase(ESTADO_DESARROLLO) && days>180){
            return ESTADO_ESPERA_PRIMER_CELO;
        }
        if(tipo.equalsIgnoreCase(PROPOSITO_CRIA)&&estado.equalsIgnoreCase(ESTADO_ESPERA_GESTACION)){
            ChronoLocalDate from2 = ChronoLocalDate.from(formatter.parse(fecha_m));
            ChronoLocalDate to2 = ChronoLocalDate.from(now);
            long days2 = ChronoUnit.DAYS.between(from2, to2);
            if(days2>21){
                return ESTADO_GESTACION;
            }
            return "";
        }
        if(tipo.equalsIgnoreCase(PROPOSITO_CRIA)&&estado.equalsIgnoreCase(ESTADO_GESTACION)){
            ChronoLocalDate from2 = ChronoLocalDate.from(formatter.parse(fecha_m));
            ChronoLocalDate to2 = ChronoLocalDate.from(now);
            long days2 = ChronoUnit.DAYS.between(from2, to2);

            if(days2>90 && days2<=100){
                return ESTADO_PROXIMA_A_CRIAR;
            }
            return "";
        }
        if(tipo.equalsIgnoreCase(PROPOSITO_CRIA)&&estado.equalsIgnoreCase(ESTADO_PROXIMA_A_CRIAR)){
            ChronoLocalDate from2 = ChronoLocalDate.from(formatter.parse(fecha_m));
            ChronoLocalDate to2 = ChronoLocalDate.from(now);
            long days2 = ChronoUnit.DAYS.between(from2, to2);
            if(days2>100 && days2<114){
                return "Criando en "+(114-days2)+" días";
            }
            return "";
        }

        if(tipo.equalsIgnoreCase(PROPOSITO_CRIA)&&estado.contains("Criando en ")){
            ChronoLocalDate from2 = ChronoLocalDate.from(formatter.parse(fecha_m));
            ChronoLocalDate to2 = ChronoLocalDate.from(now);
            long days2 = ChronoUnit.DAYS.between(from2, to2);
            if(days2>100 && days2<114){
                return "Criando en "+(114-days2)+" días";
            }
            if(days2>=114 && days < 116){
                return "Criando hoy";
            }
            return "";
        }
        if(tipo.equalsIgnoreCase(PROPOSITO_CRIA)&&estado.contains("Criando hoy")){
            ChronoLocalDate from2 = ChronoLocalDate.from(formatter.parse(fecha_m));
            ChronoLocalDate to2 = ChronoLocalDate.from(now);
            long days2 = ChronoUnit.DAYS.between(from2, to2);
            if(days2>=116 && days2<150){
                return ESTADO_LACTANCIA;
            }
            return "";
        }

        if(tipo.equalsIgnoreCase(PROPOSITO_CRIA)&&estado.equalsIgnoreCase(ESTADO_LACTANCIA)){
            ChronoLocalDate from2 = ChronoLocalDate.from(formatter.parse(fecha_m));
            ChronoLocalDate to2 = ChronoLocalDate.from(now);
            long days2 = ChronoUnit.DAYS.between(from2, to2);
            if(days2>=150 && days2<160){
                return ESTADO_DESTETE;
            }
            return "";
        }
        if(tipo.equalsIgnoreCase(PROPOSITO_CRIA)&&estado.equalsIgnoreCase(ESTADO_DESTETE)){
            ChronoLocalDate from2 = ChronoLocalDate.from(formatter.parse(fecha_m));
            ChronoLocalDate to2 = ChronoLocalDate.from(now);
            long days2 = ChronoUnit.DAYS.between(from2, to2);
            if(days2>=160 && days2<168){
                return ESTADO_ESPERA_CELO;
            }
            return "";
        }
        //ESTADOS SEMENTALES
        if(tipo.equalsIgnoreCase(PROPOSITO_SEMENTAL)&&estado.equalsIgnoreCase(ESTADO_DESARROLLO) && days>180){
            return ESTADO_LISTO_PARA_MONTA;
        }
        return "";
    }
    public static String validaEstado(String fecha_n,String tipo){
        //Metodo usado solo para agregar y actualizar porcinos
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy",
                Locale.getDefault());
        LocalDate now = LocalDate.now();
        ChronoLocalDate from = ChronoLocalDate.from(formatter.parse(fecha_n));
        ChronoLocalDate to = ChronoLocalDate.from(now);

        long days = ChronoUnit.DAYS.between(from, to);
        if(days<=90){
            return ESTADO_CRECIMIENTO;
        }
        if(days>90 && days <= 180){
            return ESTADO_DESARROLLO;
        }
        if(tipo.equalsIgnoreCase(PROPOSITO_ENGORDA) && days>180){
            return ESTADO_LISTO_PARA_VENTA;
        }
        //EStados para cria
        if(tipo.equalsIgnoreCase(PROPOSITO_CRIA)&& days>180 && days<230){
            return ESTADO_ESPERA_PRIMER_CELO;
        }
        if(tipo.equalsIgnoreCase(PROPOSITO_CRIA)&& days>230){
            return ESTADO_ESPERA_CELO;
        }
        if(tipo.equalsIgnoreCase(PROPOSITO_SEMENTAL)&& days>180){
            return ESTADO_LISTO_PARA_MONTA;
        }
        return ESTADO_DESARROLLO;
    }
    public static boolean validaMonta(TextInputEditText editText,String fecha_n){
        String fecha = ""+editText.getText();
        if(fecha.equalsIgnoreCase("")){
            editText.setError("Debe seleccionar una fecha");
            editText.requestFocus();
            return false;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date;
        Date min;
        Date max = new Date();
        try{
            min = formatter.parse(fecha_n);
            date = formatter.parse(fecha);
        } catch (ParseException e) {
            editText.setError("Hubo un error en la fecha");
            editText.requestFocus();
            return false;
        }
        if(date.before(min)){
            editText.setError("Fecha no válida. Demasiado antigua");
            editText.requestFocus();
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(min);
        calendar.add(Calendar.MONTH,4);
        min = calendar.getTime();
        if(date.before(min)){
            editText.setError("Su cerda aún es muy joven para registrar una monta");
            editText.requestFocus();
            return false;
        }
        if(date.after(max)){
            editText.setError("Fecha no debe ser futura");
            editText.requestFocus();
            return false;
        }
        editText.setError(null);
        return true;
    }
    public static boolean validaMontaS(TextInputEditText editText,String fecha_n){
        String fecha = ""+editText.getText();
        if(fecha.equalsIgnoreCase("")){
            editText.setError("Debe seleccionar una fecha");
            editText.requestFocus();
            return false;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date;
        Date min;
        Date max = new Date();
        try{
            min = formatter.parse(fecha_n);
            date = formatter.parse(fecha);
        } catch (ParseException e) {
            editText.setError("Hubo un error en la fecha");
            editText.requestFocus();
            return false;
        }
        if(date.before(min)){
            editText.setError("Fecha no válida. Demasiado antigua");
            editText.requestFocus();
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(min);
        calendar.add(Calendar.MONTH,4);
        min = calendar.getTime();
        if(date.before(min)){
            editText.setError("Su semental aún es muy joven para registrar una monta");
            editText.requestFocus();
            return false;
        }
        if(date.after(max)){
            editText.setError("Fecha no debe ser futura");
            editText.requestFocus();
            return false;
        }
        editText.setError(null);
        return true;
    }
    public static boolean validaGasto(TextInputEditText editText,TextInputEditText monto){
        String fecha = ""+editText.getText();
        if(fecha.equalsIgnoreCase("")){
            editText.setError("Debe seleccionar una fecha");
            editText.requestFocus();
            return false;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date;
        Date min;
        Date max = new Date();
        try{
            min = formatter.parse("31/12/1999");
            date = formatter.parse(fecha);
        } catch (ParseException e) {
            editText.setError("Hubo un error en la fecha");
            editText.requestFocus();
            return false;
        }
        if(date.before(min)){
            editText.setError("Fecha demasiado antigua");
            editText.requestFocus();
            return false;
        }
        /*Calendar calendar = Calendar.getInstance();
        calendar.setTime(max);
        calendar.add(Calendar.MONTH,12);
        max = calendar.getTime();*/
        if(date.after(max)){
            editText.setError("Fecha no debe ser futura");
            editText.requestFocus();
            return false;
        }
        double mont;
        String mon = ""+monto.getText();
        if(mon.equalsIgnoreCase("")){
            monto.setError("Debe agregar una cantidad");
            monto.requestFocus();
            return false;
        }
        try{
            mont = Double.parseDouble(mon);
        }catch (Exception e){
            monto.setError("Formato de número no válido");
            monto.requestFocus();
            mont = -1;
            return false;
        }
        if(mont <= 0){
            monto.setError("Cantidad debe ser mayor a 0");
            monto.requestFocus();
            return false;
        }
        editText.setError(null);
        return true;
    }
    public static boolean validaRecordatorio(TextInputEditText editText){
        String fecha = ""+editText.getText();
        if(fecha.equalsIgnoreCase("")){
            editText.setError("Debe seleccionar una fecha");
            editText.requestFocus();
            return false;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date;
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_YEAR,-14);
        now = calendar.getTime();
        try{
            //min = formatter.parse("01/11/2019");
            date = formatter.parse(fecha);
        } catch (ParseException e) {
            editText.setError("Hubo un error en la fecha");
            editText.requestFocus();
            return false;
        }
        if(date.before(now)){
            editText.setError("Fecha demasiado antigua");
            editText.requestFocus();
            return false;
        }
        editText.setError(null);
        return true;
    }
    public static boolean validaFutura(TextInputEditText editText,TextInputEditText editText2){
        String fecha = ""+editText.getText();
        String name = ""+editText2.getText();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date;
        Date now = new Date();
        try{
            date = formatter.parse(fecha);
        } catch (ParseException e) {
            editText.setError("Hubo un error en la fecha");
            editText.requestFocus();
            return false;
        }
        if(date.after(now)){
            editText.setError("Fecha no debe ser futura");
            editText.requestFocus();
            return false;
        }
        if(name.equalsIgnoreCase("")){
            editText2.setError("Debe agregar un nombre de cliente");
            editText2.requestFocus();
            return false;
        }
        editText2.setError(null);
        editText.setError(null);
        return true;
    }
    public static boolean validaPrecios(ArrayList prec) {
        double d = 0;
        for(int i = 0; i<prec.size();i++){
            try{
                d = Double.parseDouble((String)prec.get(i));
            }catch (Exception e){
                return false;
            }
            if(d<=0)
                return false;
        }
        return true;
    }
    public static double[] getPrecios(ArrayList prec){
        double[] p = new double[prec.size()];
        for(int i = 0; i< prec.size(); i++){
            p[i] = Double.parseDouble((String)prec.get(i));
        }
        return  p;
    }
}










