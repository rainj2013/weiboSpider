package io.coding.rainj2013;
import org.nutz.dao.impl.NutDao;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.combo.ComboIocLoader;
import org.nutz.lang.Lang;


public class Appcontext {
	
	private static NutDao dao;
	private static Ioc ioc;
	
	public static NutDao getDao() {
		if (dao == null) {
			dao = getIoc().get(NutDao.class, "dao");
		}
		return dao;
	}
	
	public static Ioc getIoc() {
		if (ioc == null)
			try {
				ioc = new NutIoc(new ComboIocLoader(
						"*org.nutz.ioc.loader.json.JsonLoader", "dao.js",
						"*org.nutz.ioc.loader.annotation.AnnotationIocLoader",
						"io.coding.rainj2013"));
			} catch (ClassNotFoundException e) {
				throw Lang.wrapThrow(e);
			}
		return ioc;
	}
}
