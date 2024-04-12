package Ventana;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.net.URL;
import java.awt.Color;

public class Imagen extends JLabel{
	String ruta;
	ImageIcon original;
	
	public Imagen(){

	}
	
	public Imagen(String s){
		this.setImagen(s);
	}
	
	public Imagen(int w,int h,String s,int x,int y){
		this.setLocation(x,y);
		this.setSize(w,h);
		this.setImagen(getClass().getResource(s),w,h);
	}
	
	public Imagen(int w, int h, URL s, int x, int y) {
		this.setLocation(x,y);
		this.setSize(w,h);
		this.setImagen(s,w,h);	
	}
		
	
	public Imagen(int w,int h,String s){
		this.setSize(w,h);
		this.setImagen(getClass().getResource(s),w,h);
	}
	
	public Imagen(int w,int h,String s,String text){
		this.setSize(w,h);
		this.setText(text);
		this.setImagen(getClass().getResource(s),w,h);
	}


	public void ajustar(){
		//System.out.println("ajustando a: "+" "+this.getSize().toString()+" desde "
		//		+original.getIconWidth()+" "+original.getIconHeight());
		this.setIcon(new ImageIcon(original.getImage().getScaledInstance(this.getWidth(),this.getHeight(), Image.SCALE_SMOOTH)));
	}
	
	public void setImagen(String s) {
		this.setImagen(getClass().getResource(s),this.getWidth(),this.getHeight());
	}
	
	public void setImagen(ImageIcon s) {
		this.original=s;
		ajustar();
		
	}
	
	public void setImagen(URL s,int w,int h) {
		try{
			original = new ImageIcon(s);
			this.setIcon(new ImageIcon(original.getImage().getScaledInstance(w,h,Image.SCALE_SMOOTH)));
			this.ruta=s.toString();
			
		}
		catch(Exception e){
			this.setBackground(Color.red);
			this.setOpaque(true);
			e.printStackTrace(System.out);
//			System.out.println(s.toString());
		}
	}
}
