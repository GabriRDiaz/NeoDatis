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
	static ODB odb = ODBFactory.open("D:\\2DAM\\AAD\\Ejercicios\\NeoDatis\\db.neodatis");
	static IQuery query;
	public static void saveInfo(ArrayList...values) {
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
		Objects<Departamentos> deptos;
		for(Empleados emps:emp) {
			query = new CriteriaQuery(Departamentos.class, Where.equal("codigoD", emps.getDepartamento()));
			deptos = odb.getObjects(query);
			System.out.println("Código: "+emps.getCodigoE()+"\nNombre: "
					+emps.getNombreE()+"\nApellidos: "+emps.getApellidos()+"\nDepartamento: "+deptos.getFirst().getNombreD()+"\n*-------------------*");
		}
		System.out.println("---------------------------------------");
	}
	
	public static void makeQueries() {
		//Nombre empleados dep 100
		query = new CriteriaQuery(Empleados.class, Where.equal("departamento", 100));
		Objects<Empleados> emp = odb.getObjects(query);
		for(Empleados emps:emp) {
			System.out.println("Nombre del empleado: "+emps.getNombreE());
		}
		//Número empleados dep QA
		CriteriaQuery cQuery = new CriteriaQuery(Empleados.class, Where.equal("departamento",300));
		Values value = odb.getValues(new ValuesCriteriaQuery(cQuery).count("nombreE"));
		ObjectValues ov= value.nextValues();
		BigInteger num = (BigInteger)ov.getByAlias("nombreE");
		System.out.println("Número empleados QA: "+num);
	
		//Empleados por depto
		
		cQuery = new CriteriaQuery(Empleados.class);
		CriteriaQuery cQueryEmp;
		emp = odb.getObjects(cQuery);
		Objects<Departamentos> dep = odb.getObjects(new CriteriaQuery(Departamentos.class));
		Values valores = odb.getValues(new
			ValuesCriteriaQuery(Empleados.class).count("nombreE").groupBy("departamento"));	
		for(Departamentos deps:dep) {
				ov=valores.next();
				System.out.println("Departamento "+deps.getNombreD()+": " +ov.getByIndex(0));
			}
	
	}
}
