package bills;

import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JLayeredPane;

public class TextoAnimado extends JTextArea{
	String texto;
	boolean start=false;
	JLayeredPane padre;
	
	
	private int ancho(String s) {
		if(s==null)return 0;
		int ancho=0;
		for(int i=0;i<s.length();i++) {
			ancho+=this.getFontMetrics(this.getFont()).charWidth(s.charAt(i));
		}
		return ancho;
	}
	
	TextoAnimado(String s){
		texto=s;
		__init();
	}
	
	void __init() {
		this.setBorder(null);
		this.setOpaque(false);
		this.setCaretColor(Color.white);
		this.setEditable(false);
	}
	
	public void restart() {
		start=false;
		this.setText("");
	}
	
	public void start() {
		(new Thread() {
			public void run() {
				int delay=25;
				String temp="";
				int i=0;
				start=true;
				while(i<texto.length()) {
					if(!start)return;				
					while(ancho(temp)<TextoAnimado.this.getWidth()) {
					if(!start)return;
						System.out.println(ancho(temp)+"    "+TextoAnimado.this.getWidth());
						if(i>=texto.length())break;
						System.out.println(temp);
						temp+=texto.charAt(i++);												
					}
					if(i==texto.length()) {						
						break;
					}
					int k=temp.length()-1;
					
					System.out.println("i antes del volver: "+i);
					int espacios=0;
					for(int j=k;j>=0;j--) {
					if(!start)return;
						if(temp.charAt(j)==(char)' ') {
							System.out.println("encontre el primer espacio "+(j+1));
							i-=(k-j);
							temp = temp.substring(0,j);
							break;
						}
					}
					System.out.println("i luego del volver: "+i);
					for(int j=0;j<temp.length();j++) {
						if(!start)return;
						setText(getText()+temp.charAt(j));
						//setCaretPosition(getText().length());
						try {
							sleep(delay);
						}
						catch(Exception e) {
							
						}	
					}
					TextoAnimado.this.setText(getText()+"\n");
					temp="";
				}
				int k=temp.length()-1;
				for(int j=0;j<temp.length();j++) {
					if(!start)return;
					setText(getText()+temp.charAt(j));
					//setCaretPosition(getText().length());
					try {
						sleep(delay);
					}
					catch(Exception e) {
						
					}	
				}			}
		}).start();
	}
	
}
