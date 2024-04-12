package Ventana;

import java.awt.Color;
import java.awt.Container;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import javax.swing.ImageIcon;
import java.awt.Font;

public class Listeners {
	static int vx,vy,mx,my,px,py;

	public static void addWindowsMove(Container j) {
		
		MouseListener MOVE=new MouseListener() {
			boolean pressed=true;
			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				this.pressed=true;
				(new Thread() {
					public void run() {
						 vx=j.getX();
						 vy=j.getY();
						 mx=e.getX();
						 my=e.getY();
						while(pressed) {
							px=(int) MouseInfo.getPointerInfo().getLocation().getX();
							py=(int) MouseInfo.getPointerInfo().getLocation().getY();
							j.setLocation((int)(px-mx) ,(int) (py-my));
						}
					}
				}).start();
				
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				this.pressed=false;
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		
		};
		j.addMouseListener(MOVE);
	}
	
	public static void addRedBG(JComponent j) {
		MouseListener REDBG=new MouseListener() {
			boolean in=false;
			@Override
			public void mouseClicked(MouseEvent ev) {

				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				in=true;
				(new Thread() {
					
					public void run() {
						Color bg= j.getParent().getBackground();
						int rf=230;
						int gf=0;
						int bf=0;
						j.setBackground(bg);
						j.setOpaque(true);
						for(int i=255;i>=0;i--) {
							if(!in)return;
							//System.out.println(bg.toString());
							j.setBackground(new Color(Math.min(rf,bg.getRed()+(bg.getRed()<rf?5:bg.getRed()==rf?0:-5)),
													  Math.max(0,bg.getGreen()+(bg.getGreen()<gf?5:bg.getGreen()==gf?0:-5)),
													  Math.max(0,bg.getBlue()+(bg.getBlue()<bf?5:bg.getBlue()==bf?0:-5))));
							bg = j.getBackground();
							try {
								this.sleep(5);
								j.updateUI();
							}catch(Exception e) {
								
							}
						}
					}
					
				}).start();
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				in=false;
				j.setOpaque(false);
				j.updateUI();
				//System.out.println("se supone se ponga transparente");
			}
			
		};
		j.addMouseListener(REDBG);
	}

