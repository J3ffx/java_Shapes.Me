package graphics.shapes.ui;

import java.awt.Graphics;

import graphics.shapes.SCollection;
import graphics.shapes.Shape;
import graphics.ui.Controller;
import graphics.ui.View;

public class ShapesView extends View {

	private ShapeDraftman draftman;

	private static final long serialVersionUID = 1;

	public ShapesView(Shape model) {
		super(model);
	}

	public boolean isFocusTraversable() {
		return true;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		this.draftman = new ShapeDraftman(g);
		((SCollection)super.getModel()).accept(draftman);

	}

	@Override
	public Controller defaultController(Object model) {
		return new ShapesController((SCollection) model);
	}

}