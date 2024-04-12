package com.nobody.adMEnestrator;

import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Calendar;
import javax.swing.JLabel;


public class Entry implements Serializable{
	public String  		Nombre;
	public String  		Tipo;
	public BigDecimal   Precio;
	public long    		 ID;
	public int 		    index;
	public Date    		Fecha = new Date();
	public Date    		Fpago;
	public boolean 		auto;
	
	public Entry() {
		
	}
	
	public Entry(String n,BigDecimal bigDecimal,String categoria,long ID) {
		Nombre=n;
		Precio=bigDecimal;
		Tipo=categoria;
		this.ID=ID;
	}
	
	public String string() {
		return Nombre+"      $"+Precio+"        "+Tipo;
	}

}
