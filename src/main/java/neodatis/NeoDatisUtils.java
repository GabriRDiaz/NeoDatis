package neodatis;

import java.util.ArrayList;
import java.util.stream.Stream;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;

public class NeoDatisUtils {
	static ODB odb;
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
		
	}
}
