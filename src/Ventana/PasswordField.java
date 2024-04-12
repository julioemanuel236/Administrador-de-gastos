package Ventana;

import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.AttributeSet.ColorAttribute;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JComponent;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.event.*;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLayeredPane;

public class PasswordField extends JLayeredPane{
	JLabel hint = new JLabel();
	String texto="Contraseña";
	JPasswordField text = new JPasswordField();
	JLabel underline = new JLabel();
	JLabel icon = new JLabel();
	int iconwidth=25;
	int iconheight=25;
	KeyListener showhint;

	public PasswordField(int w,int h,String hint) {
		this.texto=hint;
		this.setOpaque(false);
		this.setSize(w,h);
		this.setLayout(null);
		this.Listeners();
		this.Text();
		this.Underline();
		this.Icon();
		this.Hint();
		this.Organizar();
	}
	
	public PasswordField(int w,int h,String hint,String s) {
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
	
	public PasswordField(int w,int h) {
		this.setOpaque(false);
		this.setSize(w,h);
		this.setLayout(null);
		this.Listeners();
		this.Text();
		this.Underline();
		this.Icon();
		this.Hint();
		this.Organizar();
	}
	
	private void Text(){
		this.text.setSize(this.getWidth()-30,40);
		this.text.setLocation(35,this.getHeight()-44);
		this.text.setForeground(Color.gray);
		this.text.setFont(new Font("Roboto",0,20));
		this.text.setOpaque(false);
		this.text.setBorder(null);
		this.text.addKeyListener(showhint);
	}
	
	private void Icon() {
		this.icon.setSize(iconwidth,iconheight);
		this.icon.setOpaque(true);
		this.icon.setBackground(Color.blue);
		this.icon.setLocation(2,2);
	}
	
	private void Icon(String s) {
		icon = new Imagen(iconwidth,iconheight,s);
		this.icon.setOpaque(false);
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
		this.setLayer(hint, 2);
		this.setLayer(text, 1);
		this.setLayer(icon, 0);
		this.setLayer(underline, 3);
		this.add(hint);
		this.add(icon);
		this.add(text);
		this.add(underline);
	}
	
	private void Listeners() {
	
		showhint= new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println(e.getKeyChar());
				if(PasswordField.this.text.getText().isEmpty()&&e.getExtendedKeyCode()!=8&&e.getKeyChar()!='?') {
					hint.setVisible(false);
				}
				
				if(PasswordField.this.text.getText().length()==1&&e.getExtendedKeyCode()==8) {
					PasswordField.this.hint.setVisible(true);	
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			
		};

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
	
	
}
