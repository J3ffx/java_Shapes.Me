package graphics.shapes.interpret;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.TreeMap;

public class Processor {

	private boolean terminated;
	private TreeMap<String, Command> commands;
	private InputStream in;
	private PrintStream out;
	private Scanner scanner;
	private Object controller;

	public Processor(Object controller) {
		this.commands = new TreeMap<String, Command>();
		this.addCmd(new CommandMenu());
		this.addCmd(new CommandQuit());
		this.in = new BufferedInputStream(System.in);
		this.out = new PrintStream(System.out);
		this.scanner = new Scanner(this.in);
		this.controller = controller;
	}

	public Object getController() {
		return controller;
	}

	public void addCmd(Command c) {
		this.commands.put(c.getName(), c);

	}

	public boolean isTerminated() {
		return this.terminated;
	}

	public String fetch() throws IOException {
		return this.scanner.nextLine();
	}

	public Command decode(String cmdName) throws ProcessorException {
		return this.commands.get(cmdName);
		
	}

	public void execute(Command command) throws FileNotFoundException, IOException, ProcessorException, InputMismatchException {
		command.execute(this);

	}

	public void terminated() {
		this.terminated = true;
	}

	public PrintStream out() {
		return this.out;
	}

	public InputStream in() {
		return this.in();
	}

	public void setIn(InputStream in) {
		this.in = in;
	}

	public void setOut(PrintStream out) {
		this.out = out;
	}

	public Scanner scanner() {
		return this.scanner;
	}

	@Override
	public String toString() {
		return this.commands.keySet().toString();
	}
}
