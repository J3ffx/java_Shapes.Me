package graphics.shapes.ui;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.ListIterator;

import javax.swing.JOptionPane;

import graphics.shapes.SCircle;
import graphics.shapes.SCollection;
import graphics.shapes.SRectangle;
import graphics.shapes.SText;
import graphics.shapes.Selection;
import graphics.shapes.Shape;
import graphics.shapes.attributes.ColorAttributes;
import graphics.shapes.attributes.FontAttributes;
import graphics.shapes.attributes.SelectionAttributes;
import graphics.shapes.interpret.CNewCircle;
import graphics.shapes.interpret.Processor;
import graphics.shapes.interpret.ProcessorException;
import graphics.ui.Controller;

public class ShapesController extends Controller {

	private boolean dragging;
	private ArrayList<Shape> cop;
	private ArrayList<Shape> del;
	private Point clickLoc;
	private boolean command;

	public ShapesController(SCollection model) {
		super(model);
		this.dragging = false;
		this.del = new ArrayList<Shape>();
		this.command = false;

	}

	@Override
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		this.clickLoc = e.getPoint();
		if (e.getButton() == 1) {
			if (!this.dragging) {
				this.dragging = true;
			}
			if (selected().isEmpty()) {
				Selection s = new Selection(e.getPoint(), 0, 0);
				((SCollection) (super.getModel())).add(s);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		this.getView().repaint();
		this.dragging = false;
		ArrayList<Selection> toGrow = sel();
		for (Selection sel : toGrow) {
			SelectionAttributes sa = new SelectionAttributes();
			sa.select();
			SCollection sc = (SCollection) this.getModel();
			for (Iterator<Shape> it = sc.iterator(); it.hasNext();) {
				Shape currentShape = it.next();
				if (sel.contains(currentShape))
					currentShape.addAttributes(sa);
			}
		}
		clear();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		if (e.getButton() == 1) {
			Shape s = this.isAimed(e);
			if (!e.isShiftDown())
				this.unselAll();
			if (s != null) {
				SelectionAttributes sa = new SelectionAttributes();
				sa.select();
				s.addAttributes(sa);
			}
		}
		if (e.getButton() == 3) {
			doPop(e);
		}
		super.getView().repaint();
	}

	@Override
	public void mouseMoved(MouseEvent evt) {
		super.mouseMoved(evt);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		super.mouseWheelMoved(e);
		boolean wheelRotation = e.getWheelRotation() < 0;
		ArrayList<Shape> toGrow = selected();
		for (Shape currentShape : toGrow) {
			if (wheelRotation)
				currentShape.grow(5, 5);
			else
				currentShape.grow(-5, -5);
			this.getView().repaint();
		}

	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		super.mouseDragged(evt);
		if (this.dragging) {
			SCollection sc = (SCollection) this.getModel();
			sc.translate(evt.getPoint().x - this.clickLoc.x, evt.getPoint().y - this.clickLoc.y);
			this.clickLoc = evt.getPoint();
			super.getView().repaint();
		}
		ArrayList<Selection> toGrow = sel();
		for (Selection sel : toGrow) {
			sel.resize(evt.getX() - sel.getLoc().x, evt.getY() - sel.getLoc().y);
		}
		super.getView().repaint();
	}

	public void unselAll() {
		SCollection sc = (SCollection) this.getModel();
		for (Iterator<Shape> it = sc.iterator(); it.hasNext();) {
			Shape currentShape = it.next();
			SelectionAttributes sa = new SelectionAttributes();
			sa.unselect();
			currentShape.addAttributes(sa);
			super.getView().repaint();
		}
	}

	public Shape isAimed(MouseEvent e) {
		Shape s;
		SCollection sc = (SCollection) this.getModel();
		for (Iterator<Shape> i = sc.iterator(); i.hasNext();) {
			s = (Shape) i.next();
			if (s.getBounds().contains(e.getPoint()))
				return s;
		}
		return null;
	}

	@Override
	public void keyTyped(KeyEvent evt) {
		super.keyPressed(evt);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		doKey(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		super.keyReleased(e);
	}

	public ArrayList<Shape> selected() {
		ArrayList<Shape> shapes = new ArrayList<Shape>();
		SCollection sc = (SCollection) this.getModel();
		for (Iterator<Shape> it = sc.iterator(); it.hasNext();) {
			Shape currentShape = it.next();
			SelectionAttributes selectAtt = (SelectionAttributes) currentShape.getAttributes(SelectionAttributes.id);
			if (selectAtt != null) {
				if (selectAtt.isSelected()) {
					shapes.add(currentShape);
				}
			}
		}
		return shapes;
	}

	public void delete() {
		ArrayList<Shape> toDelete = selected();
		if (!selected().isEmpty())
			del.clear();
		for (Shape currentShape : toDelete) {
			this.del.add(currentShape);
			((SCollection) (this.getModel())).remove(currentShape);
			this.getView().repaint();
		}
		unselAll();
	}

	public void clear() {
		ArrayList<Selection> toClear = sel();
		for (Shape shape : toClear) {
			((SCollection) (this.getModel())).remove(shape);
		}
	}

	public ArrayList<Selection> sel() {
		ArrayList<Selection> selec = new ArrayList<Selection>();
		SCollection sc = (SCollection) this.getModel();
		for (Iterator<Shape> it = sc.iterator(); it.hasNext();) {
			Shape currentShape = it.next();
			if (currentShape instanceof Selection) {
				selec.add((Selection) currentShape);
			}
		}
		return (selec);
	}

	public void undo() {
		ListIterator<Shape> it = this.del.listIterator();
		while (it.hasNext()) {
			Shape s = it.next();
			((SCollection) getModel()).add(s);
		}
		this.del.clear();
		getView().repaint();
	}

	public void rectangle(MouseEvent e) {
		SRectangle r = new SRectangle(e.getPoint(), 40, 70);
		r.addAttributes(new ColorAttributes(false, true, Color.cyan, Color.black));
		r.addAttributes(new SelectionAttributes());
		((SCollection) (super.getModel())).add(r);
		this.getView().repaint();
	}

	public void circle(MouseEvent e) {
		SCircle c = new SCircle(e.getPoint(), 20);
		c.addAttributes(new ColorAttributes(false, true, Color.gray, Color.black));
		c.addAttributes(new SelectionAttributes());
		((SCollection) (super.getModel())).add(c);
		this.getView().repaint();
	}

	public void text(MouseEvent e, String text) {
		SText t = new SText(e.getPoint(), text);
		t.addAttributes(new ColorAttributes(false, true, Color.yellow, Color.black));
		t.addAttributes(new FontAttributes());
		t.addAttributes(new SelectionAttributes());
		((SCollection) (super.getModel())).add(t);
		this.getView().repaint();
	}

	public void filled(Color color) {
		ArrayList<Shape> toChange = selected();
		for (Shape currentShape : toChange) {
			if (currentShape instanceof SCollection) {
				for (Shape shape : ((SCollection) currentShape).getCollection()) {
					ColorAttributes c = ((ColorAttributes) shape.getAttributes(ColorAttributes.id));
					c.setFilled(true);
					c.setFilledColor(color);
					this.getView().repaint();
				}
			} else {
				ColorAttributes c = ((ColorAttributes) currentShape.getAttributes(ColorAttributes.id));
				c.setFilled(true);
				c.setFilledColor(color);
				this.getView().repaint();
			}
		}
	}

	public void stroked(Color color) {
		ArrayList<Shape> toChange = selected();
		for (Shape currentShape : toChange) {
			if (currentShape instanceof SCollection) {
				for (Shape shape : ((SCollection) currentShape).getCollection()) {
					ColorAttributes c = ((ColorAttributes) shape.getAttributes(ColorAttributes.id));
					c.setStroked(true);
					c.setStrokedColor(color);
					this.getView().repaint();
				}
			} else {
				ColorAttributes c = ((ColorAttributes) currentShape.getAttributes(ColorAttributes.id));
				c.setStroked(true);
				c.setStrokedColor(color);
				this.getView().repaint();
			}
		}
	}

	public void textC(Color color) {
		ArrayList<Shape> toChange = selected();
		for (Shape currentShape : toChange) {
			FontAttributes t = ((FontAttributes) currentShape.getAttributes(FontAttributes.id));
			if (currentShape instanceof SText)
				t.setFontColor(color);
			this.getView().repaint();
		}
	}

	public void none(String type) {
		ArrayList<Shape> toChange = selected();
		for (Shape currentShape : toChange) {
			if (currentShape instanceof SCollection) {
				for (Shape shape : ((SCollection) currentShape).getCollection()) {
					ColorAttributes c = ((ColorAttributes) shape.getAttributes(ColorAttributes.id));
					boolean bool = shape.isText();
					if (type == "filled" && c.isStroked() || type == "filled" && bool)
						c.setFilled(false);
					else if (c.isFilled() || bool)
						c.setStroked(false);
					this.getView().repaint();
				}
			} else {

				ColorAttributes c = ((ColorAttributes) currentShape.getAttributes(ColorAttributes.id));
				boolean bool = currentShape.isText();
				if (type == "filled" && c.isStroked() || type == "filled" && bool)
					c.setFilled(false);
				else if (c.isFilled() || bool)
					c.setStroked(false);
				this.getView().repaint();
			}
		}
	}

	public void copy() {
		this.cop = selected();
	}

	public void paste() {
		SCollection model = (SCollection) this.getModel();
		if (this.cop != null) {
			for (Shape currentShape : this.cop) {
				Shape s = clone(currentShape);
				model.add(s);
			}
			cop.clear();
			getView().repaint();
		}
	}

	private Shape clone(Shape s) {
		Shape newShape = null;

		if (s instanceof SRectangle) {
			SRectangle rec = (SRectangle) s;
			newShape = new SRectangle(new Point(rec.getLoc().x + 5, rec.getLoc().y + 5), rec.getRect().width,
					rec.getRect().height);
			ColorAttributes ca = (ColorAttributes) rec.getAttributes(ColorAttributes.id);
			newShape.addAttributes(
					new ColorAttributes(ca.isFilled(), ca.isStroked(), ca.getFilledColor(), ca.getStrokedColor()));
		} else if (s instanceof SCircle) {
			SCircle cir = (SCircle) s;
			newShape = new SCircle(new Point(cir.getLoc().x + 5, cir.getLoc().y + 5), cir.getRadius());
			ColorAttributes ca = (ColorAttributes) cir.getAttributes(ColorAttributes.id);
			newShape.addAttributes(
					new ColorAttributes(ca.isFilled(), ca.isStroked(), ca.getFilledColor(), ca.getStrokedColor()));
		} else if (s instanceof SText) {
			SText txt = (SText) s;
			newShape = new SText(new Point(txt.getLoc().x + 5, txt.getLoc().y + 5), txt.getText());
			newShape.addAttributes((FontAttributes) s.getAttributes(FontAttributes.id));
			ColorAttributes ca = (ColorAttributes) txt.getAttributes(ColorAttributes.id);
			newShape.addAttributes(
					new ColorAttributes(ca.isFilled(), ca.isStroked(), ca.getFilledColor(), ca.getStrokedColor()));

		} else if (s instanceof SCollection) {
			SCollection col = (SCollection) s;
			newShape = new SCollection();
			for (Shape shape : col.getCollection()) {
				((SCollection) newShape).add(clone(shape));
			}
		}
		newShape.addAttributes(new SelectionAttributes());
		return newShape;
	}

	public void split() {
		SCollection model = (SCollection) this.getModel();
		for (Shape col : selected()) {
			if (col instanceof SCollection) {
				for (Shape currentShape : ((SCollection) col).getCollection()) {
					Shape s = clone(currentShape);
					model.add(s);
				}
				delete();
				del.clear();
				getView().repaint();
			}
		}
	}

	public void join() {
		ArrayList<Shape> toJoin = selected();
		SCollection model = (SCollection) this.getModel();
		SCollection sc = new SCollection();
		for (Shape currentShape : toJoin) {
			sc.add(clone(currentShape));
		}
		delete();
		del.clear();
		model.add(sc);
	}

	public void resize() {
		ArrayList<Shape> toResize = selected();
		for (Shape s : toResize) {
			if (s instanceof SRectangle) {

				((SRectangle) s).resize();
			} else if (s instanceof SCircle) {
				((SCircle) s).resize();
			} else if (s instanceof SText) {
				((SText) s).resize();
			} else if (s instanceof SCollection) {
				((SCollection) s).resize(s);
				getView().repaint();
			}
		}
		getView().repaint();
	}

	public void changeText() {
		ArrayList<Shape> toRename = selected();
		for (Shape s : toRename) {
			if (s instanceof SText) {
				String askText = JOptionPane.showInputDialog("Please enter text : ");
				((SText) s).setText(askText);
				getView().repaint();
			}
		}
	}

	public void selectAll() {
		Shape s;
		SCollection sc = (SCollection) this.getModel();
		for (Iterator<Shape> i = sc.iterator(); i.hasNext();) {
			s = (Shape) i.next();
			SelectionAttributes sa = new SelectionAttributes();
			sa.select();
			s.addAttributes(sa);
		}
	}

	public void save() {
		XMLSave xml = new XMLSave();
		xml.save((SCollection) super.getModel());
	}

	public void load() {
		XMLSave xml = new XMLSave();
		xml.load();
	}

	public void ne() {
		Editor self = new Editor(null);
		self.pack();
		self.setVisible(true);
	}

	private void doPop(MouseEvent e) {
		PopupMenu p = new PopupMenu(this);
		p.pop(e);

	}

	private void doKey(KeyEvent e) {
		KeyAction k = new KeyAction(this);
		k.key(e);
	}

	public void toggleCommand() {
		Processor p = new Processor(this);
		p.addCmd(new CNewCircle());

		this.command = !this.command;
		//while (command) {
			command(p);
			getView().repaint();
		//}
	}

	public void command(Processor p) {
		System.out.print("-> ");
		try {
			p.execute(p.decode(p.fetch()));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ProcessorException e) {
			e.printStackTrace();
		} catch (InputMismatchException e) {
			e.printStackTrace();
		}
	}
}