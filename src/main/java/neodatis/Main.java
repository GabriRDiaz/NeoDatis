package neodatis;

import java.io.IOException;
import java.util.ArrayList;

import pojo.Departamentos;
import pojo.Empleados;

public class Main {
	static Object[] info = new Object[2];
	static ArrayList<Empleados> empleados = new ArrayList<Empleados>();
	static ArrayList<Departamentos> departamentos = new ArrayList<Departamentos>();
	public static void main(String[] args) throws IOException {
		info = ReadCsv.read();
		departamentos = (ArrayList<Departamentos>) info[0];
		empleados = (ArrayList<Empleados>) info[1];
		NeoDatisUtils.saveInfo(departamentos,empleados);
	}
}
