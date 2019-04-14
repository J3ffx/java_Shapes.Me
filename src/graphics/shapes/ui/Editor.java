package graphics.shapes.ui;

import graphics.shapes.SCircle;
import graphics.shapes.SCollection;
import graphics.shapes.SRectangle;
import graphics.shapes.SText;
import graphics.shapes.attributes.ColorAttributes;
import graphics.shapes.attributes.FontAttributes;
import graphics.shapes.attributes.SelectionAttributes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Editor extends JFrame {
	ShapesView sview;
	SCollection model;
	// SAVE
	XMLSave xml;
	//

	private static final long serialVersionUID = 0;

	public Editor(SCollection model) {
		super("404's Editor");

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				System.exit(0);
			}
		});
		
		// ICON
		ImageIcon img = new ImageIcon("ico.png");
		this.setIconImage(img.getImage());
		//
		// SAVE
		this.xml = new XMLSave();
		//

		this.buildModel(model);

		this.sview = new ShapesView(this.model);
		this.sview.setPreferredSize(new Dimension(1000, 500));
		this.getContentPane().add(this.sview, BorderLayout.CENTER);

		// MENUBAR
		MenuBar m = new MenuBar((ShapesController) this.sview.getController());
		setJMenuBar(m.bar());
		m.setVisible(true);
		//
	}

	private void buildModel(SCollection model) {
		if (model == null) {
			this.model = new SCollection();
			this.model.addAttributes(new SelectionAttributes());

			SRectangle r = new SRectangle(new Point(10, 10), 20, 30);
			r.addAttributes(new ColorAttributes(true, false, Color.BLUE, Color.BLUE));
			r.addAttributes(new SelectionAttributes());
			this.model.add(r);

			SCircle c = new SCircle(new Point(50, 50), 10);
			c.addAttributes(new ColorAttributes(true, true, Color.RED, Color.BLUE));
			c.addAttributes(new SelectionAttributes());
			this.model.add(c);

			SText t = new SText(new Point(100, 50), "hello");
			t.addAttributes(new ColorAttributes(true, true, Color.YELLOW, Color.BLUE));
			t.addAttributes(new FontAttributes());
			t.addAttributes(new SelectionAttributes());
			this.model.add(t);

			SCollection sc = new SCollection();
			sc.addAttributes(new SelectionAttributes());
			r = new SRectangle(new Point(20, 100), 30, 30);
			r.addAttributes(new ColorAttributes(true, true, Color.MAGENTA, Color.YELLOW));
			r.addAttributes(new SelectionAttributes());
			sc.add(r);
			c = new SCircle(new Point(75, 100), 20);
			c.addAttributes(new ColorAttributes(false, true, Color.BLUE, Color.DARK_GRAY));
			c.addAttributes(new SelectionAttributes());
			sc.add(c);
			this.model.add(sc);
		}

		// SAVE
		else
			this.model = model;
		//

	}

	public static void main(String[] args) {
		Editor self = new Editor(null);
		self.pack();
		self.setVisible(true);
	}
}
