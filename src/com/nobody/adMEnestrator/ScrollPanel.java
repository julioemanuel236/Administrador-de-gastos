package com.nobody.adMEnestrator;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.*;

/**
 * 
 *Esta clase consta de dos partes<p>
 *1- La clase en si misma que consiste en un contenedor para nuestro panel donde situaremos todo<p>
 *<p>
 *2- Un contenedor <code>back</code> sobre el cual se trabajara<p> 
 * <p>
 *Si se realizan un setSize debe tenerse en cuenta:<p>
 *1-setSize a esta clase solo cambiara el tamaño de esta<p>
 *<p>
 *2-para ajustar el tamaño de las barras se debe hacer <code>setSize</code> a <code>this.back</code> que es el panel principal<p>
 *<p>
 *3-para que los bordes se actualizen se debe hacer <code>this.border.setSize(this.getSize())</code> <p>
 *<p>
 *<p>
 *Se pueden cambiar los colores de los bordes de manera independiente mediante el metodo <code>this.border.color</code><p>
 *<p>
 *Se pueden ocultar los bordes de manera independiente mediante el metodo <code>this.border.show</code><p>
 *<p>
 * @author NOBODY
 *
 */

public class ScrollPanel extends JLayeredPane{
	private boolean bigerv=false,bigerh=false;
	private boolean smallerv=false,smallerh=false;
	JPanel back = new JPanel();
	VSB vsb;
	HSB hsb;
	Border border;
	
	protected class VSB extends JComponent{
		/**
		 *La clase VSB funciona de manera automatica
		 *se puede cambiar si color activo mediante el atributo active
		 *de igual manera el inactive mediante el atributo inactive
		 *ambos pertenecientes a la clase <code>java.awt.Color</code>
		 *		 *
		 */
		private void little() {
			(new Thread() {
				public void run() {
					bigerv=false;
					smallerv=true;
					while(vsb.width>4) {
						if(!smallerv)return;
						  vsb.width--;
						  //if(vsb.width%2==0)vsb.xpos++;
						  vsb.repaint();
						try {
							sleep(10);
						}
						catch(Exception e) {
							
						}
					}
					vsb.big=false;
				}
			}).start();			
		}
		
		private void crecer() {
			(new Thread() {
				public void run() {
					bigerv=true;
					smallerv=false;
					while(vsb.width<widthmax) {
						  if(!bigerv)return;
						  vsb.width++;
						  //if(vsb.width%2==0)vsb.xpos--;
						  vsb.repaint();
						try {
							sleep(10);
						}
						catch(Exception e) {
							
						}
					}
					vsb.big=true;
				}
			}).start();			
		}
			
		boolean big=false,mp=false,mi=false;
		int x=0;
		int xpos;
		int y=0;
		int my;
		int ty;
		int width   =4;
		int widthmax =10;
		int height  =1;
		int heightnow = 1;
		Color active=Color.green,inactive=Color.darkGray;
		
		public VSB() {
			__init();
		}
		