	public static void add_reaction(JComponent j,int borde,long delay) {
		j.addMouseListener(new MouseListener() {
			boolean over=false;
			boolean clicked=false;
			byte crecimiento=0;
			int maxwidth=j.getWidth()+borde;
			int maxheight=j.getHeight()+borde;
			int minwidth=j.getWidth();
			int minheight=j.getHeight();
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(clicked)return;
				clicked=true;
				int minwidth=j.getWidth()-borde;
				int minheight=j.getHeight()-borde;
				int maxwidth=j.getWidth();
				int maxheight=j.getHeight();
				(new Thread(){
					public void run() {
						while(over&&(j.getWidth()>minwidth||j.getHeight()>minheight)) {
							j.setSize(j.getWidth()-2,j.getHeight()-2);
							j.setLocation(j.getX()+1,j.getY()+1);
							((Imagen)j).ajustar();
							
							
							try {
								sleep(delay/2);
							}
							catch(Exception e) {
								
							}
						}
						while(over&&(j.getWidth()<maxwidth||j.getHeight()<maxheight)) {
							j.setSize(j.getWidth()+2,j.getHeight()+2);
							j.setLocation(j.getX()-1,j.getY()-1);
							((Imagen)j).ajustar();
	
							try {
								sleep(delay/2);
							}
							catch(Exception e) {
								
							}
					}
						clicked=false;
				}
			}).start();

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				over=true;
				(new Thread(){
					public void run() {
						while(over&&(j.getWidth()<maxwidth||j.getHeight()<maxheight)) {
							j.setSize(j.getWidth()+2,j.getHeight()+2);
							j.setLocation(j.getX()-1,j.getY()-1);
							((Imagen)j).ajustar();
	
							try {
								sleep(delay);
							}
							catch(Exception e) {
								
							}
						}
					}
				}).start();
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				over=false;
				(new Thread(){
					public void run() {
						while(!over&&(j.getWidth()>minwidth||j.getHeight()>minheight)) {
							j.setSize(j.getWidth()-2,j.getHeight()-2);
							j.setLocation(j.getX()+1,j.getY()+1);
							((Imagen)j).ajustar();
							
							
							try {
								sleep(delay);
							}
							catch(Exception e) {
								
							}
						}
						if(crecimiento<0)crecimiento=0;
					}
				}).start();
				
			}
			
		});
		}
	
	public static void add_reaction(JComponent j,int borde,int f) {
		j.addMouseListener(new MouseListener() {
			Font font=j.getFont();
			boolean over=false;
			boolean clicked=false;
			byte crecimiento=0;
			int maxwidth=j.getWidth()+borde;
			int maxheight=j.getHeight()+borde;
			int minwidth=j.getWidth();
			int minheight=j.getHeight();
			int fontmax=j.getFont().getSize()+f;
			int fontmin=j.getFont().getSize();
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(clicked)return;
				clicked=true;
				int minwidth=j.getWidth()-borde;
				int minheight=j.getHeight()-borde;
				int maxwidth=j.getWidth();
				int maxheight=j.getHeight();
				(new Thread(){
					public void run() {
						while(over&&(j.getWidth()>minwidth||j.getHeight()>minheight)) {
							j.setSize(j.getWidth()-2,j.getHeight()-2);
							j.setLocation(j.getX()+1,j.getY()+1);
							((Imagen)j).ajustar();
							
							
							try {
								sleep(5);
							}
							catch(Exception e) {
								
							}
						}
						while(over&&(j.getWidth()<maxwidth||j.getHeight()<maxheight)) {
							j.setSize(j.getWidth()+2,j.getHeight()+2);
							j.setLocation(j.getX()-1,j.getY()-1);
							
							((Imagen)j).ajustar();
	
							try {
								sleep(5);
							}
							catch(Exception e) {
								
							}
					}
						clicked=false;
				}
			}).start();

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				over=true;
				(new Thread(){
					public void run() {
						while(over&&(j.getWidth()<maxwidth||j.getHeight()<maxheight)) {
							j.setSize(j.getWidth()+2,j.getHeight()+2);
							j.setLocation(j.getX()-1,j.getY()-1);
							if(j.getFont().getSize()<fontmax)
								j.setFont(new Font(font.getFontName(),font.getStyle(),j.getFont().getSize()+1));
							((Imagen)j).ajustar();
	
							try {
								sleep(10);
							}
							catch(Exception e) {
								
							}
						}
					}
				}).start();
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				over=false;
				(new Thread(){
					public void run() {
						while(!over&&(j.getWidth()>minwidth||j.getHeight()>minheight)) {
							j.setSize(j.getWidth()-2,j.getHeight()-2);
							j.setLocation(j.getX()+1,j.getY()+1);
							if(j.getFont().getSize()>fontmin)
								j.setFont(new Font(font.getFontName(),font.getStyle(),j.getFont().getSize()-1));
							((Imagen)j).ajustar();
							
							
							try {
								sleep(10);
							}
							catch(Exception e) {
								
							}
						}
						if(crecimiento<0)crecimiento=0;
					}
				}).start();
				
			}
			
		});
		}

	public static void move_to(JComponent j,int x,int y,long time) {
		(new Thread() {
			public void run() {
				int xi=j.getX();
				int yi=j.getY();
				int xt;
				int yt;
				long ti=System.currentTimeMillis();
				long tf=ti+time;
				while(System.currentTimeMillis()<tf) {
					long ta=System.currentTimeMillis()-ti;
					int xp=xi+(int)((x-xi)*((float)ta/time));
					int yp=yi+(int)((y-yi)*((float)ta/time));
	
					j.setLocation(xp,yp);
				}
				j.setLocation(x,y);
			}
		}).start();
	}
	
	public static void add_Exit(JComponent j) {
		j.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
				
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
	}
	
	public static void add_animation_mouse(JComponent j,long time,ImageIcon arr[]){
		j.addMouseListener(new MouseListener() {
			boolean forward=false;
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
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
				(new Thread() {
					public void run() {
						forward=true;
						long ti=System.currentTimeMillis();
						long tf=ti+time;
						while(System.currentTimeMillis()<tf&&forward) {
							long ta=System.currentTimeMillis()-ti;
							int p = (int)(((float)ta/time)*(arr.length-1));
							((Imagen)j).setImagen(arr[p]);
							try {
								sleep(1);
							}
							catch(Exception ee) {
								
							}
						}
						((Imagen)j).setImagen(arr[arr.length-1]);
					}
				}).start();
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				(new Thread() {
					public void run() {
						forward=false;
						long ti=System.currentTimeMillis();
						long tf=ti+time;
						while(System.currentTimeMillis()<tf&&!forward) {
							long ta=System.currentTimeMillis()-ti;
							int p = (arr.length-1)-(int)(((float)ta/time)*(arr.length-1));
							((Imagen)j).setImagen(arr[p]);
							
							try {
								sleep(1);
							}
							catch(Exception ee) {
								
							}
						}
						((Imagen)j).setImagen(arr[0]);
					}
				}).start();
				
			}
			
		});
	}

	public static void add_animation_click(JComponent j,long time,ImageIcon arr[]) {
		j.addMouseListener(new MouseListener(){
			boolean forward=false;
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(!forward)
				(new Thread() {
					public void run() {
						forward=true;
						long ti=System.currentTimeMillis();
						long tf=ti+time;
						while(System.currentTimeMillis()<tf&&forward) {
							long ta=System.currentTimeMillis()-ti;
							int p = (int)(((float)ta/time)*(arr.length-1));
							if(j.getClass()==Imagen.class)((Imagen)j).setImagen(arr[p]);
							try {
								sleep(1);
							}
							catch(Exception ee) {
								
							}
						}
						((Imagen)j).setImagen(arr[arr.length-1]);
					}
				}).start();
				
				else {
					(new Thread() {
						public void run() {
							forward=false;
							long ti=System.currentTimeMillis();
							long tf=ti+time;
							while(System.currentTimeMillis()<tf&&!forward) {
								long ta=System.currentTimeMillis()-ti;
								int p = (arr.length-1)-(int)(((float)ta/time)*(arr.length-1));
								if(j.getClass()==Imagen.class)((Imagen)j).setImagen(arr[p]);
								try {
									sleep(1);
								}
								catch(Exception ee) {
									
								}
							}
							((Imagen)j).setImagen(arr[0]);
						}
					}).start();
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
	}
	
}
