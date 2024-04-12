package bills;

import com.nobody.adMEnestrator.Entry;
import com.nobody.adMEnestrator.ChargeBar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.html.parser.ParserDelegator;

import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import Ventana.Listeners;
import Ventana.TextField;
import com.nobody.adMEnestrator.ScrollPanel;
import Ventana.Imagen;


public class App extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Color bg;
	private Color fg;
	private Color fgh;
	private Color maincolor;
	private Color altern;
	private String username;
	private Base basePanel;
	private GastoLabel gastoLabel;
	private FechaLabel fechaLabel;
	private Data data;
	private BigDecimal total = new BigDecimal(0);
	private Imagen borde;
	private Set<String> categorias = new TreeSet<>();
	private Set<String> items = new TreeSet<>();
	private int bordersize = 3;
	private byte MODE;
	String  imageruta="/images/rey_mode/";
	Font  fn = new Font("Arciform",Font.PLAIN,20);
	
	private Map<String, LinkedList<Long>> tabla = new HashMap<>();

	private class Login extends JDialog {
		JLabel closebt;
		JPanel back;
		JLabel logintxt;
		TextField user;
		ChargeBar chb;
		Imagen close;
		public JButton SignUp;
		int width = 300;
		int height = 175;
		int movinsu = 0;
		int su1 = 120;
		int su2 = height + 1;

		public Login() {
			this.setAlwaysOnTop(true);
			this.setFocusable(true);
			this.setUndecorated(true);
			Listeners.addWindowsMove(this);
			this.setSize(width, height);
			this.setBackground(fg);
			this.setLayout(null);
			back();
			Closebt();
			logintxt();
			user();
			signup();
			back.add(user);
			this.setLocationRelativeTo(null);
			user.text.requestFocus();
			Imagen img = new Imagen(width, height, "/images/login_" + MODE + ".png");
			back.add(img);
			this.setVisible(true);
		}

		private void back() {
			back = new JPanel();
			back.setSize(this.getSize());
			back.setLayout(null);
			back.setBackground(bg);
			chb = new ChargeBar(Login.this.getWidth(), 10);
			chb.setLocation(0, Login.this.getHeight() - chb.getHeight());
			chb.setVisible(false);
			chb.barcolor = maincolor;
			back.add(chb);
			this.add(back);
		}

		private void Closebt() {
			close = new Imagen(30, 30, "/images/exit_" + MODE + ".png");
			close.setLocation(this.getWidth() - close.getWidth(), 0);
			Listeners.add_Exit(close);
			/*
			 * closebt = new JLabel(); closebt.setSize(40, 25);
			 * closebt.setHorizontalAlignment(SwingConstants.CENTER);
			 * closebt.setVerticalAlignment(SwingConstants.CENTER);
			 * closebt.setLocation(this.getWidth()-closebt.getWidth(),0);
			 * closebt.setFont(new Font("arial", Font.BOLD, 15)); closebt.setForeground(fg);
			 * closebt.setText("X"); Listeners.addRedBG(closebt); back.add(closebt);
			 */
			back.add(close);			
			
		}

		private void logintxt() {
			logintxt = new JLabel();
			logintxt.setSize(100, 50);
			logintxt.setFont(new Font("arial", Font.ITALIC, 30));
			logintxt.setText("Login");
			logintxt.setHorizontalAlignment(SwingConstants.CENTER);
			logintxt.setVerticalAlignment(SwingConstants.CENTER);
			logintxt.setForeground(fg);

			logintxt.setLocation((int) ((back.getWidth() / 2) - (logintxt.getWidth() / 2)), 0);
			back.add(logintxt);
		}

		private void user() {
			user = new TextField(250, 50, "User", "/images/user_" + MODE + ".png");
			user.setLocation(user.centrarx(back), user.centrary(back));
			user.requestFocus();
			user.text.setForeground(fg);
			user.hint.setForeground(fgh);
			user.text.setCaretColor(fg);
		}

		private void signup() {
			SignUp = new JButton();
			SignUp.setCursor(new Cursor(Cursor.HAND_CURSOR));
			SignUp.setSize(75, 30);
			SignUp.setText("Sign In");
			SignUp.setBackground(maincolor);
			SignUp.setOpaque(true);
			SignUp.setLocation((back.getWidth() / 2) - (SignUp.getWidth() / 2), su2);
			SignUp.setBorderPainted(false);
			SignUp.setForeground(Color.white);
			SignUp.setEnabled(false);
			SignUp.setActionCommand("\n");
			ActionListener Next = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					chb.setVisible(true);
					chb.start();
					App.this.username = capitalizar(user.text.getText().toCharArray());
					// App.this.setVisible(true);
					try {
						App.this.data = new Data(Data.getRutaRecurso(App.this.username));
					} catch (URISyntaxException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					(new Thread() {
						public void run() {
							Login.this.SignUp.removeActionListener(Login.this.SignUp.getActionListeners()[0]);
							App.this.Main();
							Login.this.setVisible(false);
							chb.stop();
						}
					}).start();
				}

			};
			SignUp.addActionListener(Next);
			user.KeyButton(SignUp, '\n');
			ComponentListener habilitar = new ComponentListener() {

				@Override
				public void componentResized(ComponentEvent e) {

				}

				@Override
				public void componentMoved(ComponentEvent e) {

				}

				@Override
				public void componentShown(ComponentEvent e) {
					SignUp.setEnabled(false);
					movinsu = -1;
					(new Thread() {
						public void run() {
							while (movinsu == -1 && SignUp.getY() < su2) {
								SignUp.setLocation(SignUp.getX(), SignUp.getY() + 4);
								SignUp.repaint();
								try {
									sleep(1);
								} catch (Exception e) {

								}
							}
						}
					}).start();
				}

				@Override
				public void componentHidden(ComponentEvent e) {
					SignUp.setEnabled(true);
					movinsu = 1;
					(new Thread() {
						public void run() {
							while (movinsu == 1 && SignUp.getY() > su1) {
								SignUp.setLocation(SignUp.getX(), SignUp.getY() - 3);
								SignUp.repaint();
								try {
									sleep(1);
								} catch (Exception e) {

								}
							}

						}
					}).start();
				}

			};
			user.hint.addComponentListener(habilitar);
			back.add(SignUp);
		}

		private void mostrar() {

			(new Thread() {
				public void run() {
					int x = width;
					int y = height;
					Login.this.setSize(0, 0);
					Login.this.setLocationRelativeTo(null);
					Login.this.setVisible(true);
					long tiempo = 100;
					long ti = System.currentTimeMillis();
					long tf = ti + tiempo;
					while (System.currentTimeMillis() < tf) {
						long ta = System.currentTimeMillis() - ti;
						int xa = (int) (((float) ta / tiempo) * x);
						int ya = (int) (((float) ta / tiempo) * y);
						Login.this.setSize(xa, ya);
						Login.this.setLocationRelativeTo(null);
						// Login.this.repaint();

						try {
							sleep(1);
						}

						catch (Exception e) {

						}
					}
					Login.this.setSize(x, y);
					Login.this.setLocationRelativeTo(null);

				}
			}).start();
		}

	}

	private class Data {
		public String MESES[] = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUTS", "SEPTEMPBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
		public String MONTHS[] = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };
		public static FileReader fr;
		public static FileWriter fw;
		public boolean newuser;
		public String dir = getRuta();
		public String ss = System.getProperty("file.separator");
		public String date;
		public BigDecimal gasto = new BigDecimal("0.00");
		Map<Long, Entry> data = new HashMap();
		int next = 0;

		public Data(String s) throws URISyntaxException, IOException {
			Date today = new Date();
			date = today.getYear() + "" + (today.getMonth());
			chek_dir();
			s = dir + ss + date;
			
			try {
				System.out.println("buscando fichero en " + s);
				fr = new FileReader(s);
			} catch (Exception e) {
				String to = getRutaRecurso(s);

				newuser = true;
				System.out.println("new user:" + newuser);
				try {
					fw = new FileWriter(to, true);
					fw.close();
				} catch (IOException e1) {
				}
			}
			Cargar_Datos();
		}

		public void chek_dir() {
			String[] s = new String[] { "Data", username };
			String file = dir;
			for (int i = 0; i < s.length; i++) {
				file += ss + s[i];
				File directorio = new File(file);
				if (!directorio.exists()) {
					newuser = true;
					if (directorio.mkdirs()) {
						System.out.println("Directorio creado " + file);
					} else {
						System.out.println("Error al crear directorio");
					}
				}
			}
			dir = file;
		}

		public void Cargar_Datos() {
			gasto = new BigDecimal("0.00");
			tabla.clear();
			data.clear();
			categorias.clear();
			items.clear();
			FileInputStream fis = null;
			try {
				String to = dir + ss + date;
				fis = new FileInputStream(to);
				ObjectInputStream oos = new ObjectInputStream(fis);
				Map<Long, Entry> readObject = (Map<Long, Entry>) oos.readObject();
				data = readObject;

				fis.close();
				next = data.keySet().size();
				for (long i : data.keySet()) {
					Entry e = data.get(i);
					gasto = gasto.add(e.Precio);
					System.out.println(gasto.toString());
					System.out.println("------------------------------------------------------\n" + e.Nombre + "|\t$"
							+ e.Precio + "|\t" + e.Tipo + "|\t" + e.ID
							+ "|\n------------------------------------------------------\n");
					if (tabla.get(e.Tipo) == null)
						tabla.put(e.Tipo, new LinkedList<Long>());
					tabla.get(e.Tipo).add(i);
					categorias.add(e.Tipo);
					items.add(e.Nombre);
				}

			} catch (Exception e) {
				System.out.println(e);
			}

		}

		public void Guardar_Datos() {
			FileOutputStream fos = null;
			String to = dir + ss + date;
			try {
				fos = new FileOutputStream(to);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(data);
				fos.close();
			} catch (Exception e) {

			}
		}

		public void change_to(String s) {
			System.out.println("cambiando a " + s);
			date = s;
			total = new BigDecimal(0);
			Cargar_Datos();
		}

		private static String getRutaRecurso(String filename) throws URISyntaxException, IOException {
			return getRuta() + filename;
		}

		private static String getRuta() throws IOException, URISyntaxException {
			final ProtectionDomain domain;
			final CodeSource source;
			final URL url;
			final URI uri;
			String DirectoryPath;
			String separador_directorios = System.getProperty("file.separator");
			String JarURL;
			File auxiliar;
			domain = App.class.getProtectionDomain();
			source = domain.getCodeSource();
			url = source.getLocation();
			uri = url.toURI();
			JarURL = uri.getPath();
			auxiliar = new File(JarURL);
			if (auxiliar.isDirectory()) {
				auxiliar = new File(auxiliar.getParentFile().getParentFile().getPath());
				DirectoryPath = auxiliar.getCanonicalPath() + separador_directorios;
			} else {
				JarURL = auxiliar.getCanonicalPath();
				DirectoryPath = JarURL.substring(0, JarURL.lastIndexOf(separador_directorios) + 1);

			}
			return DirectoryPath;

		}
	}

	private class GastoLabel extends Container{
		JLabel total = new JLabel("250.");
		JLabel coma = new JLabel("00");
		JLabel dolar = new JLabel("$");
		Container aux = new Container();
		
		int tm=110;

		public GastoLabel() {
			this.setLayout(null);
			this.setSize(474,240);
			this.setBackground(Color.red);
			total.setForeground(Color.black);
			total.setVerticalAlignment(JLabel.BOTTOM);
			dolar.setForeground(Color.black);
			dolar.setVerticalAlignment(JLabel.BOTTOM);
			coma.setForeground(Color.black);
			coma.setVerticalAlignment(JLabel.BOTTOM);
			aux.add(total);
			aux.add(coma);
			aux.add(dolar);
			this.add(aux);
			fuentes(fn.getName(),fn.getStyle(),tm);
			actualizar();
		}
		
		public void actualizar() {
			String s=data.gasto.toString();
			int p=s.indexOf('.')+1;
			total.setText(s.substring(0,p-1)+".");			
			coma.setText(s.substring(p,p+2));
			ajustar();
			
		}
		
		private boolean tamano_valido() {
			return aux.getX()>=0&&aux.getX()+aux.getWidth()<=this.getWidth()&&total.getFont().getSize()< tm;
		}
		
		private void ajustar() {						
				
				while(tamano_valido()) {
					total.setSize(ancho(total),alto(total));
					dolar.setSize(ancho(dolar),alto(dolar));
					coma.setSize(ancho(coma),alto(coma));
					posicionar();
					Font fn=total.getFont();
					fuentes(fn.getName(),fn.getStyle(),fn.getSize()+1);
				}
				while(!tamano_valido()) {
					total.setSize(ancho(total),alto(total));
					dolar.setSize(ancho(dolar),alto(dolar));
					coma.setSize(ancho(coma),alto(coma));
					posicionar();
					Font fn=total.getFont();
					fuentes(fn.getName(),fn.getStyle(),fn.getSize()-1);
				}
				total.setSize(ancho(total),alto(total));
				dolar.setSize(ancho(dolar),alto(dolar));
				coma.setSize(ancho(coma),alto(coma));
				posicionar();
		
		}				
							
		private void posicionar() {
			dolar.setLocation(0,0);
			coma.setLocation(dolar.getWidth()+total.getWidth(),0);
			total.setLocation(dolar.getWidth(),dolar.getHeight()*3/4);
			aux.setSize(dolar.getWidth()+total.getWidth()+coma.getWidth(),total.getY()+total.getHeight()-dolar.getY());
			aux.setLocation((this.getWidth()/2)-(aux.getWidth()/2),(this.getHeight()/2)-(aux.getHeight()/2));
		}
		
		private void fuentes(String name,int style,int size) {
			total.setFont(new Font(name,style,size));
			coma.setFont(new Font(name,style,size*3/5));
			dolar.setFont(new Font(name,style,size*3/5));
		}
	}

	private class FechaLabel extends Container{
		JLabel ano = new JLabel();
		JLabel mes = new JLabel();
		Container aux = new Container();
		
		public FechaLabel() {
			this.setLayout(null);
			aux.setLayout(null);
			this.setSize(300,70);
			ano.setForeground(Color.black);
			mes.setForeground(Color.black);
			fuentes(fn.getFontName(),fn.getStyle(),60);
			ajustar();
			this.add(ano);
			this.add(mes);
			
		}
		
		private void fecha(){
			int D = 1900+Integer.parseInt(data.date.substring(0,3));
			ano.setText(""+D);
			D = Integer.parseInt(data.date.substring(3));
			mes.setText(data.MESES[D]);
		}
		
		private void fuentes(String name,int style,int size) {
			ano.setFont(new Font(name,style,size*3/5));
			mes.setFont(new Font(name,style,size));
		}
		
		private void ajustar() {
			fecha();
			ano.setSize(ancho(ano),alto(ano));
			mes.setSize(ancho(mes),alto(mes));
			posicionar();
			
		}
		
		private void posicionar() {
			mes.setLocation(0,ano.getHeight()*1/4);
			ano.setLocation(mes.getWidth(),0);
			this.setSize(mes.getWidth()+ano.getWidth(),mes.getY()+mes.getHeight()-ano.getY());
			System.out.println("mes: "+mes.getLocation().toString()+" "+mes.getSize().toString());
			System.out.println("ano: "+ano.getLocation().toString()+" "+ano.getSize().toString());
			System.out.println("this: "+this.getLocation().toString()+" "+this.getSize().toString());
			
		}
	}
	
	private class CloseButton extends Ventana.Imagen{
		ImageIcon arr[] = new ImageIcon[6];
		
		public CloseButton(int w,int h) {
			this.setSize(w,h);
			int frame=0;
			for(int i=0;i<6;i++) 
				arr[i] =new ImageIcon(getClass().getResource(imageruta+"close/close_"+frame+++".png"));
			this.setImagen(arr[0]);
			Listeners.add_animation_mouse(this, 100, arr);
			Listeners.add_Exit(this);
		}
		
	}
	
	private class Base extends JLayeredPane{
		Imagen fondo = new Imagen();
		
		public Base() {
			__init();
		}
		
		public Base(int x,int y,int w,int h) {
			this.setSize(w,h);
			this.setLocation(x,y);
			__init();
		}
		
		private void __init() {
			Listeners.addWindowsMove(App.this);
			fondo.setSize(this.getSize());
			setMode();
			this.setLayer(fondo, 0);
			this.add(fondo);
		}
		
		public void setMode() {
			fondo.setImagen(imageruta+"base_"+MODE+".png");
		}
	}
	
	private class Token extends Container{
		int anchoNombre=80;
		int anchoPrecio=80;
		int inicioNombre=15;
		int inicioPrecio=inicioNombre+anchoNombre;
		
		LinkedList<JTextField> nombre;
		
		Token(int x,int y,int width,int height){
			this.setSize(width,height);
			this.setLocation(x,y);
		}
		
		
	}
	
	public App() {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,new File(getClass().getResource(imageruta+"fonts/Arciform.otf").getFile())));
		}
		catch(Exception e) {
			
		}
		this.setIconImage(new ImageIcon(getClass().getResource("/images/Icon_"+MODE+".png")).getImage());
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setSize(1080, 634);
		this.setUndecorated(true);
		this.setLayout(null);
		this.setBackground(bg);
		this.setLocationRelativeTo(null);
		setMode((byte)0);
		new Login().user.text.requestFocus();
	}
	
	private void Main() {
		basePanel = new Base(0,0,this.getWidth(),this.getHeight());
		this.add(basePanel);
		
		create_closeButton();
		crear_gastoLabel();
		crear_fechaLabel();
		this.setVisible(true);
	}
	
	private void crear_fechaLabel() {
		fechaLabel = new FechaLabel();
		fechaLabel.setLocation(135,20);
		basePanel.setLayer(fechaLabel,1);
		basePanel.add(fechaLabel);
	}
	
	private void create_closeButton() {
		CloseButton cb = new CloseButton(100,95);
		cb.setLocation(basePanel.getWidth()-cb.getWidth(),0);
		basePanel.setLayer(cb, 1);
		basePanel.add(cb);
	}
	
	private void crear_gastoLabel() {
		gastoLabel = new GastoLabel();
		gastoLabel.setLocation(105,84);
		basePanel.setLayer(gastoLabel, 1);
		basePanel.add(gastoLabel);
	}
	
	private void setMode(byte mode) {
		MODE=mode;
		if (borde != null) {
			borde.setVisible(false);
			borde = new Imagen(this.getWidth(), this.getHeight(), "/images/bordes_"+MODE+".png");
			this.add(borde);
		} else
			borde = new Imagen(this.getWidth(), this.getHeight(), "/images/bordes_"+MODE+".png");
		bg = Color.DARK_GRAY;
		fg = Color.white;
		fgh = Color.lightGray;
		altern = new Color(bg.getRed() - 10, bg.getGreen() - 10, bg.getBlue() - 10);
		maincolor = new Color(20, 144, 255);
		change_mode(this);

	}
	
	private void change_mode(Container c) {
		if(c.getClass()==Base.class) {
			((Base)c).setMode();
		}
		
		for(Component com:c.getComponents())
			change_mode((Container)com);
	}

	private int ancho(JLabel j) {
		int ancho=0;
		String s=j.getText();
		for(int i=0;i<s.length();i++) {
			ancho+=j.getFontMetrics(j.getFont()).charWidth(s.charAt(i));
		}
		return ancho;
	}
	
	private int alto(JLabel j) {
		FontMetrics f =j.getFontMetrics(j.getFont());
		return f.getHeight();
	}

	private int ancho(JTextField j) {
		int ancho=0;
		String s=j.getText();
		for(int i=0;i<s.length();i++) {
			ancho+=j.getFontMetrics(j.getFont()).charWidth(s.charAt(i));
		}
		return ancho;
	}
	
	private int alto(JTextField j) {
		FontMetrics f =j.getFontMetrics(j.getFont());
		return f.getHeight();
	}
	
	
	private String capitalizar(char s[]) {
		int r = 'a' - 'A';
		for (int i = 0; i < s.length; i++) {
			if (i == 0) {
				if (s[i] >= 'a' && s[i] <= 'z')
					s[i] -= r;
			} 
			else if (s[i] >= 'A' && s[i] <= 'Z')
				s[i] += r;
		}

		return new String(s);
	}
	
	public static void main(String[] args) {
		new App();
	}

}
