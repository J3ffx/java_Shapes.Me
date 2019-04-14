package graphics.shapes;

import java.awt.*;

import javax.swing.JOptionPane;

public class SRectangle extends Shape {

	private Rectangle rect;

	public SRectangle() {
		this.rect = new Rectangle();
	}

	public SRectangle(Point p, int width, int height) {
		this.rect = new Rectangle(p.x, p.y, width, height);
	}

	public Rectangle getRect() {
		return this.rect;
	}

	public Point getLoc() {
		Point p = new Point();
		p.setLocation(rect.x, rect.y);
		return p;
	}

	public void setLoc(Point loc) {
		this.rect.x = loc.x;
		this.rect.y = loc.y;
	}

	public void translate(int dx, int dy) {
		this.rect.translate(dx, dy);
	}

	@Override
	public void grow(int x, int y) {
		this.rect.setSize(this.rect.getSize().width+x, this.rect.getSize().height+y);
	}

	public Rectangle getBounds() {
		return this.rect;
	}

	public void accept(ShapeVisitor sv) {
		sv.visitRectangle(this);
	}

	public void resize() {
		int askWidth = Integer.parseInt(JOptionPane.showInputDialog("Please enter width : "));
		int askHeight = Integer.parseInt(JOptionPane.showInputDialog("Please enter height : "));
		this.rect.width = askWidth;
		this.rect.height = askHeight;
	}
	
	@Override
	public String toString() {
		return "SRectangle [" + this.rect.x + "," + this.rect.y + "]";
	}

	@Override
	public boolean isText() {
		return false;
	}
}
