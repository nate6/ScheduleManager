package manager;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class RoundedButton extends JButton {
	private static final long serialVersionUID = 8399931181897521785L;
	
	public RoundedButton(String text, int x, int y, int width, int height) {
		setText(text);
		setBounds(x, y, width, height);
		setContentAreaFilled(false);
		setFocusable(false);
		setBorder(new LineBorder(Color.BLACK, 2));
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (getModel().isArmed()) {
			g.setColor(new Color(160, 180, 220));
		}
		else {
			g.setColor(new Color(200, 200, 200));
		}
		g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 30, 30);
		super.paintComponent(g);
	}
	
	@Override
	protected void paintBorder(Graphics g) {
		RoundRectangle2D.Double area = new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, 30, 30);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(120, 120, 120));
		g2.setStroke(new BasicStroke(3));
		g2.draw(area);
	}
}
