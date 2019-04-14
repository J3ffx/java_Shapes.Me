package graphics.shapes.ui;

import java.awt.Color;
import java.awt.Point;
import java.io.*;
import java.util.Iterator;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import graphics.shapes.SCircle;
import graphics.shapes.SCollection;
import graphics.shapes.SRectangle;
import graphics.shapes.SText;
import graphics.shapes.Shape;
import graphics.shapes.attributes.ColorAttributes;
import graphics.shapes.attributes.FontAttributes;
import graphics.shapes.attributes.SelectionAttributes;

public class XMLSave {

	private String filename;
	private PrintWriter o;
	private String filepath;

	public XMLSave() {
		this.filename = "MySaveShapes.xml";
		this.filepath = "saves/";
	}

	public void save(SCollection model) {
		try {
			this.filename = JOptionPane.showInputDialog("Please enter file name : ");
			this.o = new PrintWriter(new BufferedOutputStream(new FileOutputStream(filepath + filename + ".xml")), true);
			o.println("<shapes>");
			for (Iterator<Shape> i = model.iterator(); i.hasNext();) {
				Shape shape = (Shape) i.next();
				write(shape);
			}
			o.println("</shapes>");
			o.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write(Shape shape) {
		if (shape instanceof SRectangle) {
			SRectangle rec = (SRectangle) shape;
			ColorAttributes colorAtt = (ColorAttributes) rec.getAttributes(ColorAttributes.id);
			int f = 0;
			int s = 0;
			if (colorAtt.isFilled()) {
				f = colorAtt.getFilledColor().getRGB();
			}
			if (colorAtt.isStroked()) {
				s = colorAtt.getStrokedColor().getRGB();
			}
			o.println("	<rectangle x=\"" + rec.getLoc().x + "\" y=\"" + rec.getLoc().y + "\"" + " height=\""
					+ rec.getRect().height + "\"" + " width=\"" + rec.getRect().width + "\"" + " isfilled=\""
					+ colorAtt.isFilled() + "\"" + " isstroked=\"" + colorAtt.isStroked() + "\"" + " filled=\"" + f
					+ "\"" + " stroked=\"" + s + "\"/>");
		}
		if (shape instanceof SCircle) {
			SCircle cir = (SCircle) shape;
			ColorAttributes colorAtt = (ColorAttributes) cir.getAttributes(ColorAttributes.id);
			int f = 0;
			int s = 0;
			if (colorAtt.isFilled())
				f = colorAtt.getFilledColor().getRGB();
			if (colorAtt.isStroked())
				s = colorAtt.getStrokedColor().getRGB();
			o.println("	<circle x=\"" + cir.getLoc().x + "\" y=\"" + cir.getLoc().y + "\"" + " radius=\""
					+ cir.getRadius() + "\"" + " isfilled=\"" + colorAtt.isFilled() + "\"" + " isstroked=\""
					+ colorAtt.isStroked() + "\"" + " filled=\"" + f + "\"" + " stroked=\"" + s + "\"/>");
		}
		if (shape instanceof SText) {
			SText tex = (SText) shape;
			ColorAttributes colorAtt = (ColorAttributes) tex.getAttributes(ColorAttributes.id);
			FontAttributes fontAtt = (FontAttributes) tex.getAttributes(FontAttributes.id);
			int f = 0;
			int s = 0;
			if (colorAtt.isFilled())
				f = colorAtt.getFilledColor().getRGB();
			if (colorAtt.isStroked())
				s = colorAtt.getStrokedColor().getRGB();
			int t = fontAtt.getFontColor().getRGB();
			o.println("	<text text=\"" + tex.getText() + "\"" + " x=\"" + tex.getLoc().x + "\" y=\"" + tex.getLoc().y
					+ "\"" + " fontSize=\"" + fontAtt.getFontSize() + "\"" + " isfilled=\"" + colorAtt.isFilled() + "\""
					+ " isstroked=\"" + colorAtt.isStroked() + "\"" + " filled=\"" + f + "\"" + " stroked=\"" + s
					+ "\" textC=\"" + t + "\" />");
		}
		if (shape instanceof SCollection) {
			SCollection col = (SCollection) shape;
			o.println("<collection>");
			for (Iterator<Shape> it = col.iterator(); it.hasNext();) {
				Shape realShape = it.next();
				write(realShape);
			}
			o.println("</collection>");
		}
	}

	public void load() {
		SCollection model = new SCollection();
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			final DocumentBuilder builder = factory.newDocumentBuilder();

			this.filename = JOptionPane.showInputDialog("Please enter file name : ");
			
			final Document document = builder.parse(new File(filepath + filename +".xml"));

			final Element root = document.getDocumentElement();

			System.out.println("\n*************ROOT************");
			System.out.println(root.getNodeName());

			final NodeList rootNodes = root.getChildNodes();
			final int nbRootNodes = rootNodes.getLength();

			for (int i = 0; i < nbRootNodes; i++) {
				if (rootNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
					final Element shape = (Element) rootNodes.item(i);
					System.out.println("\n*************SHAPE************");
					model.add(read(shape));
				}
			}
		} catch (final ParserConfigurationException e) {
			e.printStackTrace();
		} catch (final SAXException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		Editor self = new Editor(model);
		self.pack();
		self.setVisible(true);

	}

	public Shape read(Element shape) {
		String type = shape.getNodeName();
		if (type == "rectangle") {
			System.out.println("type : " + type);

			int x = Integer.parseInt(shape.getAttribute("x"));
			System.out.println("x : " + x);
			int y = Integer.parseInt(shape.getAttribute("y"));
			System.out.println("y : " + y);
			int height = Integer.parseInt(shape.getAttribute("height"));
			System.out.println("height : " + height);
			int width = Integer.parseInt(shape.getAttribute("width"));
			System.out.println("width : " + width);
			SRectangle r = new SRectangle(new Point(x, y), width, height);

			boolean isfilled = Boolean.parseBoolean(shape.getAttribute("isfilled"));
			System.out.println("isfilled : " + isfilled);
			boolean isstroked = Boolean.parseBoolean(shape.getAttribute("isstroked"));
			System.out.println("isstroked : " + isstroked);
			Color filled = Color.decode(shape.getAttribute("filled"));
			System.out.println("filled : " + filled);
			Color stroked = Color.decode(shape.getAttribute("stroked"));
			System.out.println("stroked : " + stroked);
			r.addAttributes(new ColorAttributes(isfilled, isstroked, filled, stroked));
			r.addAttributes(new SelectionAttributes());

			return (r);
		}
		if (type == "circle") {
			System.out.println("type : " + type);

			int x = Integer.parseInt(shape.getAttribute("x"));
			System.out.println("x : " + x);
			int y = Integer.parseInt(shape.getAttribute("y"));
			System.out.println("y : " + y);
			int radius = Integer.parseInt(shape.getAttribute("radius"));
			System.out.println("height : " + radius);
			SCircle c = new SCircle(new Point(x, y), radius);

			boolean isfilled = Boolean.parseBoolean(shape.getAttribute("isfilled"));
			System.out.println("isfilled : " + isfilled);
			boolean isstroked = Boolean.parseBoolean(shape.getAttribute("isstroked"));
			System.out.println("isstroked : " + isstroked);
			Color filled = Color.decode(shape.getAttribute("filled"));
			System.out.println("filled : " + filled);
			Color stroked = Color.decode(shape.getAttribute("stroked"));
			System.out.println("stroked : " + stroked);
			c.addAttributes(new ColorAttributes(isfilled, isstroked, filled, stroked));
			c.addAttributes(new SelectionAttributes());

			return (c);
		}
		if (type == "text") {
			System.out.println("type : " + type);

			int x = Integer.parseInt(shape.getAttribute("x"));
			System.out.println("x : " + x);
			int y = Integer.parseInt(shape.getAttribute("y"));
			System.out.println("y : " + y);
			String text = shape.getAttribute("text");
			System.out.println("text : " + text);
			SText t = new SText(new Point(x, y), text);

			int fontSize = Integer.parseInt(shape.getAttribute("fontSize"));
			System.out.println("font size : " + fontSize);
			Color textC = Color.decode(shape.getAttribute("textC"));
			System.out.println("text color : " + textC);
			t.addAttributes(new FontAttributes(fontSize, textC));

			boolean isfilled = Boolean.parseBoolean(shape.getAttribute("isfilled"));
			System.out.println("isfilled : " + isfilled);
			boolean isstroked = Boolean.parseBoolean(shape.getAttribute("isstroked"));
			System.out.println("isstroked : " + isstroked);
			Color filled = Color.decode(shape.getAttribute("filled"));
			System.out.println("filled : " + filled);
			Color stroked = Color.decode(shape.getAttribute("stroked"));
			System.out.println("stroked : " + stroked);
			t.addAttributes(new ColorAttributes(isfilled, isstroked, filled, stroked));
			t.addAttributes(new SelectionAttributes());

			return (t);
		}
		if (type == "collection") {
			System.out.println("type : " + type);
			SCollection col = new SCollection();
			final NodeList colNodes = shape.getChildNodes();
			final int nbColNodes = colNodes.getLength();

			for (int i = 0; i < nbColNodes; i++) {
				if (colNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
					final Element realShape = (Element) colNodes.item(i);
					System.out.println("\n*************COL_SHAPE************");
					col.add(read(realShape));
				}
			}
			return (col);
		}
		return null;
	}
}
