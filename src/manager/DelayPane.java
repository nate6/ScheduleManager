package manager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;

public class DelayPane extends JFrame {
	private static final long serialVersionUID = -1531775781996782560L;
	private JFrame mainNotification;
	private int width;
	private int xloc;
	private int yloc;
	private int maxHeight;

	public DelayPane(int width, int height, JFrame mainNotification) {
		this.mainNotification = mainNotification;
		this.width = width/2-8;
		setSize(this.width, 800);
		maxHeight = height;
		setLayout(null);
		
		setComponents();
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		Insets toolHeight = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
		xloc = scrSize.width - width/2;
		yloc = scrSize.height - toolHeight.bottom - 47;
		setLocation(xloc, yloc);
		setUndecorated(true);
		setAlwaysOnTop(true);
	}
	
	public void setComponents() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setBounds(0, 0, getWidth(), getHeight());
		mainPanel.setBackground(new Color(255, 242, 175));
		add(mainPanel);
		
		JLabel desc = new JLabel("Remind me in:");
		desc.setBounds(15, 5, 100, 20);
		desc.setFont(new Font("Calibri", Font.BOLD, 16));
		mainPanel.add(desc);
		
		SpinnerModel spinnerTimeModel = new SpinnerNumberModel(1, 1, 59, 1);
		JSpinner delaySpinner = new JSpinner(spinnerTimeModel);
		delaySpinner.setBounds((width/2)-35, 25, 34, 20);
		mainPanel.add(delaySpinner);
		
		String[] timeTypeStrings = {"m", "h", "d"};
		SpinnerListModel spinnerTypeModel = new SpinnerListModel(timeTypeStrings);
		JSpinner timeTypeSpinner = new JSpinner(spinnerTypeModel);
		timeTypeSpinner.setBounds((width/2)+5, 25, 32, 20);
		mainPanel.add(timeTypeSpinner);
		
		RoundedButton set = new RoundedButton("Set", 15, 50, 100, 30);
		set.setText("Set");
		set.setBackground(Color.lightGray);
		set.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO
				mainNotification.dispose();
				dispose();
			}
		});
		mainPanel.add(set);
	}
	
	public int getMaxHeight() {
		return maxHeight;
	}
	
	public void reset() {
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		Insets toolHeight = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
		yloc = scrSize.height - toolHeight.bottom - 47;
	}
	
	public void animate() {
		reset();
		setVisible(true);
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			int i = 5;
			int size = 0;
			@Override
			public void run() {
				yloc -= 2;
				size += 2;
				setLocation(getX(), yloc);
				setSize(getWidth(), size);
		    	if (maxHeight-getHeight() >= maxHeight-10) {
		    		try {
		    			if (i < 0) i = 0;
						Thread.sleep(i);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		    		i-=1;
		    	}
		    	if (maxHeight-getHeight() <= 30) {
		    		try {
		    			if (i < 0) i = 0;
						Thread.sleep(i);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		    		i+=1;
		    	}
		        if (maxHeight-getHeight() <= 0) {
		        	timer.cancel();
		        }
		        repaint();
			}
		}, 10, 3);
    }

	public void deanimate() {
		reset();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			int i = 5;
			int size = 0;
			@Override
			public void run() {
				yloc += 2;
				size -= 2;
				setLocation(getX(), yloc);
				setSize(getWidth(), size);
		    	if (getHeight() >= maxHeight-10) {
		    		try {
		    			if (i < 0) i = 0;
						Thread.sleep(i);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		    		i-=1;
		    	}
		    	if (getHeight() <= 30) {
		    		try {
		    			if (i < 0) i = 0;
						Thread.sleep(i);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		    		i+=1;
		    	}
		        if (getHeight() <= 0) {
		        	timer.cancel();
		        }
		        repaint();
			}
		}, 10, 3);
		setVisible(false);
	}
}
