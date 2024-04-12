package com.nobody.adMEnestrator;

import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;

public abstract class ToggleButton extends JComponent{
	/**
	 * sobrescribir public void actionPerformed() para la accion que desea realizar
	 */
	boolean canclick=true;
	boolean vertical=false;
	boolean moving=false;
	boolean actioning=false;
	boolean active=false;
	Color left=Color.blue;
	Color right=Color.LIGHT_GRAY;
	Color ball=Color.white;
	Color border=Color.black;
	int ballpos=0;
	
	public ToggleButton() {
		this.setLayout(null);
		__init();
	}
	
	public ToggleButton(int w,int h) {
		if(h>w)vertical=true;
		this.setLayout(null);
		this.setSize(w,h);
		__init();
	}
	
	public abstract void  actionPerformed();
	
	
	private void __init() {
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getButton()==e.BUTTON1) {
					
					//System.out.println(actioning+" "+moving+" "+canclick);
					if(!ToggleButton.this.canclick)return;
					ToggleButton.this.canclick=false;
					(new Thread() {
						public void run() {
							ToggleButton.this.actioning=true;
							actionPerformed();
							ToggleButton.this.actioning=false;
							if(!ToggleButton.this.actioning&&!ToggleButton.this.moving)ToggleButton.this.canclick=true;
							
						}
					}).start();
					//canclick=false;
					if(moving)e.consume();
					
						if(moving)return;
							moving=true;
							active=!active;
							(new Thread() {
								public void run() {
									
									long tiempo=150;
									long ti=System.currentTimeMillis();
									long tf=ti+tiempo;
									while(System.currentTimeMillis()<=tf) {
										long ta=System.currentTimeMillis()-ti;
										ToggleButton.this.ballpos=(int)(((float)ta/tiempo)*100);
										if(!active)ToggleButton.this.ballpos=100-ToggleButton.this.ballpos;
										ToggleButton.this.repaint();
									}
									if(active)ballpos=100;
									else ballpos=0;
									ToggleButton.this.repaint();
									moving=false;
									if(!ToggleButton.this.actioning&&!ToggleButton.this.moving)ToggleButton.this.canclick=true;
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
	
	@Override
	public void paint(Graphics g) {
		
		if(vertical) {
			int r=(this.getHeight()-this.getWidth())+1;
			int p=r*ballpos/100;
//			System.out.println(ballpos+"/"+p);
			g.setColor(left);
			g.fillRoundRect(-1, 0, this.getWidth()-1, this.getHeight()-1,this.getWidth()-1 ,this.getWidth()-1);
			g.setColor(right);
			g.fillRoundRect(-1,(int)(p+this.getWidth()/4), this.getWidth()-1, this.getHeight()-(p+this.getWidth()/4)-1,this.getWidth()-1 ,this.getWidth()-1);
			g.setColor(border);
			//g.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight(),this.getWidth() ,this.getWidth());
			g.setColor(ball);
			g.fillArc(-1,p,this.getWidth()-1,this.getWidth()-1,0,360);
		}
		
		else {
			int r=(this.getWidth()-this.getHeight())+1;
			int p=r*ballpos/100;
		//	System.out.println(ballpos+"/"+p);
			g.setColor(left);
			g.fillRoundRect(-1, 0, p+this.getHeight(), this.getHeight()-1,this.getHeight()-1 ,this.getHeight()-1);
			g.setColor(right);
			g.fillRoundRect((int)(p+this.getHeight()/4)-1,0, this.getWidth()-(p+this.getHeight()/4)-1,this.getHeight()-1 ,this.getHeight()-1 ,this.getHeight()-1);
			g.setColor(border);
			//g.drawRoundRect(0, 0, this.getWidth(), this.getHeight()-1,this.getHeight() ,this.getHeight());
			g.setColor(ball);
			g.fillArc(p-1,-1,this.getHeight() + 1,this.getHeight() +1,0,360);
		}
		
	}
	
	public static void main(String args[]) {
		javax.swing.JFrame root = new javax.swing.JFrame();
		root.setSize(500,500);
		root.setLayout(null);
		ToggleButton b = new ToggleButton(100,20) {
			public void actionPerformed() {
				
			}
		};
		root.add(b);
		root.setVisible(true);
		root.setDefaultCloseOperation(root.EXIT_ON_CLOSE);
	}
	
}
