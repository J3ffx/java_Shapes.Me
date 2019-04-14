package graphics.shapes.interpret;

public class CommandMenu extends Command {

	public CommandMenu() {
		super.setName("menu");
	}

	@Override
	public void execute(Processor p) {
		p.out().println(p.toString());

	}

}
