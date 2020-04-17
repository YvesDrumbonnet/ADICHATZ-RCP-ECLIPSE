package #{adichatz.package.name};

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

public class GlobalEnumType implements UserType, ParameterizedType {
	@SuppressWarnings("rawtypes")
	private Class<? extends Enum> enumClass;

	private HashMap<String, Object> enumMap;

	private HashMap<Object, String> valueMap;

	private int[] sqlTypes;

	@Override
	public void setParameterValues(Properties parameters) {
		String enumClassName = parameters.getProperty("enumClassName");
		try {
			enumClass = Class.forName(enumClassName).asSubclass(Enum.class);
		} catch (ClassNotFoundException cfne) {
			throw new HibernateException("'" + enumClassName + "' enum class not found", cfne);
		}

		try {
			Object[] enumValues = (Object[]) enumClass.getMethod("values").invoke(null);
			enumMap = new HashMap<String, Object>(enumValues.length);
			valueMap = new HashMap<Object, String>(enumValues.length);
			Method m = enumClass.getMethod("getValue");

			for (Object e : enumValues) {

				Object value = m.invoke(e);

				enumMap.put(value.toString(), e);
				valueMap.put(e, value.toString());
			}
		} catch (Exception e) {
			throw new HibernateException("Failed to obtain identifier method", e);
		}

		sqlTypes = new int[] { Types.OTHER };
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
			throws HibernateException, SQLException {
		String value = rs.getString(names[0]);
		if (!rs.wasNull()) {
			return enumMap.get(value);
		}
		return null;
	}

	@Override
	public void nullSafeSet(PreparedStatement ps, Object obj, int index, SharedSessionContractImplementor session)
			throws HibernateException, SQLException {
		if (obj == null) {
			ps.setNull(index, sqlTypes[0]);
		} else {
			ps.setObject(index, valueMap.get(obj), sqlTypes[0]);
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Class returnedClass() {
		return enumClass;
	}

	@Override
	public int[] sqlTypes() {
		return sqlTypes;
	}

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		return x == y;
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}
}
