package #{adichatz.package.name};

import java.util.List;

import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.BooleanType;
import org.hibernate.type.Type;

public class PgFullTextFunction implements SQLFunction {
	@Override
	public Type getReturnType(Type columnType, Mapping mapping) throws QueryException {
		return new BooleanType();
	}

	@Override
	public boolean hasArguments() {
		return true;
	}

	@Override
	public boolean hasParenthesesIfNoArguments() {
		return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String render(Type type, List args, SessionFactoryImplementor factory) throws QueryException {
		if (args.size() != 2 && args.size() != 3) {
			throw new IllegalArgumentException("The function must be passed 3 arguments");
		}
		int length = args.size();
		String field = (String) args.get(length - 2);
		String value = (String) args.get(length - 1);
		String language = 3 == length ? (String) args.get(0) : "english";

		return new StringBuffer(field).append(" @@ to_tsquery(").append(language).append(", ").append(value).append(")").toString();
	}
}