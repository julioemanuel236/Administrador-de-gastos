package com.nobody.adMEnestrator;

import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Color;

public class ChargeBar extends JComponent {
	int barpos;
	int barwidth;
	Color background;
	public Color barcolor=Color.blue;
	boolean run=false;
	public ChargeBar(){
		this.setOpaque(false);
	}
	
	public ChargeBar(int w,int h) {
		this.setOpaque(false);
		this.setSize(w,h);
	}
	
	@Override
	public void paint(Graphics g) {
		if(background!=null) {
			g.setColor(background);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		if(barcolor!=null) {
			g.setColor(barcolor);
			g.fillRect(barpos, 0, barwidth, this.getHeight());
		}
		}
	
	public void start(){
		//int k=0;
		(new Thread() {
			public void run(){
				run=true;
				while(run) {
					barpos=0;
					long ti=System.currentTimeMillis();
					long tiempo=1000;
					long tf=ti+tiempo;
					while(System.currentTimeMillis()<tf) {
						long ta=System.currentTimeMillis()-ti;
						barwidth=(int)(((float)ta/tiempo)*ChargeBar.this.getWidth());
	//					System.out.println(barwidth);
						ChargeBar.this.repaint();
	//					k=k+1;
						try {
							sleep(10);
						}
						catch(Exception e) {
							
						}
						
					}
					barwidth=ChargeBar.this.getWidth();
					ChargeBar.this.repaint();
					ti=System.currentTimeMillis();
					tiempo=1000;
					tf=ti+tiempo;
					while(System.currentTimeMillis()<tf) {
						long ta=System.currentTimeMillis()-ti;
						barpos=(int)(((float)ta/tiempo)*ChargeBar.this.getWidth());
	//					System.out.println(barpos);
						ChargeBar.this.repaint();
						try {
							sleep(10);
						}
						catch(Exception e) {
							
						}
					}
					barpos=ChargeBar.this.getWidth();
					ChargeBar.this.repaint();
				}
			}
		}).start();
	}
	
	public void stop() {
		run=false;
	}
	
	public static void main(String[] args) {
		javax.swing.JFrame root = new javax.swing.JFrame();
		javax.swing.JPanel p = new javax.swing.JPanel();
		root.setSize(500,500);
		root.add(p);
		p.setLayout(null);
		ChargeBar chb= new ChargeBar(500,10);
		chb.setLocation(0,0);
		chb.barcolor = new Color(100,150,200);
		p.setBackground(Color.black);
		p.add(chb);
		chb.start();
		root.setVisible(true);
		root.setDefaultCloseOperation(root.EXIT_ON_CLOSE);
	}
}
