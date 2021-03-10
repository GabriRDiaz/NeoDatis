package neodatis;

import java.util.ArrayList;
import java.util.stream.Stream;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

import pojo.Departamentos;
import pojo.Empleados;

import org.neodatis.odb.Objects;
public class NeoDatisUtils {
	static ODB odb;
	static IQuery query;
	public static void saveInfo(ArrayList...values) {
		odb = ODBFactory.open("D:\\2DAM\\AAD\\Ejercicios\\NeoDatis\\db.neodatis");
		Stream<ArrayList> stream = Stream.of (values);
		stream.forEach(l->{
			l.forEach(o->{
				odb.store(o);
			});
		});
		odb.commit();
		odb.close();
	}
	
	public static void showInfo() {
		odb = ODBFactory.open("D:\\2DAM\\AAD\\Ejercicios\\NeoDatis\\db.neodatis");
		query = new CriteriaQuery(Departamentos.class);
		Objects<Departamentos> dep = odb.getObjects(query);
		System.out.println("Departamentos");
		System.out.println("---------------------------------------");
		for(Departamentos deps:dep) {
			System.out.println("Código: "+deps.getCodigoD()+"\nNombre: "
					+deps.getNombreD()+"\nLocalidad: "+deps.getLocalidad()+"\n*-------------------*");
		}
		System.out.println("---------------------------------------");
		
		System.out.println("Empleados");
		System.out.println("---------------------------------------");
		query = new CriteriaQuery(Empleados.class);
		
		Objects<Empleados> emp = odb.getObjects(query);
		
		for(Empleados emps:emp) {
			IQuery query = new CriteriaQuery(Departamentos.class, Where.equal("codigoD", emps.getDepartamento()));
			Objects<Departamentos> deptos = odb.getObjects(query);
			System.out.println("Código: "+emps.getCodigoE()+"\nNombre: "
					+emps.getNombreE()+"\nApellidos: "+emps.getApellidos()+"\nDepartamento: "+deptos.getFirst().getNombreD()+"\n*-------------------*");
		}
		System.out.println("---------------------------------------");
	}
	
	public static void makeQueries() {
		
	}
}
