package neodatis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import pojo.Departamentos;
import pojo.Empleados;

public class Main {
	static Object[] info = new Object[2];
	static ArrayList<Empleados> empleados = new ArrayList<Empleados>();
	static ArrayList<Departamentos> departamentos = new ArrayList<Departamentos>();
	public static void main(String[] args) throws IOException, InterruptedException {
		showMenu();
	}
	private static void showMenu() throws IOException, InterruptedException {
		System.out.println("*--------*");
		System.out.println("|NEODATIS|");
		System.out.println("*--------*");
		System.out.println("1. Guardar CSV");
		System.out.println("2. Visualizar información");
		System.out.println("3. Ejecutar consultas");
		System.out.println("4. Salir");
		System.out.print("Seleccione una opción: ");
		int option = readInfo();
		switch (option) {
		case 1:
			info = ReadCsv.read();
			departamentos = (ArrayList<Departamentos>) info[0];
			empleados = (ArrayList<Empleados>) info[1];
			NeoDatisUtils.saveInfo(departamentos,empleados);
			showMenu();
			break;
		case 2:
			NeoDatisUtils.showInfo();
			showMenu();
			break;
		case 3:
			NeoDatisUtils.makeQueries();
			showMenu();
			break;
		case 4:
			System.out.print("Terminating program");
			for(int i=0;i<3;i++) {
				Thread.sleep(600);
				System.out.print(".");
			}
			System.out.println("\nProgram finished");
			break;
		}
	}
	private static int readInfo() throws IOException, InterruptedException {
		Scanner scan = new Scanner(System.in);
		int option = 0;
		try {
			 option = scan.nextInt();
			if(option<1 || option >4) {
				System.out.println("Valor inválido");
				showMenu();
				}
		}catch(Exception ex) {System.out.println("Valor inválido"); showMenu();}
		return option;
	}
}
