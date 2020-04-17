package #{adichatz.package.name};

import org.hibernate.dialect.PostgreSQL95Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.DoubleType;
import org.hibernate.type.ObjectType;

public class PgFullTextDialect extends PostgreSQL95Dialect {

	public PgFullTextDialect() {
		registerFunction("fts", new PgFullTextFunction());
		registerFunction("ts_rank", new StandardSQLFunction("ts_rank", DoubleType.INSTANCE));
		registerFunction("to_tsquery", new StandardSQLFunction("to_tsquery", ObjectType.INSTANCE));
	}

}