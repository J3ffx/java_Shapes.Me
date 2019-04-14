package graphics.shapes.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;

	private JMenuBar bar = new JMenuBar();
	private ShapesController c;

	public MenuBar(ShapesController c) {
		this.c = c;
	}

	public JMenuBar bar() {
		
		JMenu file = new JMenu("File");
		
		JMenuItem sav = new JMenuItem("Save");
		sav.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.save();
			}
		});
		file.add(sav);
		
		JMenuItem loa = new JMenuItem("Load");
		loa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.load();
			}
		});
		file.add(loa);
		
		JMenuItem ne = new JMenuItem("New");
		ne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.ne();
			}
		});
		file.add(ne);
		
		JMenuItem com = new JMenuItem("Command");
		com.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.toggleCommand();
			}
		});
		file.add(com);
		
		JMenu edit = new JMenu("Edit");

		JMenuItem del = new JMenuItem("Delete");
		del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.delete();
			}
		});
		edit.add(del);
		
		JMenuItem und = new JMenuItem("Undelete");
		und.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.undo();
			}
		});
		edit.add(und);
		
		edit.addSeparator();
		
		JMenuItem res = new JMenuItem("Resize");
		res.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.resize();
			}
		});
		edit.add(res);
		
		JMenuItem tex = new JMenuItem("Change Text");
		tex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.changeText();
			}
		});
		edit.add(tex);
		
		edit.addSeparator();

		JMenuItem cop = new JMenuItem("Copy");
		cop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.split();
			}
		});
		edit.add(cop);

		JMenuItem pas = new JMenuItem("Paste");
		pas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.join();
			}
		});
		edit.add(pas);
		
		edit.addSeparator();

		JMenuItem spl = new JMenuItem("Split");
		spl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.split();
			}
		});
		edit.add(spl);

		JMenuItem joi = new JMenuItem("Join");
		joi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.join();
			}
		});
		edit.add(joi);

		JMenu newShape = new JMenu("New Shape");
		
		MouseEvent e = new MouseEvent(this.bar, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, 100, 100, 1, false);

		JMenuItem rec = new JMenuItem("Rectangle");
		rec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.rectangle(e);
			}
		});
		newShape.add(rec);

		JMenuItem cir = new JMenuItem("Circle");
		cir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.circle(e);
			}
		});
		newShape.add(cir);

		JMenuItem txt = new JMenuItem("Text");
		txt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String askText = JOptionPane.showInputDialog("Please enter text : ");
				;
				c.text(e, askText);
			}
		});
		newShape.add(txt);

		JMenu changeColor = new JMenu("Change Color");

		JMenu fil = new JMenu("Filled");

		JMenuItem blackF = new JMenuItem("Black");
		blackF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.filled(Color.black);
			}
		});
		fil.add(blackF);

		JMenuItem blueF = new JMenuItem("Blue");
		blueF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.filled(Color.blue);
			}
		});
		fil.add(blueF);

		JMenuItem grayF = new JMenuItem("Gray");
		grayF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.filled(Color.gray);
			}
		});
		fil.add(grayF);

		JMenuItem greenF = new JMenuItem("Green");
		greenF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.filled(Color.green);
			}
		});
		fil.add(greenF);

		JMenuItem orangeF = new JMenuItem("Orange");
		orangeF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.filled(new Color(255, 165, 0));
			}
		});
		fil.add(orangeF);

		JMenuItem pinkF = new JMenuItem("Pink");
		pinkF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.filled(Color.pink);
			}
		});
		fil.add(pinkF);

		JMenuItem redF = new JMenuItem("Red");
		redF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.filled(Color.red);
			}
		});
		fil.add(redF);

		JMenuItem yellowF = new JMenuItem("Yellow");
		yellowF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.filled(Color.yellow);
			}
		});
		fil.add(yellowF);

		JMenuItem noneF = new JMenuItem("None");
		noneF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.none("filled");
			}
		});
		fil.add(noneF);

		changeColor.add(fil);


		JMenu str = new JMenu("Stroked");

		JMenuItem blackS = new JMenuItem("Black");
		blackS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.stroked(Color.black);
			}
		});
		str.add(blackS);

		JMenuItem blueS = new JMenuItem("Blue");
		blueS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.stroked(Color.blue);
			}
		});
		str.add(blueS);

		JMenuItem grayS = new JMenuItem("Gray");
		grayS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.stroked(Color.gray);
			}
		});
		str.add(grayS);

		JMenuItem greenS = new JMenuItem("Green");
		greenS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.stroked(Color.green);
			}
		});
		str.add(greenS);

		JMenuItem orangeS = new JMenuItem("Orange");
		orangeS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.stroked(new Color(255, 165, 0));
			}
		});
		str.add(orangeS);

		JMenuItem pinkS = new JMenuItem("Pink");
		pinkS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.stroked(Color.pink);
			}
		});
		str.add(pinkS);

		JMenuItem redS = new JMenuItem("Red");
		redS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.stroked(Color.red);
			}
		});
		str.add(redS);

		JMenuItem yellowS = new JMenuItem("Yellow");
		yellowS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.stroked(Color.yellow);
			}
		});
		str.add(yellowS);

		JMenuItem noneS = new JMenuItem("None");
		noneS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.none("stroked");
			}
		});
		str.add(noneS);

		changeColor.add(str);

		JMenu text = new JMenu("Text");

		JMenuItem blackT = new JMenuItem("Black");
		blackT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.textC(Color.black);
			}
		});
		text.add(blackT);

		JMenuItem blueT = new JMenuItem("Blue");
		blueT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.textC(Color.blue);
			}
		});
		text.add(blueT);

		JMenuItem grayT = new JMenuItem("Gray");
		grayT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.textC(Color.gray);
			}
		});
		text.add(grayT);

		JMenuItem greenT = new JMenuItem("Green");
		greenT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.textC(Color.green);
			}
		});
		text.add(greenT);

		JMenuItem orangeT = new JMenuItem("Orange");
		orangeT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.textC(new Color(255, 165, 0));
			}
		});
		text.add(orangeT);

		JMenuItem pinkT = new JMenuItem("Pink");
		pinkT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.textC(Color.pink);
			}
		});
		text.add(pinkT);

		JMenuItem redT = new JMenuItem("Red");
		redT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.textC(Color.red);
			}
		});
		text.add(redT);

		JMenuItem yellowT = new JMenuItem("Yellow");
		yellowT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				c.textC(Color.yellow);
			}
		});
		text.add(yellowT);

		changeColor.add(text);
		
		JMenu blank1 = new JMenu(" ");
		JMenu blank2 = new JMenu(" ");
		JMenu blank3 = new JMenu(" ");
		
		this.bar.add(file);
		this.bar.add(blank1);
		this.bar.add(edit);
		this.bar.add(blank2);
		this.bar.add(newShape);
		this.bar.add(blank3);
		this.bar.add(changeColor);
		return this.bar;
	}
}