package Ventana;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JLayeredPane;

public class TextField extends JLayeredPane{
	public  			JLabel 			hint = new JLabel();
	public  			String 			texto="Ingrese el texto deseado";
	public  			JTextField 		text = new JTextField();
	public  			JLabel 			underline = new JLabel();
	public 	 			Imagen 			icon;
	private 			int 			iconwidth=25;
	private				int 			iconheight=25;
	KeyListener 		showhint;
	KeyListener			NUMERICMODE;
	KeyListener			NoFirstSpace;
	DocumentListener 	sh;
	
	public TextField copia() {
		try {
			return (TextField)this.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
			
		}
	}
	
	public TextField(int w,int h,String hint) {
		this.texto=hint;
		this.setOpaque(false);
		this.setSize(w,h);
		this.setLayout(null);
		this.Listeners();
		this.Text();
		this.Underline();
		this.Hint();
		this.Organizar();
	}
	
	public TextField(int w,int h,String hint,String s) {
		this.texto=hint;
		this.setOpaque(false);
		this.setSize(w,h);
		this.setLayout(null);
		this.Listeners();
		this.Text();
		this.Underline();
		this.Icon(s);
		this.Hint();
		this.Organizar();
	}
		
	public TextField(int w,int h) {
		this.setOpaque(false);
		this.setSize(w,h);
		this.setLayout(null);
		this.Listeners();
		this.Underline();
		this.Text();
		this.Hint();
		this.Organizar();
	}

	private void Text(){
		this.text.setSize(this.getWidth()-30,20);
		this.text.setLocation(35,underline.getY()+text.getHeight());
		this.text.setForeground(Color.gray);
		this.text.setFont(new Font("Roboto",0,17));
		this.text.setOpaque(false);
		this.text.setBorder(null);
		this.text.addKeyListener(showhint);
	}
	
	private void Icon(String s) {
		icon = new Imagen(iconwidth,iconheight,s);
		this.icon.setOpaque(false);
		this.icon.setLocation(2,underline.getY()-icon.getHeight()-2);
	}

	private void Icon() {
		this.icon.setSize(iconwidth,iconheight);
		this.icon.setBackground(Color.red);
		this.icon.setLocation(2,2);
	}
	
	private void Underline() {
		this.underline.setSize(this.getWidth(),2);
		this.underline.setBackground(Color.gray);
		this.underline.setOpaque(true);
		this.underline.setLocation(0,this.getHeight()-10);
	}
	
	private void Hint() {
		this.hint.setBackground(Color.white);
		this.hint.setOpaque(false);
		this.hint.setSize(this.text.getWidth(),this.text.getHeight());
		this.hint.setLocation(this.text.getX()+1,this.text.getY());
		this.hint.setFont(this.text.getFont());
		this.hint.setForeground(new Color(220,220,220));
		this.hint.setText(texto);
	}
	
	private void Organizar() {
		this.setLayer(hint, 3);
		this.setLayer(text, 2);
		if(icon!=null)this.setLayer(icon, 0);
		this.setLayer(underline, 1);
		this.add(hint);
		if(icon!=null)this.add(icon);
		this.add(text);
		this.add(underline);
	}
	
	private void Listeners() {
		sh= new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				
				hint.setVisible(false);
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if(e.getDocument().getLength()==0)hint.setVisible(true);
				
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				System.out.println("CAMBIO3");
				
			}
			

			
		};

		text.getDocument().addDocumentListener(sh);
		showhint= new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
				
			@Override
			public void keyReleased(KeyEvent e) {
				if(!text.getText().isEmpty()&&text.getText().charAt(0)==' ')TextField.this.text.setText("");
			}
			
		};
	
		NUMERICMODE = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c=e.getKeyChar();
				if(c=='-') {
					if(text.getText().length()!=0)
						e.consume();					
				}
				else if(c=='.') {
					if(text.getText().indexOf('.')>-1)
						e.consume();
				}
				else if(c<'0'||c>'9')e.consume();
				
			}

			@Override
			public void keyPressed(KeyEvent e) {

				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		NoFirstSpace = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				//System.out.println(e.getKeyChar()+" "+e.VK_SPACE);
				if(hint.isVisible()&&e.getKeyChar()==(char)e.VK_SPACE)e.consume();
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		};
		text.addKeyListener(NoFirstSpace);
	}

	public void KeyButton(JButton j,char c) {
		KeyListener k=new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getExtendedKeyCode()==(int) c) {
					j.doClick();
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		};
		text.addKeyListener(k);
		
	}
	
	public void KeyToNext(char c) {
		KeyListener k=new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getExtendedKeyCode()==c) {
					TextField.this.text.transferFocus();
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		};
		text.addKeyListener(k);	
	}
	
	public int centrarx(JComponent a) {
		return (int)((a.getWidth()/2)-(this.getWidth()/2));
		
	}
	
	public int centrary(JComponent a) {
		return (int)((a.getHeight()/2)-(this.getHeight()/2));
	}
	
	public void changeHint(String s) {
		this.hint.setText(s);
		//if(this.text.getText().isEmpty()||this.text.getText()==texto)this.text.setText(s);
		this.texto=s;
	}

	public void setNumericMode() {
		this.text.addKeyListener(NUMERICMODE);
	}
	
	public void setNormalMode() {
		try{
			text.removeKeyListener(NUMERICMODE);
		}
		catch(Exception e) {
			System.out.println("Ya esta en modo normal");
		}
	}
	
	public BigDecimal toBigDecimal() {	
		return new BigDecimal(text.getText());
		/*
		float f=0;
		String s=text.getText();
		int p=s.indexOf('.');
		if(p==-1)p=s.length();	
		
		for(int i=p-1,j=1;i>=0;i--) {
			f+=(float)(s.charAt(i)-48)*j;
			j*=10;
		}
		
		for(int i=p+1,j=10;i<s.length();i++) {
			f+=(float)(s.charAt(i)-48)*(float)1/j;
			j*=10;
		}
		
		System.out.println("Turned into "+(f));
		return BigDecimal.valueOf(f);
		*/
		
	}
	
}
