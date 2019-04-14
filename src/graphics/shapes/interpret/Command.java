package graphics.shapes.interpret;

import java.io.IOException;

public abstract class Command {
	private String name;
	
	public abstract void execute(Processor p) throws IOException, ProcessorException;
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
