package com.ha.hack.assembler;

import java.util.*;
import java.io.*;

public class HackAssembler {
	private static enum CommandType {
		A_COMMAND, C_COMMAND, L_COMMAND
	}

	private static class Code {
		public String dest(String destMnemonic) {
			switch (destMnemonic) {
			case "null":
				return "000";
			case "M":
				return "001";
			case "D":
				return "010";
			case "MD":
				return "011";
			case "A":
				return "100";
			case "AM":
				return "101";
			case "AD":
				return "110";
			case "AMD":
				return "111";
			default:
				throw new IllegalArgumentException("Not support dest " + destMnemonic);
			}
		}

		public String comp(String compMnemonic) {
			switch (compMnemonic) {
			case "0":
				return "0101010";
			case "1":
				return "0111111";
			case "-1":
				return "0111010";
			case "D":
				return "0001100";
			case "A":
				return "0110000";
			case "!D":
				return "0001101";
			case "!A":
				return "0110001";
			case "-D":
				return "0001111";
			case "-A":
				return "0110011";
			case "D+1":
				return "0011111";
			case "A+1":
				return "0110111";
			case "D-1":
				return "0001110";
			case "A-1":
				return "0110010";
			case "D+A":
				return "0000010";
			case "D-A":
				return "0010011";
			case "A-D":
				return "0000111";
			case "D&A":
				return "0000000";
			case "D|A":
				return "0010101";
			case "M":
				return "1110000";
			case "!M":
				return "1110001";
			case "-M":
				return "1110011";
			case "M+1":
				return "1110111";
			case "M-1":
				return "1110010";
			case "D+M":
				return "1000010";
			case "D-M":
				return "1010011";
			case "M-D":
				return "1000111";
			case "D&M":
				return "1000000";
			case "D|M":
				return "1010101";
			default:
				throw new IllegalArgumentException("Not support comp " + compMnemonic);
			}
		}

		public String jump(String jumpMnemonic) {
			switch (jumpMnemonic) {
			case "null":
				return "000";
			case "JGT":
				return "001";
			case "JEQ":
				return "010";
			case "JGE":
				return "011";
			case "JLT":
				return "100";
			case "JNE":
				return "101";
			case "JLE":
				return "110";
			case "JMP":
				return "111";
			default:
				throw new IllegalArgumentException("Not support jump " + jumpMnemonic);
			}
		}
	}
	
	private static class SymbolTable {
		private Map<String, Integer> internal;
		
		public SymbolTable() {
			internal = new TreeMap<>();
		}
		
		public void addEntry(String symbol, int address) {
			internal.put(symbol, address);
		}
		
		public boolean contains(String symbol) {
			return internal.containsKey(symbol);
		}
		
		public int getAddress(String symbol) {
			if (!contains(symbol)) {
				throw new IllegalArgumentException("Symbol " + symbol + " is not existed.");
			}
			return internal.get(symbol);
		}
		
		public void print() {
			for (String key : internal.keySet()) {
				System.out.printf("Label %s -> %d\n", key, internal.get(key));
			}
		}
	}

	private static class Parser {
		private Scanner in;
		private CommandType cmdType;
		private String cmdSymbol;
		private String cmdDest;
		private String cmdJump;
		private String cmdComp;
		private String cmdText;

		public Parser(FileInputStream asmInStream) {
			in = new Scanner(asmInStream);
		}

		public boolean hasMoreCommands() {
			return in.hasNext();
		}
		
