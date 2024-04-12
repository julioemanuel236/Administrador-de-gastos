package com.nobody.adMEnestrator;

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
import javax.swing.JWindow;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
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
import Ventana.Imagen;

public class App extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Color 						bg;
	private Color 						fg;
	private Color 						fgh;
	private Color 						maincolor;
	private Color 						altern;
	private String 						username;
	private JLayeredPane 				Main;
	private JPanel 						root;
	private JPanel 						topbar;
	private JPanel 						Menu;
	private JLabel 						closebt;
	private JLabel 						clock;
	private JLabel 						userlabel;
	private Data 						data;
	private NewZone 					newz;
	private CalendarZone 				calenz;
	private ShowZone 					showz;
	private OptionsZone					optionsz;
	private SearchZone					searchz;
	private MenuBar 					menubar;
	private MoreMoneyZone				moremoneyz;
	private BigDecimal 					total 		= 	new BigDecimal(0);
	private Imagen 						borde;
	private Set<String> 				categorias 	=	new TreeSet<>();
	private Set<String> 				items 		= 	new TreeSet<>();
	private int 						bordersize = 3;
	private byte 						MODE;
	
	private Map<String, LinkedList<Long>> tabla  =   new HashMap<>();

	
	private class Login extends JDialog {
		JLabel closebt;
		JPanel back;
		JLabel logintxt;
		TextField user;
		ChargeBar chb ;
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
			Imagen img = new Imagen(width,height,"/images/login_"+MODE+".png");
			back.add(img);
			this.setVisible(true);
		}
		
		private void back() {
			back = new JPanel();
			back.setSize(this.getSize());
			back.setLayout(null);
			back.setBackground(bg);
			chb = new ChargeBar(Login.this.getWidth(),10);
			chb.setLocation(0,Login.this.getHeight()-chb.getHeight());
			chb.setVisible(false);
			chb.barcolor=maincolor;
			back.add(chb);
			this.add(back);
		}

		private void Closebt() {
			close = new Imagen(30,30,"/images/exit_"+MODE+".png");
			close.setLocation(this.getWidth()-close.getWidth(),0);
			Listeners.add_Exit(close);
			/*closebt = new JLabel();
			closebt.setSize(40, 25);
			closebt.setHorizontalAlignment(SwingConstants.CENTER);
			closebt.setVerticalAlignment(SwingConstants.CENTER);
			closebt.setLocation(this.getWidth()-closebt.getWidth(),0);
			closebt.setFont(new Font("arial", Font.BOLD, 15));
			closebt.setForeground(fg);
			closebt.setText("X");
			Listeners.addRedBG(closebt);
			back.add(closebt);*/
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
					//App.this.setVisible(true);
					try {
						App.this.data = new Data(Data.getRutaRecurso(App.this.username));
					} catch (URISyntaxException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					(new Thread(){
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
					int x=width;
					int y=height;
					Login.this.setSize(0, 0);
					Login.this.setLocationRelativeTo(null);
					Login.this.setVisible(true);
					long tiempo = 100;
					long ti=System.currentTimeMillis();
					long tf=ti+tiempo;
					while(System.currentTimeMillis()<tf) {
						long ta=System.currentTimeMillis()-ti;
						int xa=(int)(((float)ta/tiempo)*x);
						int ya=(int)(((float)ta/tiempo)*y);
						Login.this.setSize(xa, ya);
						Login.this.setLocationRelativeTo(null);
						//Login.this.repaint();
						
						try {
							sleep(1);
						}

						catch (Exception e) {

						}
					}
					Login.this.setSize(x,y);
					Login.this.setLocationRelativeTo(null);

				}
			}).start();
		}

	}

	private class Data {
		public String MONTHS[]= {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
		public static FileReader fr;
		public static FileWriter fw;
		public boolean newuser;
		public String dir=getRuta();
		public String ss=System.getProperty("file.separator");
		public String date;
		Map<Long, Entry> data = new HashMap();
		int next = 0;

		public Data(String s) throws URISyntaxException, IOException {
			Date today = new Date();
			date=today.getYear()+""+(today.getMonth());
			chek_dir();
			s=dir+ss+date;
			try {
				System.out.println("buscando fichero en "+s);
				fr = new FileReader(s);
			} catch (Exception e) {
				String to =getRutaRecurso(s);

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
			String[] s=new String[] {"Data",username};
			String file=dir;
			for(int i=0;i<s.length;i++) {
				file+=ss+s[i];
			 File directorio = new File(file);
		        if (!directorio.exists()) {
		        	newuser=true;
		            if (directorio.mkdirs()) {
		                System.out.println("Directorio creado "+file);
		            } else {
		                System.out.println("Error al crear directorio");
		            }
		        }
			}
			dir=file;
		}
		
		public void Cargar_Datos() {
			if(userlabel!=null)
			userlabel.setText("$0");
			tabla.clear();
			data.clear();
			categorias.clear();
			items.clear();
			FileInputStream fis = null;
			try {
				String to = dir+ss+date;
				fis = new FileInputStream(to);
				ObjectInputStream oos = new ObjectInputStream(fis);
				Map<Long, Entry> readObject = (Map<Long, Entry>) oos.readObject();
				data = readObject;

				fis.close();
				next = data.keySet().size();
				for (long i : data.keySet()) {
					Entry e = data.get(i);
					System.out.println( "------------------------------------------------------\n"
					+e.Nombre+"|\t$"+e.Precio+"|\t"+e.Tipo+"|\t"+e.ID+
									 "|\n------------------------------------------------------\n");
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

		public void Guardar_Datos(){
			FileOutputStream fos = null;
			String to = dir+ss+date;
			try {
				fos = new FileOutputStream(to);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(data);
				fos.close();
			} catch (Exception e) {

			}
		}
		
		public void change_to(String s) {
			System.out.println("cambiando a "+s);
			date=s;
			showz.setVisible(false);
			showz.removeAll();
			showz=new ShowZone();
			showz.back.setSize(10,showz.back.getHeight());
			total=new BigDecimal(0);
			Cargar_Datos();
			(new Thread() {
				public void run() {
					generate_cate_zone();
				}
			}).start();
		}
		
		private static String getRutaRecurso(String filename) throws URISyntaxException, IOException {
			return getRuta() + filename;
		}
		
		private static String getRuta()  throws IOException, URISyntaxException {
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

	private class Zone extends JPanel{
		int Limitev;
		int Limiteh;
		
		boolean UP=true;
		boolean DOWN=false;
		boolean RIGHT=true;
		boolean LEFT=false;
		
		Imagen fondo;
		
		public Zone() {
			
		}
		
		protected void __fondo() {
			//System.out.println(this.getSize());
			if(fondo!=null)fondo.setVisible(false);
			fondo = new Imagen(this.getWidth(),this.getHeight(),"/images/fondo_" + MODE + "_transparente.png");
			this.add(fondo);
		}
		
		protected void UP() {
			
			UP = true;
			DOWN = false;
			(new Thread() {
				public void run() {
					int k = 30;
					while (UP && Zone.this.getY() >= -Zone.this.getHeight()) {

						Zone.this.setLocation(Zone.this.getX(), Zone.this.getY() - k);
						//if (k > 2)
							k++;
						try {
							sleep(10);
							Zone.this.repaint();
						} catch (Exception e) {

						}

					}
					if (!UP)
						return;
					Zone.this.setLocation(Zone.this.getX(), -Zone.this.getHeight());
				}
			}).start();
		}

		protected void RIGHT() {
			
			RIGHT = true;
			LEFT = false;
			(new Thread() {
				public void run() {
					int k = 30;
					while (RIGHT && Zone.this.getX() <= Limiteh) {

						Zone.this.setLocation(Zone.this.getX()+k, Zone.this.getY());
						//if (k > 2)
							k++;
						try {
							sleep(10);
							Zone.this.repaint();
						} catch (Exception e) {

						}

					}
					if (!RIGHT)
						return;
					Zone.this.setLocation(Limiteh,Zone.this.getY() );
				}
			}).start();
		}

		protected void LEFT() {
			
			LEFT = true;
			RIGHT = false;
			(new Thread() {
				public void run() {
					int k = 30;
					while (LEFT && Zone.this.getX() >= Limiteh-Zone.this.getWidth()) {

						Zone.this.setLocation(Zone.this.getX()-k, Zone.this.getY());
						//if (k > 2)
							k++;
						try {
							sleep(10);
							Zone.this.repaint();
						} catch (Exception e) {

						}

					}
					if (!LEFT)
						return;
					Zone.this.setLocation(Limiteh-Zone.this.getWidth(), Zone.this.getY());
				}
			}).start();
		}
				
		protected void DOWN() {
			DOWN = true;
			UP = false;
			(new Thread() {
				public void run() {
					
					int k = 10;
					while (DOWN && Zone.this.getY() < 5) {

						Zone.this.setLocation(Zone.this.getX(), Zone.this.getY() + k);
						//if (k > 2)
							k++;
						try {
							sleep(10);
							Zone.this.repaint();
						} catch (Exception e) {

						}
					}
					if (!DOWN)
						return;
					Zone.this.setLocation(Zone.this.getX(), 5);
				}
			}).start();
		}
	
	}

	private class WindowZone extends JDialog{

	
		int Limitev;
		int Limiteh;
	
		boolean UP=true;
		boolean DOWN=false;
		boolean RIGHT=true;
		boolean LEFT=false;
		
		Imagen fondo;
		
		public WindowZone() {
			
		}
		
		protected void __fondo() {
			//System.out.println(this.getSize());
			if(fondo!=null)fondo.setVisible(false);
			fondo = new Imagen(this.getWidth(),this.getHeight(),"/images/fondo_" + MODE + "_transparente.png");
			this.add(fondo);
		}
		
		protected void UP() {
			
			UP = true;
			DOWN = false;
			(new Thread() {
				public void run() {
					int k = 30;
					while (UP && WindowZone.this.getY() >= -WindowZone.this.getHeight()) {

						WindowZone.this.setLocation(WindowZone.this.getX(), WindowZone.this.getY() - k);
						//if (k > 2)
							k++;
						try {
							sleep(10);
							WindowZone.this.repaint();
						} catch (Exception e) {

						}

					}
					if (!UP)
						return;
					WindowZone.this.setLocation(WindowZone.this.getX(), -WindowZone.this.getHeight());
				}
			}).start();
		}

		protected void RIGHT() {
			
			RIGHT = true;
			LEFT = false;
			(new Thread() {
				public void run() {
					int k = 30;
					while (RIGHT && WindowZone.this.getX() <= Limiteh) {

						WindowZone.this.setLocation(WindowZone.this.getX()+k, WindowZone.this.getY());
						//if (k > 2)
							k++;
						try {
							sleep(10);
							WindowZone.this.repaint();
						} catch (Exception e) {

						}

					}
					if (!RIGHT)
						return;
					WindowZone.this.setLocation(Limiteh,WindowZone.this.getY() );
				}
			}).start();
		}

		protected void LEFT() {
			
			LEFT = true;
			RIGHT = false;
			(new Thread() {
				public void run() {
					int k = 30;
					while (LEFT && WindowZone.this.getX() >= Limiteh-WindowZone.this.getWidth()) {

						WindowZone.this.setLocation(WindowZone.this.getX()-k, WindowZone.this.getY());
						//if (k > 2)
							k++;
						try {
							sleep(10);
							WindowZone.this.repaint();
						} catch (Exception e) {

						}

					}
					if (!LEFT)
						return;
					WindowZone.this.setLocation(Limiteh-WindowZone.this.getWidth(), WindowZone.this.getY());
				}
			}).start();
		}
		
		
		protected void DOWN() {
			DOWN = true;
			UP = false;
			(new Thread() {
				public void run() {
					
					int k = 10;
					while (DOWN && WindowZone.this.getY() < 5) {

						WindowZone.this.setLocation(WindowZone.this.getX(), WindowZone.this.getY() + k);
						//if (k > 2)
							k++;
						try {
							sleep(10);
							WindowZone.this.repaint();
						} catch (Exception e) {

						}
					}
					if (!DOWN)
						return;
					WindowZone.this.setLocation(WindowZone.this.getX(), 5);
				}
			}).start();
		}
	}

	private class NewZone extends Zone {
		public boolean e1 = false, e2 = false, e3 = false;
		JWindow cpred = new JWindow();
		JWindow ipred = new JWindow();
		TextField name, price, categoria;
		JButton accept;
		

		public NewZone() {			
			this.setOpaque(false);
			this.setLayout(null);
			this.setBackground(bg);
			this.setSize(250, 220);
			this.setLocation((menubar.getWidth() / 2) - (this.getWidth() / 2)+menubar.getX(), -this.getHeight());

			__name();
			__price();
			__categoria();
			acceptbtn();
			__fondo();
			listeners();

			Main.setLayer(this, 1);
			Main.add(this);
		}
		
		private void __categoria() {
			categoria = new TextField(240, 50, "Tipe", "/images/tag_" + MODE + ".png");
			categoria.setLocation(categoria.centrarx(this), 110);
			categoria.hint.setForeground(fgh);
			categoria.text.setCaretColor(fg);
			categoria.text.setForeground(fg);
			categoria.text.getDocument().addDocumentListener(new DocumentListener() {

				@Override
				public void insertUpdate(DocumentEvent e) {
					cpred.removeAll();
					cpred.setVisible(false);
					cpred = new JWindow();
					crear_prediccion(cpred, categoria);
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					boolean b = false;
					try {
						b = !(e.getDocument().getText(0, e.getDocument().getLength()).isEmpty());
					} catch (Exception ee) {

					}
					if (b) {
						cpred.removeAll();
						cpred.setVisible(false);
						cpred = new JWindow();
						crear_prediccion(cpred, categoria);
					} else
						cpred.setVisible(false);
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					cpred.removeAll();
					cpred.setVisible(false);
					cpred = new JWindow();
					crear_prediccion(cpred, categoria);

				}

			});
			categoria.text.addFocusListener(new FocusListener() {

				@Override
				public void focusGained(FocusEvent e) {

				}

				@Override
				public void focusLost(FocusEvent e) {
					cpred.setVisible(false);

				}

			});
			categoria.KeyToNext((char) KeyEvent.VK_ENTER);
			this.add(categoria);
		}
		
		private void __price() {
			price = new TextField(240, 50, "Price", "/images/dolar_" + MODE + ".png");
			price.setLocation(price.centrarx(this), 60);
			price.text.setForeground(fg);
			price.hint.setForeground(fgh);
			price.text.setCaretColor(fg);
			price.setNumericMode();
			price.KeyToNext((char) KeyEvent.VK_ENTER);
			this.add(price);
		}
		
		private void __name() {
			name = new TextField(240, 50, "Item", "/images/item_" + MODE + ".png");
			name.setLocation(name.centrarx(this), 5);
			name.text.setForeground(fg);
			name.hint.setForeground(fgh);
			name.text.setCaretColor(fg);
			name.text.getDocument().addDocumentListener(new DocumentListener() {

				@Override
				public void insertUpdate(DocumentEvent e) {
					ipred.removeAll();
					ipred.setVisible(false);
					ipred = new JWindow();
					crear_prediccion(ipred, name);
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					boolean b = false;
					try {
						b = !(e.getDocument().getText(0, e.getDocument().getLength()).isEmpty());
					} catch (Exception ee) {

					}
					if (b) {
						ipred.removeAll();
						ipred.setVisible(false);
						ipred = new JWindow();
						crear_prediccion(ipred, name);
					} else
						ipred.setVisible(false);
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					ipred.removeAll();
					ipred.setVisible(false);
					ipred = new JWindow();
					crear_prediccion(ipred, name);

				}

			});
			name.text.addFocusListener(new FocusListener() {

				@Override
				public void focusGained(FocusEvent e) {

				}

				@Override
				public void focusLost(FocusEvent e) {
					ipred.setVisible(false);

				}

			});
			name.KeyToNext((char) KeyEvent.VK_ENTER);
			this.add(name);
		}
		
		private void listeners() {
			KeyListener escape = (new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==KeyEvent.VK_ESCAPE)menubar.newbtn.getMouseListeners()[0].mousePressed(null);; 

					
				}
			});
			name.text.addKeyListener(escape);	
			price.text.addKeyListener(escape);	
			categoria.text.addKeyListener(escape);	

		}

		private void crear_prediccion(JWindow pred, TextField tf) {
			pred.setSize(150, 0);
			try {
				pred.setOpacity(0.9f);
			}
			catch(Exception e) {
				
			}
			pred.setLocationRelativeTo(null);
			pred.setBackground(Color.red);
			ShowZone sz = new ShowZone(150, 0);
			sz.setLocation(0, 0);
			sz.vsb.active = App.this.showz.vsb.active;
			sz.vsb.active = App.this.showz.vsb.inactive;
			sz.border.color("all", maincolor);
			pred.add(sz);
			int k = 0;
			for (String i : tf.equals(name) ? items : categorias) {
				if (i.toLowerCase().contains(tf.text.getText().toLowerCase())) {

					JLabel j = new JLabel(i);
					j.setForeground(fg);
					j.setOpaque(true);
					j.setSize(sz.back.getWidth(), 40);
					j.setHorizontalAlignment(j.CENTER);
					j.setVerticalAlignment(j.CENTER);
					j.setBackground(sz.back.getBackground());
					j.addMouseListener(new MouseListener() {

						@Override
						public void mouseClicked(MouseEvent e) {

						}

						@Override
						public void mousePressed(MouseEvent e) {
							tf.text.setText(i);
							pred.setVisible(false);
						}

						@Override
						public void mouseReleased(MouseEvent e) {

						}

						@Override
						public void mouseEntered(MouseEvent e) {
							j.setBackground(altern);

						}

						@Override
						public void mouseExited(MouseEvent e) {
							j.setBackground(bg);

						}

					});
					j.setLocation(0, k * j.getHeight());
					pred.setSize(pred.getWidth(),
							pred.getHeight() + j.getHeight() < newz.getHeight() ? pred.getHeight() + j.getHeight() : newz.getHeight());
					sz.setSize(pred.getSize());
					sz.back.setSize(sz.back.getWidth(), sz.back.getHeight() + j.getHeight());
					sz.back.add(j);
					sz.border.setSize(sz.getSize());
					k++;
				}

			}
			/*
			 * int ft=0; int fws[]=tf.text.getFontMetrics(tf.text.getFont()).getWidths();
			 * String s=tf.text.getText(); for(int i=0;i<s.length();i++)
			 * ft+=fws[s.charAt(i)];
			 */
			int x = App.this.getX() + Main.getX() + this.getX() + this.getWidth();
			int y = App.this.getY() + root.getY() + Main.getY() + this.getY();
			pred.setLocation(x, y);
			pred.setVisible(true);

		}

		private void acceptbtn() {
			accept = new JButton();
			accept.setCursor(new Cursor(Cursor.HAND_CURSOR));
			accept.setSize(200, 30);
			accept.setText("Accept");
			accept.setBackground(maincolor);
			accept.setOpaque(true);
			// center x (this.getWidth()/2)-(accept.getWidth()/2)
			accept.setLocation((this.getWidth() / 2) - (accept.getWidth() / 2), this.getHeight());
			accept.setBorderPainted(false);
			accept.setForeground(fg);
			accept.setEnabled(false);
			accept.setFocusable(false);
			ActionListener Next = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent ee) {
					long id=System.currentTimeMillis();
					Entry e = new Entry(capitalizar(name.text.getText().toCharArray()), price.toBigDecimal(),
							capitalizar(categoria.text.getText().toCharArray()),id);
					
					data.data.put(id, e);
					if (tabla.get(e.Tipo) == null) {
						LinkedList<Long> ls = new LinkedList<>();
						ls.add(id);
						tabla.put(e.Tipo, ls);

					} else
						tabla.get(e.Tipo).add(id);

					Component sz[] = showz.back.getComponents();
					boolean existe = false;
					for (int i = 0; i < sz.length; i++) {
						if (sz[i].getName().equals(e.Tipo)) {
							existe = true;
							int k = tabla.get(e.Tipo).size();
							ShowZone s = (ShowZone) sz[i];
							configurar_panel_nuevo(s, e, k,e.ID);
							break;
						}
					}
					if (!existe) {
						int k = 1;
						int i = sz.length;
						String j = e.Tipo;
						categorias.add(e.Tipo);
						items.add(e.Nombre);
						ShowZone t = new ShowZone(showz.back.getWidth()-5, 5, 200, showz.getHeight() - 25);
						//System.out.println(t.getLocation());
						configurar_nueva_categoria(t, j);
						configurar_panel_nuevo(t, e, k,e.ID);
					}
					try {
						data.Guardar_Datos();
					} catch (Exception e1) {

						e1.printStackTrace();
					}

					name.text.setText(null);
					price.text.setText(null);
					categoria.text.setText(null);
					name.text.requestFocus();
				}

			};
			accept.addActionListener(Next);
			name.KeyButton(accept, '\n');
			price.KeyButton(accept, '\n');
			categoria.KeyButton(accept, '\n');

			ComponentListener habilitar = new ComponentListener() {
				int movinsu = 0;
				int su1 = 170;
				int su2 = NewZone.this.getHeight();

				@Override
				public void componentResized(ComponentEvent e) {

				}

				@Override
				public void componentMoved(ComponentEvent e) {

				}

				private void hab_dis() {
					if (e1 && e2 && e3) {
						accept.setEnabled(true);
						movinsu = 1;
						(new Thread() {
							public void run() {
								while (movinsu == 1 && accept.getY() > su1) {
									accept.setLocation(accept.getX(), accept.getY() - 3);
									accept.repaint();
									try {
										sleep(1);
									} catch (Exception e) {

									}
								}

							}
						}).start();

					} else {
						accept.setEnabled(false);
						movinsu = -1;
						(new Thread() {
							public void run() {
								while (movinsu == -1 && accept.getY() < su2) {
									accept.setLocation(accept.getX(), accept.getY() + 4);
									accept.repaint();
									try {
										sleep(1);
									} catch (Exception e) {

									}
								}
							}
						}).start();
					}
				}

				@Override
				public void componentShown(ComponentEvent e) {
					if (e.getComponent() == name.hint)
						e1 = false;
					else if (e.getComponent() == price.hint)
						e2 = false;
					else if (e.getComponent() == categoria.hint)
						e3 = false;
					hab_dis();
				}

				@Override
				public void componentHidden(ComponentEvent e) {
					if (e.getSource() == name.hint)
						e1 = true;
					else if (e.getSource() == price.hint)
						e2 = true;
					else if (e.getComponent() == categoria.hint)
						e3 = true;
					hab_dis();
				}
			
			};
			name.hint.addComponentListener(habilitar);
			price.hint.addComponentListener(habilitar);
			categoria.hint.addComponentListener(habilitar);
			this.add(accept);
		}

	}

	private class ShowZone extends ScrollPanel {

		public BigDecimal valor = new BigDecimal(0);

		public ShowZone() {
			this.setLocation(0, 0);
			this.setSize(App.this.Main.getWidth(), App.this.Main.getHeight());
			this.back.setSize(this.getSize());
			this.back.setBackground(bg);
			this.border.show("left", false);
			this.border.show("down", false);
			this.border.show("right", false);
			init();
		}

		public ShowZone(int w, int h) {
			this.setSize(w, h);
			this.back.setSize(w, h);
			this.back.setBackground(bg);
			init();
		}

		public ShowZone(int x, int y, int w, int h) {
			this.setSize(w, h);
			this.back.setSize(w, h);
			this.back.setBackground(bg);
			this.setLocation(x, y);
			init();
		}

		public void init() {
			this.vsb.toRight();
			this.hsb.toDown();
			this.hsb.inactive = Color.gray;
			this.hsb.active = maincolor;
			this.vsb.inactive = Color.gray;
			this.vsb.active = maincolor;
			this.border.setSize(this.getSize());
			this.border.color("all", this.vsb.active);
			Main.setLayer(this, 0);
			Main.add(this);
		}

		@Override
		public int hashCode() {
			return this.valor.hashCode();

		}

	}
	
	private class SearchZone extends Zone{
		Toolkit t = Toolkit.getDefaultToolkit();
		//Dimension s=t.getScreenSize();
		Dimension s=Main.getSize();
		TextField sb;
		JButton go = new JButton();
		JLayeredPane rshow;
		JPanel searchbar;
		ShowZone list;
		public SearchZone() {
			Limiteh=s.width;
			//this.setUndecorated(true);
			
			this.setLayout(null);
			this.setBackground(bg);
			//this.setSize(s.width/4, s.height);
			this.setSize(300,s.height);
			this.setLocation(s.width,(Main.getHeight()/2)-(getHeight()/2));
			//this.setLocation((menubar.getWidth() / 2) - (this.getWidth() / 2)+menubar.getX(), -this.getHeight());
			__results();
			__fondo();
			Main.setLayer(this, 1);
			Main.add(this);
		}
		
		private void __results() {
			rshow = new JLayeredPane();
			rshow.setSize(this.getSize());
			this.add(rshow);
			
			__searchbox();
			__searchbutton();
			__see_list();
			
			rshow.setLayer(searchbar, 2);
			rshow.add(searchbar);				
			/*sb.text.addFocusListener(new FocusListener() {

				@Override
				public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void focusLost(FocusEvent e) {
					SearchZone.this.toFront();
					
				}
				
			});
			this.setAlwaysOnTop(true);
			this.setVisible(true);*/
		}
	
		private void __see_list() {
			list = new ShowZone(this.getWidth(),this.getHeight());
			list.back.setSize(list.getWidth(),0);
			Imagen fondo = new Imagen(list.getWidth(),list.getHeight(),"/images/fondo_"+MODE+"_transparente.png");
			list.add(fondo);
			rshow.setLayer(list,1);
			rshow.add(list);
			
		}
		
		private void __searchbox() {
			searchbar = new JPanel();
			searchbar.setBackground(null);
			searchbar.setLayout(null);
			searchbar.setOpaque(false);
			searchbar.setSize(this.getWidth()-20,30);
			searchbar.setLocation((this.getWidth()/2)-(searchbar.getWidth()/2),this.getHeight()-searchbar.getHeight()-10);
			
			sb = new TextField(searchbar.getWidth()-25,50,"Search","/images/search_"+MODE+".png");
			sb.setLocation(0,-12);
			sb.hint.setForeground(fgh);
			sb.text.setForeground(fg);
			sb.text.setCaretColor(fg);
			sb.underline.setVisible(false);
			sb.KeyButton(go, '\n');
			searchbar.add(sb);
			
		}
		
		 public void __searchbutton(){
			go.setFocusable(false);
			go.setSize(15,30);
			int height = (searchbar.getHeight()/2)-(go.getHeight()/2);
			go.setLocation(searchbar.getWidth()-go.getWidth()-height,height);
			ImageIcon goicon= new ImageIcon(new ImageIcon(getClass().getResource("/images/next_"+MODE+".png")).getImage().getScaledInstance(8,16,Image.SCALE_SMOOTH));
			go.setIcon(goicon);
			searchbar.add(go);
			Imagen sbfond = new Imagen(searchbar.getWidth(),searchbar.getHeight(),"/images/fondo_"+MODE+"_transparente.png");
			searchbar.add(sbfond);
			go.setContentAreaFilled(false);
			go.setBorder(null);
			go.addActionListener(new ActionListener() {
				boolean animation=false;
				private void click_animation() {
					
					(new Thread() {
						public void run() {
							
							animation=true;
							int x = go.getX();
							for(int i=0;i<10;i++) {
								
								go.setLocation(go.getX()-1,go.getY());
								go.repaint();
								try {
									sleep(10);
								}catch(Exception e) {
									
								}
							}
							for(int i=0;i<10;i++) {
								
								go.setLocation(go.getX()+1,go.getY());
								go.repaint();
								try {
									sleep(1);
								}catch(Exception e) {
									
								}
							}
								animation=false;
						}
					}).start();
				}
				public void actionPerformed(ActionEvent e) {
					click_animation();
					String find=sb.text.getText();
					for(Component i:list.back.getComponents()) {
						i.setVisible(false);
						i=null;
					}
					list.back.setSize(list.back.getWidth(),0);
					list.back.removeAll();
					boolean c=true;
					for(long i:data.data.keySet()) {
						Entry E = data.data.get(i);
						if(E.Nombre.toLowerCase().contains(find.toLowerCase())||
						   E.Tipo.toLowerCase().contains(find.toLowerCase())) {
							//System.out.println(E.string());
							
							JPanel panel = new JPanel();
							JLabel nombre = new JLabel();
							JLabel precio = new JLabel();
							JLabel tipo = new JLabel();
							panel.setSize(list.getWidth(),40);
							panel.setBackground(c?bg:altern);						
							panel.setOpaque(true);
							panel.setLayout(null);
							int lwidth=panel.getWidth()/3;
							nombre.setOpaque(false);
							nombre.setFont(sb.text.getFont());
							nombre.setForeground(fg);
							nombre.setText(E.Nombre);
							nombre.setHorizontalAlignment(JLabel.LEFT);
							nombre.setSize(lwidth,panel.getHeight());
							nombre.setLocation(5,0);
							
							precio.setOpaque(false);
							precio.setFont(sb.text.getFont());
							precio.setForeground(fg);
							precio.setText(E.Precio.toString());
							precio.setHorizontalAlignment(JLabel.CENTER);
							precio.setSize(lwidth,panel.getHeight());
							precio.setLocation(lwidth*1,0);
							
							tipo.setOpaque(false);
							tipo.setFont(sb.text.getFont());
							tipo.setForeground(fg);
							tipo.setText(E.Tipo);
							tipo.setHorizontalAlignment(JLabel.RIGHT);
							tipo.setSize(lwidth,panel.getHeight());
							tipo.setLocation((lwidth*2)-5,0);
							
							panel.add(nombre);
							panel.add(precio);
							panel.add(tipo);
							list.add_item(panel, 0);
							c=!c;
						}
						
					}
				}
			});
			
		 }
	}
	
	private class MenuBar extends JPanel {
		Imagen newbtn;
		Imagen calenbtn;
		Imagen menubtn;
		Imagen searchbtn;
		Imagen moremoneybtn;
		Zone   seen = new Zone();
		ImageIcon menuarr[] = new ImageIcon[7];
		ToggleButton changebtn;
		int borde=10;
		
		public MenuBar() {
			this.setSize(topbar.getWidth() / 3, topbar.getHeight());
			this.setLocation((topbar.getWidth() / 2) - (this.getWidth() / 2), 0);
			
			for(int i=0;i<7;i++)
				menuarr[i]=new ImageIcon(getClass().getResource("/images/otros_"+MODE+"_"+i+".png"));
			this.setBackground(Color.red);
			this.setOpaque(true);
			this.setLayout(null);
			topbar.add(this);

		}

		public void ini_buttons() {
			borde=10;
			if (changebtn == null) {
				changebtn = new ToggleButton(15, this.getHeight()) {
					@Override
					public void actionPerformed() {

						switch (MODE) {
						case 2:
							MenuBar.this.changebtn.ball = Color.DARK_GRAY;

							setDarkMode();
							break;

						case 0:
							MenuBar.this.changebtn.ball = Color.white;
							setLightMode();
							break;
						}
					}
				};
				changebtn.setLocation(this.getWidth() - closebt.getWidth() - changebtn.getWidth(), 0);
				//changebtn.left = new Color(255, 127, 0);
				changebtn.left=Color.green;
				changebtn.right = new Color(20, 144, 222);
				changebtn.ball = bg;
				changebtn.border = altern;
			//	this.add(changebtn);
			}
			
			if (newbtn != null)
				newbtn.setVisible(false);
			newbtn = new Imagen(this.getHeight()-borde, this.getHeight()-borde,
					"/images/carrito_plus_" + MODE + ".png");
			newbtn.setLocation(borde/2, borde/2);
			this.add(newbtn);
			
			if(calenbtn != null) 
				calenbtn.setVisible(false);
			Font f=userlabel.getFont();
			
			calenbtn = new Imagen(this.getHeight()-borde, this.getHeight()-borde,
						"/images/calendario_" + MODE + ".png");
			calenbtn.setLocation(newbtn.getWidth()+borde,borde/2);
			calenbtn.setFont(new Font(f.getFontName(),f.BOLD,13));
			calenbtn.setVerticalAlignment(JLabel.CENTER);
			
			/*int w[]=calenbtn.getFontMetrics(calenbtn.getFont()).getWidths();
			int p=0;
			for(int i=0;i<calenbtn.getText().length();i++)
				p+=w[calenbtn.getText().charAt(i)];
			p=(calenbtn.getWidth()/2)+(p/2);
			calenbtn.setIconTextGap(-p);*/
			this.add(calenbtn);
			
			if(menubtn !=null)
				menubtn.setVisible(false);
			menubtn = new Imagen();
			menubtn.setSize(this.getHeight(),this.getHeight());
			menubtn.setImagen(menuarr[0]);
			Listeners.add_animation_click(menubtn, 200 , menuarr);
			menubtn.setLocation(this.getWidth()-menubtn.getWidth(),0);
			this.add(menubtn);
			
			if(searchbtn!=null)
				searchbtn.setVisible(false);
			searchbtn = new Imagen(this.getHeight()-borde, this.getHeight()-borde,
					"/images/search_" + MODE + ".png");
			searchbtn.setLocation(calenbtn.getX()+calenbtn.getWidth()+borde,borde/2);
			this.add(searchbtn);
			
			if(moremoneybtn!=null)
				moremoneybtn.setVisible(false);
			moremoneybtn = new Imagen(this.getHeight()-borde, this.getHeight()-borde,
					"/images/ingresos_"+MODE+".png");
			moremoneybtn.setLocation(searchbtn.getX()+searchbtn.getWidth()+borde,borde/2);
			//this.add(moremoneybtn);
			__Listeners();
		}

		private void __Listeners() {
			newbtn.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {

				}

				@Override
				public void mousePressed(MouseEvent e) {

						if (newz.UP) {
							//optionsz.UP();
							seen.UP();
							seen=newz;
							newz.DOWN();
							newz.name.text.requestFocus();
						} else {
							if (newz.DOWN)
								newz.UP();
							newz.cpred.setVisible(false);
							newz.ipred.setVisible(false);
						}

				}

				@Override
				public void mouseReleased(MouseEvent e) {

				}

				@Override
				public void mouseEntered(MouseEvent e) {

				}

				@Override
				public void mouseExited(MouseEvent e) {

				}

			});
			calenbtn.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {

				}

				@Override
				public void mousePressed(MouseEvent e) {

						if (calenz.UP) {
							seen.UP();
							seen=calenz;
							calenz.DOWN();
							//optionsz.UP();
							
							
						} else {
							if (calenz.DOWN)
								calenz.UP();
						}

				}

				@Override
				public void mouseReleased(MouseEvent e) {

				}

				@Override
				public void mouseEntered(MouseEvent e) {

				}

				@Override
				public void mouseExited(MouseEvent e) {

				}

			});
			menubtn.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {

				}

				@Override
				public void mousePressed(MouseEvent e) {
						if (optionsz.UP) {
							optionsz.DOWN();
					} else {
						if (optionsz.DOWN)
							optionsz.UP();
					}
				}

				@Override
				public void mouseReleased(MouseEvent e) {

				}

				@Override
				public void mouseEntered(MouseEvent e) {
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					
				}

			});
			searchbtn.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {

					if (searchz.RIGHT) {
						searchz.LEFT();
						searchz.sb.text.requestFocus();
						//optionsz.UP();
						
						
					} else {
						if (searchz.LEFT)
							searchz.RIGHT();
					}
					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
			});
			moremoneybtn.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					if (moremoneyz.UP) {
						seen.UP();
						seen=moremoneyz;
						moremoneyz.DOWN();		
					} 
					else {
						if (moremoneyz.DOWN)
							moremoneyz.UP();
					}
					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
			});
						
			Listeners.add_reaction(newbtn,borde,5);
			Listeners.add_reaction(calenbtn,borde,5);
			Listeners.add_reaction(searchbtn,borde,5);
			Listeners.add_reaction(moremoneybtn,borde,5);
		}

	}

	private class CalendarZone extends Zone{
		int year;
		JLabel yearl;
		JPanel yearselection;
		int yswidth;
		int ysheight;
		int mtwidth=50;
		int mtheight=35;
		boolean show=false;
		Imagen marco = new Imagen(mtwidth+4,mtheight+4,"/images/fondo_"+MODE+"_transparente.png");
		public CalendarZone() {
			this.setOpaque(false);
			this.setLayout(null);
			this.setSize(250,220);
			this.setLocation((menubar.getWidth() / 2) - (this.getWidth() / 2)+menubar.getX(), -this.getHeight());
			__init();
			Main.setLayer(this, 1);
			Main.add(this);
		}
	
		private void __init() {
			marco.setOpaque(true);
			year();
			yearselection();
			meses();
			this.add(marco);
			
			__fondo();
		}
		
		private void yearselection() {
			yearselection = new JPanel();
			yearselection.setVisible(false);
			yearselection.setLayout(null);
			yswidth=(yearl.getWidth()*2)-20;
			ysheight=this.getHeight()-yearl.getY()-yearl.getHeight()-5;
			yearselection.setSize(yswidth,ysheight);
			yearselection.setLocation(yearl.getX()-((yearselection.getWidth()-yearl.getWidth())/2),yearl.getY()+yearl.getHeight());
			ShowZone b = new ShowZone(yearselection.getWidth(),yearselection.getHeight());
			b.setBackground(bg);
			b.vsb.widthmax=5;
			b.back.setOpaque(true);
			
			
			int y = new Date().getYear();
			System.out.println(1900+y);
			for(int i=0;(y-i)+1900>1999;i++) {
				JLabel ys = new JLabel(""+(1900+(y-i)));
				ys.setHorizontalAlignment(JLabel.CENTER);
				ys.setFont(new Font("Robot",Font.BOLD,15));
				ys.setSize(b.back.getWidth(),20);
				ys.setForeground(fg);
				ys.setBackground(bg);
				ys.setOpaque(true);
				configurar_mouselistener_configurar(ys, ys,true);
				ys.setLocation(0,i*ys.getHeight());
				//Listeners.add_reaction(ys, 5,5);
				ys.addMouseListener(new MouseListener() {

					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mousePressed(MouseEvent e) {
						String to = ""+(Integer.parseInt(ys.getText())-1900)+data.date.substring(3);
						yearl.setText(ys.getText());
						yearselection.setVisible(false);
						yearselectionhidde();
						if(to.equals(data.date))return;
						data.change_to(to);
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
				});
				b.back.setSize(b.back.getWidth(),ys.getY()+ys.getHeight()+5);
				
				b.back.add(ys);
			}
			yearselection.add(b);
			
			
			this.add(yearselection);
		}
		
		private int k=0;
		private void meses() {
			k=0;
			for(int i=0;i<3;i++) {
				for(int j=0;j<4;j++,k++){
					JLabel mt = new JLabel(data.MONTHS[k]);
					
					mt.setFont(new Font("arial",Font.PLAIN,15));
					mt.setSize(mtwidth,mtheight);
					mt.setLocation(10+(mtwidth*j)+(j*10),yearl.getY()+yearl.getHeight()+(mtheight*i)+(i*10));
					if(k==Integer.parseInt(data.date.substring(3))) {						
						marco.setLocation(mt.getX()-2,mt.getY()-2);
					}
					mt.setForeground(fg);
					mt.setBackground(bg);
					//mt.setOpaque(true);
					mt.setHorizontalAlignment(JLabel.CENTER);
					configurar_mouselistener_configurar(mt, mt,true);
					mt.addMouseListener(new MouseListener() {
						int to=k;
						@Override
						public void mouseClicked(MouseEvent e) {
														
						}

						@Override
						public void mousePressed(MouseEvent e) {
							String year = ((Integer)(new Integer(yearl.getText())-1900)).toString();
							if(data.date.equals(year+to))return;
							data.change_to(year+to);
							Listeners.move_to(marco, mt.getX()-2, mt.getY()-2, 100);
						}

						@Override
						public void mouseReleased(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mouseEntered(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mouseExited(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}
						
					});

					this.add(mt);
				}
			}
		}
		
		private void listeners() {
			KeyListener escape = new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==KeyEvent.VK_ESCAPE)menubar.newbtn.getMouseListeners()[0].mousePressed(null);; 

					
				}
			};	
			this.addKeyListener(escape);
		}
		
		private void year() {
			year =1900 + new Integer(data.date.substring(0,3));
			yearl= new JLabel(""+year);
			yearl.setSize(70,50);
			yearl.setCursor(new Cursor(Cursor.HAND_CURSOR));
			yearl.setLocation((this.getWidth()/2)-(yearl.getWidth()/2),10);
			yearl.setForeground(fg);
			yearl.setBackground(bg);
			//yearl.setOpaque(true);
			
			yearl.setFont(new Font("arial", Font.BOLD, 20));
			yearl.setHorizontalAlignment(JLabel.CENTER);
			yearl.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					if(!yearselection.isVisible())yearselectionshow();
					else yearselectionhidde();
					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
			});
			this.add(yearl);
		}
			
		private void yearselectionshow() {
			(new Thread() {
				public void run() {
					yearselection.setVisible(true);
					show=true;
					long ti=System.currentTimeMillis();
					long time=200;
					long tf=ti+time;
					while(show&&yearselection.getHeight()<ysheight) {
						long ta=System.currentTimeMillis()-ti;
						int t=(int)(ysheight*((float)ta/time));
						yearselection.setSize(yearselection.getWidth(),t);
					}
					yearselection.setSize(yswidth,ysheight);
					
				}
			}).start();
		}
		
		private void yearselectionhidde() {
			(new Thread() {
				public void run() {
					
					show=false;
					
					long ti=System.currentTimeMillis();
					long time=200;
					long tf=ti+time;
					while(!show&&yearselection.getHeight()>0) {
						long ta=System.currentTimeMillis()-ti;
						int t=ysheight-(int)(ysheight*((float)ta/time));
						yearselection.setSize(yearselection.getWidth(),t);
					}
					yearselection.setVisible(false);
				}
			}).start();
		}

	}
	
	private class OptionsZone extends Zone{
		
		Imagen closebt = new Imagen();
		
		public OptionsZone(){
			this.setSize(menubar.menubtn.getWidth(),220);
			this.setLocation(menubar.getX()+menubar.menubtn.getX(), -this.getHeight());
			this.setLayout(null);
			this.setOpaque(false);
			
			closebt();
			__fondo();
			
			Main.setLayer(this, 1);
			Main.add(this);
		}
		
		private void closebt() {
			ImageIcon arr[] = new ImageIcon[4];
			for(int i=0;i<4;i++)
				arr[i]= new ImageIcon(getClass().getResource("/images/Power_"+MODE+"_"+i+".png"));
			
			closebt.setSize(this.getWidth(),this.getWidth());
			closebt.setImagen(new ImageIcon(getClass().getResource("/images/exit_"+MODE+".png")));
			closebt.setLocation(1,this.getHeight()-closebt.getHeight());
			//Listeners.add_reaction(closebt, 5);
			//Listeners.add_animation_mouse(closebt, 100, arr);
			Listeners.add_Exit(closebt);
			this.add(closebt);
		}
		
		public void listeners() {
			if (optionsz.DOWN)
				optionsz.UP();
		}
	}
	
	private class MoreMoneyZone extends Zone{
		ShowZone sz;
	
		public  MoreMoneyZone() {
			Limitev=0;
			this.setOpaque(false);
			this.setLayout(null);
			this.setSize(Main.getWidth()/3 ,Main.getHeight()-20);			
			this.setLocation((Main.getWidth()/2)-(this.getWidth()/2),-this.getHeight());
			__init();
			__fondo();
			Main.setLayer(this, 1);
			Main.add(this);
		}
		
		private void __init() {
			sz = new ShowZone(0,0,this.getWidth(),this.getHeight());
			this.add(sz);
		}
		
	}
	
	public App() {
		this.setIconImage(new ImageIcon(getClass().getResource("/images/Icon_"+MODE+".png")).getImage());
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setSize(960, 600);
		this.setUndecorated(true);
		this.setLayout(null);
		this.setBackground(bg);
		this.setLocationRelativeTo(null);
		setDarkMode();
		new Login().user.text.requestFocus();
	}
	
	private void Main() {

		Menu();
		TopBar();
		MainPanel();
		Root();

		Closebt(menubar);
		Clock(topbar);
		Userlabel(topbar);
		closebt.setVisible(false);
		this.setSize(root.getWidth() + 6, root.getHeight() + 6);
		this.setLocationRelativeTo(null); ;
		this.add(root);
		setDarkMode();
		newz = new NewZone();
		calenz = new CalendarZone();
		optionsz = new OptionsZone();
		searchz = new SearchZone();
		moremoneyz = new MoreMoneyZone();
		generate_cate_zone();

		Listeners.addWindowsMove(this);

		Bordes();
		/*
		 * (new Thread(){ public void run() { while(true) { if(MODE==1)setDarkMode();
		 * else setLightMode(); try { sleep(5000); } catch(Exception e) { } } }
		 * }).start();
		 */
		//data.change_to("1228");
		this.setVisible(true);
		this.addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {

			}

			@Override
			public void componentMoved(ComponentEvent e) {
				int x = App.this.getX() + Main.getX() + newz.getX() + newz.getWidth();
				int y = App.this.getY() + root.getY() + Main.getY() + newz.getY();
				newz.cpred.setLocation(x, y);
				newz.ipred.setLocation(x, y);

			}

			@Override
			public void componentShown(ComponentEvent e) {

			}

			@Override
			public void componentHidden(ComponentEvent e) {

			}

		});

	}
			
	private void Bordes() {
		this.add(borde);
	}

	private void Root() {
		root = new JPanel();
		root.setLayout(null);
		root.setBackground(bg);
		root.setLocation(bordersize, bordersize);
		root.setSize(Menu.getWidth() + Main.getWidth(), Menu.getHeight());
		root.add(Menu);
		root.add(Main);
		root.add(topbar);
	}
	
	private void MainPanel() {
		Main = new JLayeredPane();
		Main.setLayout(null);
		Main.setBackground(bg);
		Main.setOpaque(true);
		Main.setLocation(Menu.getWidth(), topbar.getHeight());
		Main.setSize(this.getWidth() - (bordersize * 2), this.getHeight() - topbar.getHeight() /*- (bordersize * 2)*/);
		showz = new ShowZone();
		showz.back.setSize(10,showz.getHeight());
	}

	private void TopBar() {
		topbar = new JPanel();
		topbar.setLayout(null);
		topbar.setBackground(bg);
		topbar.setLocation(Menu.getWidth(), 0);
		topbar.setSize(this.getWidth(), 40);
		menubar = new MenuBar();
	}
	
	private void Menu() {
		Menu = new JPanel();
		Menu.setLayout(null);
		Menu.setBackground(maincolor);
		Menu.setOpaque(true);
		Menu.setLocation(0, 0);
		Menu.setSize(0, this.getHeight());
	}

	private void setDarkMode() {
		MODE = 0;
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
	
	private void setLightMode() {
		MODE = 2;
		if (borde != null) {
			borde.setVisible(false);
			borde = new Imagen(this.getWidth(), this.getHeight(), "/images/bordes_"+MODE+".png");
			this.add(borde);
		} else
			borde = new Imagen(this.getWidth(), this.getHeight(), "/images/bordes_"+MODE+".png");
		bg = Color.white;
		fg = Color.darkGray;
		fgh = Color.lightGray;
		altern = new Color(bg.getRed() - 10, bg.getGreen() - 10, bg.getBlue() - 10);
		//maincolor = new Color(255, 127, 0);
		maincolor=Color.green;
		change_mode(this);

	}

	private void generate_cate_zone() {
		
		int i = 0;
		for (String j : categorias) {
			ShowZone t = new ShowZone(i == 0 ? 5:(5+((i)*200)), 5, 200, showz.getHeight() - 25);
			configurar_nueva_categoria(t, j);
			int k = 1;
			for (long a : tabla.get(j)) {
				Entry e = data.data.get(a);
				configurar_panel_nuevo(t, e, k++,a);
			}
			i++;
		}

	}
	
	private void configurar_panel_nuevo(ShowZone s, Entry e, int k,long ID) {
		JPanel jp = (JPanel) s.getComponent(3);
		JLabel jl = (JLabel) jp.getComponent(1);
		int he = jp.getHeight();
		s.valor = s.valor.add(e.Precio);
		jl.setText("$" + s.valor);

		total = total.add(e.Precio);

		categorias.add(e.Tipo);
		items.add(e.Nombre);
		actualizar_total(e.Precio, total);
		App.this.userlabel.repaint();
		JPanel p = new JPanel();
		p.setLayout(null);
		p.setSize(s.back.getWidth() - 6, 60);
		p.setLocation(3, k == 1 ? he : (60 * k) - he);
		p.setOpaque(true);
		p.setBackground(k % 2 == 0 ? bg : altern);
		configurar_mouselistener_configurar(p, p,false);
		
		/*
		 * JComponent bordes = new JComponent() {
		 * 
		 * @Override public void paint(Graphics g) { g.setColor(showz.hsb.active);
		 * g.drawRect(0, -1, this.getWidth(), 1); g.drawRect(0, this.getHeight()-1,
		 * this.getWidth(), 2); g.drawRect(-1, 0, 1,this.getHeight());
		 * g.drawRect(this.getWidth()-1, 0, 2, this.getHeight()); } };
		 * bordes.setSize(p.getSize()); bordes.setLocation(0,0);
		 */
		
		JLabel name = new JLabel(e.Nombre);
		JLabel price = new JLabel("$" + e.Precio);
		
		JLabel date = new JLabel(e.Fecha.getDate() + "/" + e.Fecha.getMonth() + "/" + (1900 + e.Fecha.getYear()));
		Imagen edit= new Imagen(14,20,"/images/edit_"+MODE+".png");
		Imagen delete= new Imagen(14,20,"/images/basura_"+MODE+".png");
		Listeners.add_reaction(delete, 3,5);
		Listeners.add_reaction(edit, 3,5);
		edit.setLocation(-edit.getWidth(),5);
		edit.addMouseListener(new MouseListener(){
			TextField editname;
			TextField editprice;
			TextField editcate;
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			private void apply_change() {
				String n,c;
				BigDecimal pr;
				if(editname.text.getText().isEmpty())n=e.Nombre;
				else n=editname.text.getText();
				
				if(editprice.text.getText().isEmpty())pr=e.Precio;
				else pr=editprice.toBigDecimal();
				
				if(editcate.text.getText().isEmpty())c=e.Tipo;
				else c=editcate.text.getText();
				long id =System.currentTimeMillis();
						
				Entry E = new Entry(n,pr,c,id);
				
				if(!E.Tipo.equals(e.Tipo)    ||
				   !E.Nombre.equals(e.Nombre)||
				   !E.Precio.equals(e.Precio)){
						delete_showz_info(ID,he,e,s,jl,p);
						newz.name.text.setText(n);
						newz.price.text.setText(pr.toString());
						newz.categoria.text.setText(c);
						newz.accept.setEnabled(true);
						newz.accept.doClick();
						newz.accept.setEnabled(false);
				}
			}
			
			@Override
			public void mousePressed(MouseEvent evt) {
				JDialog editw = new JDialog();
				Listeners.addWindowsMove(editw);
				editw.setUndecorated(true);
				editw.setSize(260,180);
				editw.setLocationRelativeTo(null);
				//editw.setBackground(bg);
				editw.setAlwaysOnTop(true);
				
				JButton confirm = new JButton();
				confirm.setSize(30,30);
				confirm.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/confirm_"+MODE+".png")).getImage().
						getScaledInstance(confirm.getWidth(), confirm.getHeight(), Image.SCALE_SMOOTH)));
				confirm.setContentAreaFilled(false);
				confirm.setBorder(null);
				
				JButton cancel = new JButton();
				cancel.setSize(30,30);
				cancel.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/exit_"+MODE+".png")).getImage().
						getScaledInstance(cancel.getWidth(), cancel.getHeight(), Image.SCALE_SMOOTH)));
				cancel.setContentAreaFilled(false);
				cancel.setBorder(null);
				
				
				editname = new TextField(250,50,e.Nombre,"/images/item_"+MODE+".png");
				editname.setLocation(5,0);
				editname.setOpaque(false);
				editname.text.setForeground(fg);
				editname.hint.setForeground(fg);
				editname.text.setBackground(bg);
				editname.hint.setBackground(bg);
				editname.text.setCaretColor(fg);
				editname.KeyButton(confirm, '\n');
				editname.KeyButton(cancel, (char)27);
				
				editprice = new TextField(250,50,e.Precio.toString(),"/images/dolar_"+MODE+".png");
				editprice.setLocation(5,45);
				editprice.setNumericMode();
				editprice.text.setForeground(fg);
				editprice.hint.setForeground(fg);
				editprice.text.setBackground(bg);
				editprice.hint.setBackground(bg);
				editprice.text.setCaretColor(fg);
				editprice.KeyButton(confirm, '\n');
				editprice.KeyButton(cancel, (char)27);
				
				editcate = new TextField(250,50,e.Tipo,"/images/tag_"+MODE+".png");
				editcate.setLocation(5,90);
				editcate.text.setForeground(fg);
				editcate.hint.setForeground(fg);
				editcate.text.setBackground(bg);
				editcate.hint.setBackground(bg);
				editcate.text.setCaretColor(fg);
				editcate.KeyButton(confirm, '\n');
				editcate.KeyButton(cancel, (char)27);
				
				editw.add(editname);
				editw.add(editprice);
				editw.add(editcate);
				
				Imagen fondo = new Imagen(editw.getWidth(),editw.getHeight(),"/images/fondo_"+MODE+"_transparente.png");
				
				
				confirm.setLocation((editw.getWidth()/2)-(confirm.getWidth()/2)-40,editw.getHeight()-confirm.getHeight()-10);
				cancel.setLocation((editw.getWidth()/2)-(cancel.getWidth()/2)+40,editw.getHeight()-cancel.getHeight()-10);
				confirm.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						apply_change();
						editw.setVisible(false);
						editw.removeAll();
					}
				});
				
				cancel.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						editw.setVisible(false);
						editw.removeAll();
					}
				});
				
				editw.add(confirm);
				editw.add(cancel);
				editw.add(fondo);
				editw.setVisible(true);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		configurar_mouselistener_configurar(edit, p,false);
		delete.setLocation(-delete.getWidth(),35);	
		delete.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent ee) {
				App.this.setEnabled(false);
				JDialog window = new JDialog();
				window.setUndecorated(true);
				try {
					window.setOpacity(0.0f);
				}
				catch(Exception e) {
					
				}
				JPanel w = new JPanel();
				window.setSize(300,150);
				window.setAlwaysOnTop(true);
				w.setBackground(bg);
				w.setLayout(null);
				w.setSize(window.getWidth()-4,window.getHeight()-4);
				w.setLocation(2,2);
				
				JButton acpbtn = new JButton("Ok");									
				Font f=userlabel.getFont();JButton canbtn = new JButton("Cancel");
				
				ActionListener act=new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ee) {
						if(ee.getSource()==acpbtn)delete_showz_info(ID,he,e,s,jl,p);
						window.setVisible(false);
					    window.removeAll();
					    App.this.setEnabled(true);
						App.this.requestFocus();
					
					}
				};
				acpbtn.setSize(125,40);
				acpbtn.setBackground(maincolor);
				acpbtn.setForeground(fg);
				acpbtn.setFont(new Font(f.getFontName(),f.getStyle(),15));
				acpbtn.setLocation((w.getWidth()/2)-acpbtn.getWidth()-10,w.getHeight()-acpbtn.getHeight()-10);
				acpbtn.addActionListener(act);
				acpbtn.setBorder(null);
				canbtn.setBackground(maincolor);
				canbtn.setSize(125,40);
				canbtn.setForeground(fg);
				canbtn.setFont(acpbtn.getFont());
				canbtn.setLocation((w.getWidth()/2)+10,w.getHeight()-canbtn.getHeight()-10);
				canbtn.addActionListener(act);
				canbtn.setBorder(null);
				w.add(acpbtn);
				w.add(canbtn);
				JTextArea p2= new JTextArea();
				
				p2.setFont(new Font(f.getFontName(),f.getStyle(),13));
				p2.setSize(w.getWidth(),acpbtn.getY()-5);
				p2.setBackground(bg);
				p2.setForeground(fg);
				p2.setLocation(15,5);
				p2.setText("Esta seguro de que desea eliminar\nNombre:\t"+e.Nombre+"\nPrecio:\t$"+e.Precio+"\nTipo:\t"+e.Tipo+"\nEsta accion no puede ser deshecha");
				p2.setEditable(false);
				w.add(p2);
				window.setLocationRelativeTo(null);
				window.setVisible(true);
				window.add(w);
				window.add(new Imagen(window.getWidth(),window.getHeight(),"/images/bordes_"+MODE+".png"));
				w.requestFocus();
				w.addKeyListener(new KeyListener() {

					@Override
					public void keyTyped(KeyEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void keyPressed(KeyEvent e) {
						//System.out.println(e.getKeyCode());
						switch(e.getKeyCode()) {
						case KeyEvent.VK_ENTER: acpbtn.doClick();break;
						case KeyEvent.VK_ESCAPE: canbtn.doClick();break;
						}
						
						
					}

					@Override
					public void keyReleased(KeyEvent e) {
						// TODO Auto-generated method stub
						
					}
					
				});
				(new Thread() {
					public void run() {
						while(window.getOpacity()<1.0f) {
							try {
								window.setOpacity(window.getOpacity()+0.1f);
								sleep(20);
							}
							catch(Exception e) {
								window.setOpacity(1.0f);
							}
						}
					}
				}).start();
			}
			

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});		
		configurar_mouselistener_configurar(delete, p,false);
		configurar(name, 0, 0, s, p);
		configurar(price, 0, 20, s, p);
		configurar(date, 0, 40, s, p);
		p.add(name);
		p.add(price);
		p.add(date);		
		p.add(edit);
		p.add(delete);
		s.back.add(p);
		s.back.setSize(s.back.getWidth(), p.getY() + p.getHeight() + 3);
		userlabel.setText("$"+total);
		s.repaint();
	}

	private void delete_showz_info(long ID,int he,Entry e,ShowZone s,JLabel jl,JPanel p) {
		total = total.subtract(e.Precio);
		userlabel.setText("$"+total);
		s.valor = s.valor.subtract(e.Precio);
		tabla.get(e.Tipo).remove(ID);
		jl.setText("$"+s.valor);
		p.setVisible(false);
		if(tabla.get(e.Tipo).isEmpty()) {
			s.setName("null");
			JPanel ss=(JPanel)s.getParent();
			s.setVisible(false);
			int k=0;
			for(Component i:ss.getComponents()) {
				if(i.isVisible()) {
					i.setLocation(k > 0 ? (k * 200) + 5 - k : 5, 5);
					k++;
				}
			}
			showz.remove(s);
			ss.setSize(ss.getWidth()-s.getWidth(),ss.getHeight());
		}
		else {
			int k=1;
			for(Component i:s.back.getComponents()) {
				if(i.isVisible()) {
					i.setLocation(3, k == 1 ? he : (60 * k) - he);
					i.setBackground(k % 2 == 0 ? bg : altern);
					i.removeMouseListener(i.getMouseListeners()[0]);
					configurar_mouselistener_configurar((JComponent) i, (JComponent) i,false);
					k++;
				}
			}
			s.back.remove(p);
			s.back.setSize(s.back.getWidth(),s.back.getHeight()-p.getHeight());
		}
		data.data.remove(ID);
		try{
			data.Guardar_Datos();
		}
		catch(Exception ee) {
			
		}
	}
	
	private void configurar_nueva_categoria(ShowZone t, String j) {
		t.setName(j);
		JPanel n = new JPanel();
		JLabel nc = new JLabel(j);
		JLabel nt = new JLabel("$" + 0);

		n.add(nc);
		n.add(nt);
		n.setLayout(null);
		n.setSize(t.back.getWidth() - 2, 30);
		n.setOpaque(true);
		n.setBackground(bg);
		n.setLocation(t.back.getX() + 1, t.back.getY() + 1);
		n.setForeground(fg);
		n.setFont(new Font("arial", Font.BOLD, 15));

		nc.setSize(n.getWidth() / 2, 30);
		nc.setOpaque(true);
		nc.setBackground(bg);
		nc.setLocation(0, 0);
		nc.setHorizontalAlignment(nc.CENTER);
		nc.setForeground(fg);
		nc.setFont(new Font("arial", Font.BOLD, 15));

		nt.setSize(n.getWidth() / 2, 30);
		nt.setOpaque(true);
		nt.setBackground(bg);
		nt.setLocation(nc.getWidth(), 0);
		nt.setHorizontalAlignment(nc.CENTER);
		nt.setForeground(fg);
		nt.setFont(new Font("arial", Font.BOLD, 15));

		t.setLayer(n, 1);
		t.add(n);
		showz.back.add(t);
		showz.back.setSize(t.getX() + t.getWidth() + 5, showz.back.getHeight());
	}

	private void configurar_mouselistener_configurar(JComponent A, JComponent B,boolean temp) {
		A.addMouseListener(new MouseListener() {
			Color out = B.getBackground();
			Color over = new Color(out.getRed() - 20, out.getGreen() - 20, out.getBlue() - 20);
			boolean mover = false;
			boolean mout = false;
			Component edit; 
			Component delete;

			private int getx(Container c) {
				if(c==null)return 0;
				return c.getX()+getx(c.getParent());
			}
			
			private int gety(Container c) {
				if(c==null)return 0;
				return c.getY()+gety(c.getParent());
			}

			
			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				
					
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
				mover = true;
				mout = false;
				
				try {
					edit = B.getComponent(3);
					delete = B.getComponent(4);
				}
				catch(Exception ee) {
					edit=null;
					delete=null;
				}
				(new Thread() {

					public void run() {
						if(temp)B.setOpaque(true);
						Color now = B.getBackground();
						int r = now.getRed(), g = now.getGreen(), b = now.getBlue();
						while (mover) {
							if(edit!=null) {
								if(edit.getX()<=0) {
									edit.setLocation(edit.getX()+1,edit.getY());
									delete.setLocation(edit.getX(),delete.getY());
								}
							}
							if (r > over.getRed())
								r--;
							if (g > over.getGreen())
								g--;
							if (b > over.getBlue())
								b--;
							B.setBackground(new Color(r, g, b));
							if (r == over.getRed() && g == over.getGreen() && b == over.getBlue())
								return;
							try {
								sleep(10);
							} catch (Exception e) {

							}
						}
					}

				}).start();

			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				int x=getx((Container)B);
				int y=gety((Container)B);
				int mx=(int)MouseInfo.getPointerInfo().getLocation().getX();
				int my=(int)MouseInfo.getPointerInfo().getLocation().getY();
				//System.out.println(x+"/"+(x+A.getWidth()));
				//System.out.println(y+"/"+(y+A.getHeight()));
				//System.out.println(e.getXOnScreen()+"/"+e.getYOnScreen());
				
				if(mx>x&&mx<x+B.getWidth()&&
				   my>y&&my<y+B.getHeight()) return;
					
				mover = false;
				mout = true;
				try {
					edit = B.getComponent(3);
					delete = B.getComponent(4);
				}
				catch(Exception ee) {
					edit=null;
					delete=null;
				}
				(new Thread() {

					public void run() {
						if(temp)B.setOpaque(false);
						Color now = B.getBackground();
						int r = now.getRed(), g = now.getGreen(), b = now.getBlue();
						while (mout) {
							if(edit!=null) {
								if(edit.getX()>=-edit.getWidth()) {
									edit.setLocation(edit.getX()-1,edit.getY());
									delete.setLocation(edit.getX(),delete.getY());
								}
							}
							if (r < out.getRed())
								r++;
							if (g < out.getGreen())
								g++;
							if (b < out.getBlue())
								b++;
							B.setBackground(new Color(r, g, b));
							if (r == out.getRed() && g == out.getGreen() && b == out.getBlue())
								return;
							try {
								sleep(10);
							} catch (Exception e) {

							}
						}
					}

				}).start();

			}

		});
	}
	
	private void configurar(JLabel name, int x, int y, ShowZone t, JPanel p) {

		name.setOpaque(false);
		name.setBackground(bg);
		name.setForeground(fg);
		name.setFont(name.getFont());
		name.setLocation(x, y);
		name.setSize(t.back.getWidth(), 20);
		name.setHorizontalAlignment(name.CENTER);

	}

	private void Clock(JComponent j) {
		clock = new JLabel();
		clock.setSize(280, topbar.getHeight());
		Font f = closebt.getFont();
		clock.setFont(new Font(f.getFontName(), f.getStyle(), 25));
		clock.setHorizontalAlignment(clock.CENTER);
		clock.setVerticalAlignment(clock.CENTER);
		clock.setForeground(fg);
		clock.setLocation(Main.getWidth() - clock.getWidth(), 0);
		(new Thread() {
			public void run() {
				while (true) {
					Date d = new Date();
					boolean am=d.getHours()<=11;
					clock.setText(d.toString().substring(0, 8) + (d.getHours()==0?12:d.getHours() < 10 ? 0 : "") + (d.getHours()==0?":" : d.getHours()!=12? d.getHours() %12 + ":":"12:")
							+ (d.getMinutes() < 10 ? 0 : "") + d.getMinutes() + ":" + (d.getSeconds() < 10 ? 0 : "")
							+ d.getSeconds() + " " + (d.getHours() > 11 ? "PM" : "AM"));
					try {
						sleep(100);
					} catch (Exception e) {

					}
				}
			}
		}).start();
		j.add(clock);
	}

	private void Userlabel(JComponent j) {
		userlabel = new JLabel("Bienvenido");
		userlabel.setForeground(altern);
		Font f = clock.getFont();
		userlabel.setFont(f);
		userlabel.setSize(topbar.getWidth() / 3, topbar.getHeight());
		userlabel.setHorizontalAlignment(SwingConstants.LEFT);
		userlabel.setVerticalAlignment(SwingConstants.CENTER);
		userlabel.setLocation(10, 0);
		j.add(userlabel);
	}

	private void Closebt(JComponent j) {
		closebt = new JLabel();
		closebt.setSize(40, j.getHeight());
		closebt.setHorizontalAlignment(SwingConstants.CENTER);
		closebt.setVerticalAlignment(SwingConstants.CENTER);
		closebt.setLocation((j.getWidth()) - (closebt.getWidth()), 0);
		closebt.setFont(new Font("arial", Font.BOLD, 15));
		closebt.setForeground(fg);
		closebt.setText("X");
		Listeners.addRedBG(closebt);
		j.add(closebt);
	}

	private void actualizar_total(BigDecimal A, BigDecimal B) {
		(new Thread() {
			public void run() {
				long tiempo = 1000;
				long ti = System.currentTimeMillis();
				long tf = ti + tiempo;
				long ta;
				BigDecimal ini = B.subtract(A);
				BigDecimal d = new BigDecimal(100.00);
				while (System.currentTimeMillis() <= tf) {

					ta = System.currentTimeMillis() - ti;
					BigDecimal perc = new BigDecimal((int) (((float) ta / tiempo) * 100.00));
					BigDecimal put = ini.add(A.multiply(perc.divide(d)));
					userlabel.setText("$" + put);

				}
				userlabel.setText("$" + total);
			}
		}).start();
	}

	private void change_mode(Container j) {
	
		if (j.getClass() == NewZone.class) {
			NewZone nz = (NewZone) j;
			nz.name.text.setForeground(fg);
			nz.name.text.setBackground(bg);
			nz.name.text.setCaretColor(fg);
			int w = nz.name.icon.getWidth();
			int h = nz.name.icon.getHeight();
			URL dir = nz.getClass().getResource("/images/item_" + MODE + ".png");
			nz.name.icon.setImagen(dir, w, h);
	
			nz.price.text.setForeground(fg);
			nz.price.text.setBackground(bg);
			nz.price.text.setCaretColor(fg);
			w = nz.price.icon.getWidth();
			h = nz.price.icon.getHeight();
			dir = nz.getClass().getResource("/images/dolar_" + MODE + ".png");
			nz.price.icon.setImagen(dir, w, h);

			nz.categoria.text.setForeground(fg);
			nz.categoria.text.setBackground(bg);
			nz.categoria.text.setCaretColor(fg);
			w = nz.categoria.icon.getWidth();
			h = nz.name.icon.getHeight();
			dir = nz.getClass().getResource("/images/tag_" + MODE + ".png");
			nz.categoria.icon.setImagen(dir, w, h);

			w = nz.fondo.getWidth();
			h = nz.fondo.getHeight();
			dir = nz.getClass().getResource("/images/fondo_" + MODE + "_transparente.png");
			nz.fondo.setImagen(dir, w, h);

			if (nz.ipred.isVisible()) {
				nz.ipred.removeAll();
				nz.ipred.setVisible(false);
				nz.ipred = new JWindow();
				nz.crear_prediccion(nz.ipred, nz.name);
			} else if (nz.cpred.isVisible()) {
				nz.cpred.removeAll();
				nz.cpred.setVisible(false);
				nz.cpred = new JWindow();
				nz.crear_prediccion(nz.cpred, nz.categoria);
			}
		}
		if (j.getClass() == TextField.class) {
			TextField t = (TextField) j;
			t.text.setBackground(altern);
			t.text.setForeground(fg);
			t.hint.setBackground(altern);
			t.hint.setForeground(fgh);
		}

		else if (j.getClass() == ShowZone.class) {
			ShowZone sz = (ShowZone) j;
			sz.border.color("all", maincolor);
			sz.vsb.active = maincolor;
			sz.hsb.active = maincolor;
			for (Component i : sz.getComponents()) {
				if (i.getClass() == JPanel.class) {
					for (Component k : ((Container) i).getComponents()) {
						k.setBackground(bg);
						k.setForeground(fg);
					}
				}
			}
			sz.back.setBackground(bg);
			int k = 0;
			for (Component i : sz.back.getComponents()) {
				Container c = (Container) i;
				if (i.getClass() == ShowZone.class)
					change_mode(c);

				else if (i.getClass() == JPanel.class) {
					i.setBackground(k % 2 != 0 ? bg : altern);
					i.setForeground(fg);
					Component[] arr = c.getComponents();
					for (int l = 0; l < c.getComponentCount(); l++) {
						if (arr[l].getClass() == JLabel.class) {
							((JLabel) arr[l]).setBackground(bg);
							((JLabel) arr[l]).setForeground(fg);
							i.removeMouseListener(i.getMouseListeners()[0]);
							configurar_mouselistener_configurar((JComponent) i, (JComponent) i,false);
						}
					}
				}
				k++;
			}

		}

		else if (j.getClass() == App.class) {
			for (Component i : j.getComponents()) {
				change_mode((Container) i);
			}
		}

		else if (j.getClass() == JButton.class) {
			j.setBackground(maincolor);
			j.setForeground(Color.white);
		}

		else if (j.getClass() == MenuBar.class) {
			j.setBackground(altern);
			menubar.ini_buttons();
			for (Component i : j.getComponents()) {
				change_mode((Container) i);
			}
		}

		else {
			j.setBackground(bg);
			j.setForeground(fg);
			for (Component i : j.getComponents()) {
				change_mode((Container) i);
			}
		}
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
