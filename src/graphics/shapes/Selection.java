package graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;

import graphics.shapes.Shape;
import graphics.shapes.ShapeVisitor;


public class Selection extends Shape{
	
	private Rectangle sel;
	
	public Selection(Point p, int width, int height) {
		this.sel = new Rectangle(p.x, p.y, width, height);
	}
	
	public Rectangle getRect() {
		return this.sel;
	}

	public Point getLoc() {
		Point p = new Point();
		p.setLocation(sel.x, sel.y);
		return p;
	}

	public void setLoc(Point loc) {
		this.sel.x = loc.x;
		this.sel.y = loc.y;
	}
	
	public void resize(int width, int height) {
		this.sel.width = width;
		this.sel.height = height;
	}

	@Override
	public void translate(int dx, int dy) {		
	}

	@Override
	public void grow(int x, int y) {		
	}

	@Override
	public Rectangle getBounds() {
		return this.getRect().getBounds();
	}

	@Override
	public void accept(ShapeVisitor sv) {
		sv.visitSelection(this);
		
	}

	@Override
	public boolean isText() {
		return false;
	}

	@Override
	public void resize() {
	}

	public boolean contains(Shape s) {
		if (sel.contains(s.getBounds()))
					return true;
		return false;
	}
}
