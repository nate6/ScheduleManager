package manager;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Notification extends JFrame {
	private static final long serialVersionUID = -3556747532882260619L;
	private DelayPane delay;
	private String message;
	private String link;
	
	public static void main(String[] args) {
		new Notification("Need To Code", "C:\\Users\\Nate\\Documents\\Coding");
	}
	
	public Notification(String message, String link) {
		this.message = message;
		this.link = link;
		
		setSize(275, 125);
		setLayout(null);
		delay = new DelayPane(getWidth(), getHeight()-40, this);
		setComponents();
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		Insets toolHeight = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
		setLocation(scrSize.width - getWidth(), scrSize.height - toolHeight.bottom - getHeight());
		setUndecorated(true);
		setAlwaysOnTop(true);
		setVisible(true);
		
		timer();
	}

	public void setComponents() {
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, getWidth(), getHeight());
		panel.setLayout(null);
		panel.setBackground(new Color(255, 146, 78));
		add(panel);
		
		JLabel messageLabel = new JLabel(message);
		ImageIcon timerIcon = new ImageIcon(getClass().getResource("/manager/clock.png"));
		messageLabel.setIcon(timerIcon);
		messageLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		messageLabel.setForeground(new Color(15, 15, 15));
		messageLabel.setOpaque(false);
		messageLabel.setBounds(5, -5, getWidth()-60, 80);
		panel.add(messageLabel);
		
		JButton closeButton = new JButton("X");
		closeButton.setFont(new Font(closeButton.getFont().getFontName(), Font.PLAIN, 14));
		closeButton.setFocusable(false);
		closeButton.setMargin(new Insets(0, 0, 1, 0));
		closeButton.setBounds(getWidth()-24, 7, 17, 20);
		closeButton.setContentAreaFilled(false);
		closeButton.setRolloverEnabled(false);
		closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	closeButton.setForeground(Color.RED);
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	closeButton.setForeground(Color.BLACK);
		    }
		});
		closeButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		delay.dispose();
        	}
		});
		panel.add(closeButton);
		
		RoundedButton open = new RoundedButton("Open", 5, getHeight()-45, getWidth()/2-10, 40);
		open.setFont(new Font("Calibri", Font.PLAIN, 18));
		open.setForeground(new Color(20, 20, 20));
		open.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
        			Desktop.getDesktop().open(new File(link));
        		} catch (IOException eIO) {
        			System.out.println("File Not Found");
        			eIO.printStackTrace();
        		}
        	}
		});
		panel.add(open);
		
		RoundedButton postpone = new RoundedButton("Remind Later", getWidth()/2+5, getHeight()-45, getWidth()/2-10, 40);
		postpone.setFont(new Font("Calibri", Font.PLAIN, 18));
		postpone.setForeground(new Color(20, 20, 20));
		postpone.addActionListener(new ActionListener() {
			boolean turnOn = true;
        	public void actionPerformed(ActionEvent e) {
        		if (turnOn) {
            		delay.setVisible(true);
            		delay.setSize(getWidth()/2-8, delay.getMaxHeight());
        			delay.animate();
            		turnOn = false;
        		}
        		else {
        			delay.deanimate();
        			turnOn = true;
        		}
        	}
		});
		panel.add(postpone);
	}
	
	public void timer() {
		new Thread(){
			@Override
			public void run() {
				try {
					Thread.sleep(10000);
	                if (!delay.isVisible()) {
	                	dispose();
		                delay.dispose();
	                }
	            } catch (InterruptedException e) {
	            	e.printStackTrace();
		        }
			};
		}.start();
	}
}