		public void advance() {
			cmdType = null;
			cmdSymbol = null;
			cmdDest = null;
			cmdJump = null;
			cmdComp = null;
			
			String line = null;
			while (in.hasNext() && ((line = in.nextLine().trim()).startsWith("//") || line.equals(""))) {
			};
			
			if (line == null || line.equals("") || line.startsWith("//")) {
				return;
			}
			
			// Remove comment
			line = line.split("//")[0].trim();
			
			cmdText = line;
			
			if (line.startsWith("@")) {
				cmdType = CommandType.A_COMMAND;
				cmdSymbol = line.substring(1);
			} else if (line.startsWith("(")) {
				cmdType = CommandType.L_COMMAND;
				cmdSymbol = line.substring(1, line.length()-1).trim();
			} else {
				cmdType = CommandType.C_COMMAND;
				
				int assignIndex = line.indexOf("=");
				String rest = null;
				if (assignIndex != -1) {
					cmdDest = line.substring(0, assignIndex);
					rest = line.substring(assignIndex+1);
				} else {
					cmdDest = "null";
					rest = line;
				}
				
				int commaIndex = rest.indexOf(";");
				if (commaIndex != -1) {
					cmdComp = rest.substring(0, commaIndex);
					cmdJump = rest.substring(commaIndex+1);
				} else {
					cmdComp = rest;
					cmdJump = "null";
				}
			}
		}

		public CommandType commandType() {
			return cmdType;
		}

		public String symbol() {
			return cmdSymbol;
		}

		public String dest() {
			return cmdDest;
		}

		public String comp() {
			return cmdComp;
		}

		public String jump() {
			return cmdJump;
		}
		
		public String text() {
			return cmdText;
		}
	}
	
	private static void addPredefineRAMAddr(SymbolTable symTbl) {
		symTbl.addEntry("SP", 0);
		symTbl.addEntry("LCL", 1);
		symTbl.addEntry("ARG", 2);
		symTbl.addEntry("THIS", 3);
		symTbl.addEntry("THAT", 4);
		symTbl.addEntry("SCREEN", 16384);
		symTbl.addEntry("KBD", 24576);
		for(int i=0; i<16; i++) {
			symTbl.addEntry("R" + i, i);
		}
	}

	public static void main(String[] argv) throws Exception {
		if (argv.length != 1) {
			System.out.println("Please provide input file");
			return;
		}
		String asmFileName = argv[0];
		String nameWoExt = null;
		int dotIndex = asmFileName.lastIndexOf(".");
		if (dotIndex != -1) {
			nameWoExt = asmFileName.substring(0, dotIndex);
		} else {
			nameWoExt = asmFileName;
		}
		String hackFileName = nameWoExt + ".hack";
		
		SymbolTable symTbl = new SymbolTable();
		addPredefineRAMAddr(symTbl);
		Parser labelParser = new Parser(new FileInputStream(asmFileName));
		int romAddr = 0;
		while (labelParser.hasMoreCommands()) {
			labelParser.advance();
			CommandType cmdType = labelParser.commandType();
			switch (cmdType) {
				case A_COMMAND:
				case C_COMMAND:
					romAddr++;
					break;
				case L_COMMAND:
					symTbl.addEntry(labelParser.symbol(), romAddr);
					break;
			}
		}

		PrintWriter writer = new PrintWriter(new FileWriter(hackFileName));
		Parser parser = new Parser(new FileInputStream(asmFileName));
		Code code = new Code();
		int nextFreeRAMAddr = 16;
		System.out.println("=== Listing ===");
		romAddr = 0;
		while (parser.hasMoreCommands()) {
			parser.advance();
			CommandType cmdType = parser.commandType();
			switch (cmdType) {
			case A_COMMAND:
				writer.print("0");
				Integer ramAddr = null;
				try {
					ramAddr = Integer.parseInt(parser.symbol());
				} catch(Exception ex) {
					if (!symTbl.contains(parser.symbol())) {
						symTbl.addEntry(parser.symbol(), nextFreeRAMAddr);
						nextFreeRAMAddr++;
					}
					ramAddr = symTbl.getAddress(parser.symbol());
				}
				writer.print(String.format("%15s\n", Integer.toBinaryString(ramAddr)).replace(" ", "0"));
				System.out.printf("%05d %s\n", ++romAddr, parser.text());
				break;
			case C_COMMAND:
				writer.print("111");
				writer.printf("%s", code.comp(parser.comp()));
				writer.printf("%s", code.dest(parser.dest()));
				writer.printf("%s", code.jump(parser.jump()));
				writer.print("\n");
				System.out.printf("%05d %s\n", ++romAddr, parser.text());
				break;
			}
		}
		writer.close();
		
		System.out.println("=== Symbol ===");
		symTbl.print();
	}
}