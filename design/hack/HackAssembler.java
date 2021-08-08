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

	private static class Parser {
		private Scanner in;
		private CommandType cmdType;
		private String cmdSymbol;
		private String cmdDest;
		private String cmdJump;
		private String cmdComp;

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
			
			System.out.println("Cmd : " + line);
			
			if (line.startsWith("@")) {
				cmdType = CommandType.A_COMMAND;
				cmdSymbol = line.substring(1);
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

		PrintWriter writer = new PrintWriter(new FileWriter(hackFileName));
		Parser parser = new Parser(new FileInputStream(asmFileName));
		Code code = new Code();
		while (parser.hasMoreCommands()) {
			parser.advance();
			CommandType cmdType = parser.commandType();
			switch (cmdType) {
			case A_COMMAND:
				writer.print("0");
				writer.printf("%015d", Long.parseLong(parser.symbol()));
				break;
			case C_COMMAND:
				writer.print("111");
				writer.printf("%s", code.comp(parser.comp()));
				writer.printf("%s", code.dest(parser.dest()));
				writer.printf("%s", code.jump(parser.jump()));
				break;
			case L_COMMAND:
				break;
			}
			writer.print("\n");
		}
		writer.close();
	}
}