		private void __init() {
			xpos=ScrollPanel.this.getWidth()-width-7;
			this.setBackground(Color.DARK_GRAY);
			//this.setOpaque(true);
			this.setSize(widthmax,height);
			this.addMouseMotionListener(new MouseMotionListener() {
				@Override
				public void mouseDragged(MouseEvent e) {
				//	System.out.println(y+"/"+ e.getY());
					int toy = ty+e.getYOnScreen()-my;
					
					if(toy<0)toy=0;
					else if(toy>ScrollPanel.this.getHeight()-VSB.this.getHeight())
						toy=ScrollPanel.this.getHeight()-VSB.this.getHeight();
					
					VSB.this.setLocation(VSB.this.getX(),toy);
					VSB v = VSB.this;
					JPanel b = ScrollPanel.this.back;
					b.setLocation(b.getX(),-(int)(b.getHeight()*(double)v.getY()/ScrollPanel.this.getHeight()));
					//System.out.println(b.getHeight()*(double)v.getY()/ScrollPanel.this.getHeight());
					
				}

				@Override
				public void mouseMoved(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
			});
			this.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub(int)
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					my=e.getYOnScreen();
					ty=VSB.this.getY();
					mp=true;
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					mp=false;
					if(!mi)
					little();
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					mi=true;
					crecer();
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					mi=false;
					if(!mp)
					little();
					
				}
				
			});
			ScrollPanel.this.addMouseWheelListener(new MouseWheelListener() {

				int MOVE=50;
				
				private void Move(int d) {
				//	System.out.println("ruedita VSB");
					JPanel b = ScrollPanel.this.back;
					VSB v = VSB.this;
					(new Thread() {
						public void run() {
						if(ScrollPanel.VSB.this.isVisible())
							for(int i=0;i<10;i++) {	
								int t=(int)(((double)MOVE/b.getHeight())*100.00);
							//	System.out.println("porcentaje: "+t);
								int toy = v.getY()+(d>0?t:-t);
								if(toy<0)toy=0;
								else if(toy>ScrollPanel.this.getHeight()-VSB.this.getHeight())toy=ScrollPanel.this.getHeight()-VSB.this.getHeight();
								v.setLocation(v.getX(),toy);
								b.setLocation(b.getX(),-(int)(b.getHeight()*(double)v.getY()/ScrollPanel.this.getHeight()));
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
				public void mouseWheelMoved(MouseWheelEvent e) {
				//	System.out.println(e.getWheelRotation());
					Move(e.getWheelRotation());
				}
			});
			if(ScrollPanel.this.getHeight()<=ScrollPanel.this.back.getHeight())this.setVisible(false);
			toRight();
		}
		
		protected void toLeft() {
			xpos=5;
		}
		
		protected void toRight() {
			xpos=ScrollPanel.this.getWidth()-widthmax-(width/2);
		}
		
		@Override
		public void paint(Graphics g) {
				toRight();
				this.setLocation(xpos,this.getY());
				//System.out.println(this.getLocation().toString());
				height = (int) (ScrollPanel.this.getHeight()*(double)ScrollPanel.this.getHeight()/back.getHeight());
				//	height = Math.max(height, 20);
				this.setSize(width,heightnow<height?heightnow+=5:heightnow>height?--heightnow:height);
				//this.setSize(width,height);
				//System.out.println(ScrollPanel.this.getHeight()*(double)ScrollPanel.this.getHeight()/back.getHeight());
				g.setColor(width>4?active:inactive);
				g.fillRoundRect(x-1, y, width, height, width, width);
		}
	
	
	}
	
	
	protected class HSB extends JComponent{
		
		/**
		 *La clase HSB funciona de manera automatica
		 *se puede cambiar si color activo mediante el atributo active
		 *de igual manera el inactive mediante el atributo inactive
		 *ambos pertenecientes a la clase <code>java.awt.Color</code>
		 */
		
		private void little() {
			(new Thread() {
				public void run() {
					bigerh=false;
					smallerh=true;
					while(hsb.height>4) {
						if(!smallerh)return;
						  hsb.height--;
						 // if(hsb.height%2==0)hsb.ypos++;
						  hsb.repaint();
						try {
							sleep(10);
						}
						catch(Exception e) {
							
						}
					}
					hsb.big=false;

				}
			}).start();			
		}

		private void crecer() {
			(new Thread() {
				public void run() {
					bigerh=true;
					smallerh=false;
					while(hsb.height<10) {
						  if(!bigerh)return;
						  hsb.height++;
						//  if(hsb.height%2==0)hsb.ypos--;
						  hsb.repaint();
						try {
							sleep(10);
						}
						catch(Exception e) {
							
						}
					}
					hsb.big=true;

				}
			}).start();			
		}
	
		boolean big=false,mp=false,mi=false;
		int x=0;
		int y=0;
		int ypos;
		int mx;
		int tx;
		int width    =1;
		int widthnow =1;
		int height   =4;
		int heightmax=10;
		Color active=Color.green,inactive=Color.darkGray;
		public HSB() {
			__init();
		}
		
		public void __init() {
			
			ypos= ScrollPanel.this.getHeight()-height-10;
			this.setBackground(Color.darkGray);
			//this.setOpaque(true);
			this.setSize(width,height);
			this.addMouseMotionListener(new MouseMotionListener() {
				@Override
				public void mouseDragged(MouseEvent e) {
					//System.out.println("ruedita HSB");
				//	System.out.println(y+"/"+ e.getY());
					//if(!ScrollPanel.this.vsb.isShowing()) {
						int tox = tx+e.getXOnScreen()-mx;
						if(tox<0)tox=0;
						else if(tox>ScrollPanel.this.getWidth()-HSB.this.getWidth())tox=ScrollPanel.this.getWidth()-HSB.this.getWidth();
						HSB.this.setLocation(tox,HSB.this.getY());
						HSB h = HSB.this;
						JPanel b = ScrollPanel.this.back;
						b.setLocation(-(int)(b.getWidth()*(double)h.getX()/ScrollPanel.this.getWidth()),b.getY());
					//	System.out.println((b.getWidth()*(double)h.getX()/ScrollPanel.this.getWidth()));
					//}
				}

				@Override
				public void mouseMoved(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
			});
			this.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub(int)
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					mp=true;
					mx=e.getXOnScreen();
					tx=HSB.this.getX();
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					mp=false;
					if(!mi)
					little();
					
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					mi=true;
					crecer();
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					mi=false;
					if(!mp)
					little();
					
				}
				
			});
			ScrollPanel.this.addMouseWheelListener(new MouseWheelListener() { 		
				//fuera de funcionamiento porque es horizontal solo funciona si el puntero esta encima

				int MOVE=50;
				
				private void Move(int d) {
					JPanel b = ScrollPanel.this.back;
					HSB h = HSB.this;
					(new Thread() {
						public void run() {
							if(!ScrollPanel.this.vsb.isShowing()&&ScrollPanel.this.hsb.isShowing())
							for(int i=0;i<10;i++) {	
								int t=(int)(((double)MOVE/b.getWidth())*100.00);
								int tox = h.getX()+(d>0?t:-t);
								if(tox<0)tox=0;
								else if(tox>ScrollPanel.this.getWidth()-HSB.this.getWidth())tox=ScrollPanel.this.getWidth()-HSB.this.getWidth();
								h.setLocation(tox,h.getY());
								b.setLocation(-(int)(b.getWidth()*(double)h.getX()/ScrollPanel.this.getWidth()),b.getY());
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
				public void mouseWheelMoved(MouseWheelEvent e) {
					//System.out.println(e.getWheelRotation());
					Move(e.getWheelRotation());
				}
			});
			if(ScrollPanel.this.getWidth()<=ScrollPanel.this.back.getWidth())this.setVisible(false);
			toDown();
		}
		
		protected void toUp() {
			ypos=5;
		}
		
		protected void toDown() {
			ypos=ScrollPanel.this.getHeight()-heightmax-(height/2);
		}
		
		@Override
		public void paint(Graphics g) {
				toDown();
				this.setLocation(this.getX(),ypos);
				width = (int) (ScrollPanel.this.getWidth()*(double)ScrollPanel.this.getWidth()/back.getWidth());
				width = Math.max(width, 20);
				//System.out.println(width);
				//this.setSize(width,height);
				this.setSize(widthnow<width?widthnow+=5:widthnow>width?--widthnow:width,height);
				
				//Graphics2D g2 = (Graphics2D) g;
				g.setColor(height>4?active:inactive);
				g.fillRoundRect(x, y-1, width, height, height, height);
				
		}
	}


	protected class Border extends JComponent{
		private boolean up,down,left,right;
		private Color UP,DOWN,LEFT,RIGHT;
		
		public Border(){
			this.setSize(ScrollPanel.this.getSize());
			up=down=left=right=true;
			UP=DOWN=LEFT=RIGHT=Color.red;
		}
		
		protected void show(String b,boolean s) {
			/**
			 * show recive string up||down||left||right||all and boolean show
			 */
			switch(b) {
			case "up" :up=s;break;
			case "down" :down=s;break;
			case "left" :left=s;break;
			case "right" :right=s;break;
			case "all" :up=down=left=right=s;break;
			}
			
			repaint();
		}
		
		protected void color(String b,Color c) {
			/**
			 * color recive string up||down||left||right||all and Color color
			 */
			switch(b) {
			case "up" :UP=c;break;
			case "down" :DOWN=c;break;
			case "left" :LEFT=c;break;
			case "right" :RIGHT=c;break;
			case "all" :UP=DOWN=LEFT=RIGHT=c;break;
			}
			
			repaint();
		}
		
		protected Color getColor(String b) {
			switch(b) {
			case "up":   return UP;
			case "down": return DOWN;
			case "left": return LEFT;
			case "right":return RIGHT;
			default: 	 return null;
			}
		}
		
		@Override
		public void paint(Graphics g) {
			//System.out.println(up+" "+down+" "+left+" "+right);
			if(up) {g.setColor(UP);g.drawRect(0, -1, ScrollPanel.this.getWidth(), 1);}
			if(down) {g.setColor(DOWN);g.drawRect(0, ScrollPanel.this.getHeight()-1, ScrollPanel.this.getWidth(), 2);}
			if(left) {g.setColor(LEFT);g.drawRect(-1, 0, 1, ScrollPanel.this.getHeight());}
			if(right) {g.setColor(RIGHT);g.drawRect(ScrollPanel.this.getWidth()-1, 0, 2, ScrollPanel.this.getHeight());}
		}
		
	}
	

	public ScrollPanel() {
		__init();
	}


	public ScrollPanel(int w,int h) {
		back.setSize(w,h);
		this.setSize(w,h);
		__init();
		
	}
	
	public void add_item(Component j,int separacion) {
		int y=back.getHeight()+separacion;
		int x=(back.getWidth()/2)-(j.getWidth()/2);
		j.setLocation(x,y);
		
		back.add(j);
		back.setSize(back.getWidth(),y+j.getHeight());
		System.out.println(j.getLocation());
	}
	
	public void __init() {
		//this.setBackground(Color.red);
		this.setLayout(null);
		this.setOpaque(false);
		back.setBackground(Color.blue);
		back.setOpaque(true);
		back.setLayout(null);
		vsb = new VSB();
		hsb = new HSB();
		border = new Border();
		
		this.setLayer(border,2);
		this.setLayer(hsb, 1);
		this.setLayer(vsb, 1);
		this.setLayer(back, 0);
		
		this.add(back);
		this.add(vsb);
		this.add(hsb);
		this.add(border);
		//motiontracking();
		sizetracking();
		
	}
	
	private void sizetracking() {
		ComponentListener resize = new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				if(ScrollPanel.this.getHeight()<back.getHeight()) {
					border.repaint();
					vsb.repaint();
					vsb.setVisible(true);
				}
				else vsb.setVisible(false);
					
				if(ScrollPanel.this.getWidth()<back.getWidth()) {
					border.repaint();
					hsb.repaint();
					hsb.setVisible(true);
				}
				else hsb.setVisible(false);
				
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		};
		this.addComponentListener(resize);
		back.addComponentListener(resize);
		
	}
	
	private void motiontracking() {
		addMouseMotionListener(new MouseMotionListener() {
			int x,y;
			
			private void crecerv() {
				(new Thread() {
					public void run() {
						bigerv=true;
						smallerv=false;
						while(vsb.width<10&&bigerv) {
							  vsb.width++;
							  if(vsb.width%2==0)vsb.xpos--;
							  vsb.repaint();
							try {
								sleep(10);
							}
							catch(Exception e) {
								
							}
						}
						vsb.big=true;
					}
				}).start();			
			}
			
			private void crecerh() {
				(new Thread() {
					public void run() {
						bigerh=true;
						smallerh=false;
						while(hsb.height<10&&bigerh) {
							  hsb.height++;
							  if(hsb.height%2==0)hsb.ypos--;
							  hsb.repaint();
							try {
								sleep(10);
							}
							catch(Exception e) {
								
							}
						}
						hsb.big=true;
					}
				}).start();			
			}
		
			private void littlev() {
				(new Thread() {
					public void run() {
						bigerv=false;
						smallerv=true;
						while(vsb.width>4&&smallerv) {
							  vsb.width--;
							  if(vsb.width%2==0)vsb.xpos++;
							  vsb.repaint();
							try {
								sleep(10);
							}
							catch(Exception e) {
								
							}
						}
						vsb.big=false;
					}
				}).start();			
			}
			
			private void littleh() {
				(new Thread() {
					public void run() {
						bigerh=false;
						smallerh=true;
						while(hsb.height>4&&smallerh) {
							  hsb.height--;
							  if(hsb.height%2==0)hsb.ypos++;
							  hsb.repaint();
							try {
								sleep(10);
							}
							catch(Exception e) {
								
							}
						}
						hsb.big=false;
					}
				}).start();			
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				x=e.getX();
				y=e.getY();
				
				//System.out.println(x+" "+y + " big:"+vsb.big);
				if(x>=vsb.xpos-2&&x<=vsb.xpos+vsb.width+2&&!vsb.big) {
					crecerv();
					vsb.repaint();
				}
				else if((x<=vsb.xpos-2||x>=vsb.xpos+vsb.width+2)&&vsb.big){
					littlev();
					vsb.repaint();
				}
				
				if(y>=hsb.ypos-2&&y<=hsb.ypos+hsb.height+2&&!hsb.big) {
					crecerh();
					hsb.repaint();
				}
				else if((y<=hsb.ypos-2||y>=hsb.ypos+hsb.height+2)&&hsb.big){
					littleh();
					hsb.repaint();
				}
				
			}
			
		});
	}
	
	public static void main(String[] args) {
		javax.swing.JFrame root = new javax.swing.JFrame();
		root.setLayout(null);
		root.setSize(700,700);
		root.setDefaultCloseOperation(root.EXIT_ON_CLOSE);
		root.setVisible(true);
		ScrollPanel sc = new ScrollPanel();
		sc.setSize(250,250);
		sc.back.setSize(800,0);
		root.add(sc);
		for(int i=0;i<50;i++) {
			JLabel jl = new JLabel();
			jl.setSize(100,50);
			jl.setBackground(Color.red);
			jl.setOpaque(true);
			sc.add_item(jl, 5);
			try {
				Thread.sleep(500);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}