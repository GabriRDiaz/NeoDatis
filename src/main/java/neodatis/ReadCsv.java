package neodatis;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import pojo.Departamentos;
import pojo.Empleados;

public class ReadCsv {
	private static ArrayList<Empleados> empleados = new ArrayList<Empleados>();
	private static ArrayList<Departamentos> departamentos = new ArrayList<Departamentos>();
	public static Object[] read() throws IOException{
		BufferedReader br = Files.newBufferedReader(Paths.get("D:\\2DAM\\AAD\\Ejercicios\\NeoDatis\\departamentos.csv"));
		Stream<String> lines = br.lines();
		
		lines.forEach(l -> {
			String[] field = l.split(";");
			System.out.println(Integer.parseInt(field[0]));
			System.out.println(field[1]);
			System.out.println(field[2]);
			departamentos.add(new Departamentos(Integer.parseInt(field[0]), field[1], field[2]));
		});
		
		br = Files.newBufferedReader(Paths.get("D:\\2DAM\\AAD\\Ejercicios\\NeoDatis\\empleados.csv"));
		lines = br.lines();
		
		lines.forEach(l -> {
			String[] field = l.split(";");
			System.out.println(Integer.parseInt(field[0])+"\n"+field[1]+"\n"+field[2]+"\n"+field[3]+"\n"+Float.parseFloat(field[4])+"\n"+Integer.parseInt(field[5]));
			empleados.add(new Empleados(Integer.parseInt(field[0]), field[1], field[2], field[3],Float.parseFloat(field[4]),Integer.parseInt(field[5])));
		});
		lines.close();
		br.close();
		
		Object[] db =new Object[2];
		db[0] = departamentos;
		db[1] = empleados;
		return db;
	}
}
