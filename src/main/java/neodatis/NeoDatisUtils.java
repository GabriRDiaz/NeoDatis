package neodatis;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.ObjectValues;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import org.neodatis.odb.impl.core.query.values.ValuesCriteriaQuery;

import pojo.Departamentos;
import pojo.Empleados;

import org.neodatis.odb.Objects;
import org.neodatis.odb.Values;
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
		System.out.println("*-------------*");
		System.out.println("|Departamentos|");
		System.out.println("*-------------*");
		System.out.println("*-------------------------------*");
		for(Departamentos deps:dep) {
			System.out.println("Código: "+deps.getCodigoD()+"\nNombre: "
					+deps.getNombreD()+"\nLocalidad: "+deps.getLocalidad()+"\n*-------------------------------*");
		}
		System.out.println("*---------*");
		System.out.println("|Empleados|");
		System.out.println("*---------*");
		System.out.println("*-------------------------------*");
		query = new CriteriaQuery(Empleados.class);
		
		Objects<Empleados> emp = odb.getObjects(query);
		Objects<Departamentos> deptos;
		for(Empleados emps:emp) {
			query = new CriteriaQuery(Departamentos.class, Where.equal("codigoD", emps.getDepartamento()));
			deptos = odb.getObjects(query);
			System.out.println("Código: "+emps.getCodigoE()+"\nNombre: "
					+emps.getNombreE()+"\nApellidos: "+emps.getApellidos()+"\nDepartamento: "+deptos.getFirst().getNombreD()+"\n*-------------------------------*");
		}
		odb.close();
	}
	
	public static void makeQueries() {
		odb = ODBFactory.open("D:\\2DAM\\AAD\\Ejercicios\\NeoDatis\\db.neodatis");
		Objects<Empleados> emp = null;
		Objects<Departamentos> dep = null;
		CriteriaQuery cQuery = null;
		ObjectValues ov = null;
		Values valueGroup = null;
		//Nombre empleados dep 100
		System.out.println("*-------------------------------*");
		System.out.println("|Empleados del departamento 100 |");
		System.out.println("*-------------------------------*");
		query1(emp);
		//Número empleados dep QA
		System.out.println("*------------------------------------*");
		System.out.println("|Número empleados del departamento QA|");
		System.out.println("*------------------------------------*");
		query2(cQuery, valueGroup, ov);
		//Empleados por depto
		System.out.println("*---------------------------------*");
		System.out.println("|Número empleados por departamento|");
		System.out.println("*---------------------------------*");
		query3(dep,cQuery, emp,valueGroup,ov);
		System.out.println("*---------------------------------*");
		
		odb.close();
	}

	private static void query3(Objects<Departamentos> dep, CriteriaQuery cQuery, Objects<Empleados> emp, Values valueGroup, ObjectValues ov) {
		cQuery = new CriteriaQuery(Empleados.class);
		emp = odb.getObjects(cQuery);
		dep = odb.getObjects(new CriteriaQuery(Departamentos.class));
		valueGroup = odb.getValues(new
			ValuesCriteriaQuery(Empleados.class).count("nombreE").groupBy("departamento"));	
		for(Departamentos deps:dep) {
				ov=valueGroup.next();
				System.out.println("Departamento "+deps.getNombreD()+": " +ov.getByIndex(0));
			}
	}

	private static void query2(CriteriaQuery cQuery, Values valueGroup, ObjectValues ov) {
		cQuery = new CriteriaQuery(Empleados.class, Where.equal("departamento",300));
		valueGroup = odb.getValues(new ValuesCriteriaQuery(cQuery).count("nombreE"));
		ov = valueGroup.nextValues();
		BigInteger num = (BigInteger)ov.getByAlias("nombreE");
		System.out.println("Número empleados QA: "+num);
	}

	private static void query1(Objects<Empleados> emp) {
		//Nombre empleados dep 100
		query = new CriteriaQuery(Empleados.class, Where.equal("departamento", 100));
		emp = odb.getObjects(query);
		for(Empleados emps:emp) {
			System.out.println("Nombre del empleado: "+emps.getNombreE());
		}
	}
}
