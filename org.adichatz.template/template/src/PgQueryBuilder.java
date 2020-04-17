package #{adichatz.package.name};

import java.util.HashMap;
import java.util.Map;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.jpa.query.JPAQueryBuilder;
import org.adichatz.jpa.query.JPAQueryManager;

public class PgQueryBuilder<T> extends JPAQueryBuilder<T> {

	private Map<String, String> params = new HashMap<>();

	public PgQueryBuilder(JPAQueryManager<T> queryManager, Map<String, String> params) {
		super(queryManager);
		this.params = params;
	}

	@Override
	protected void addFullTextClause() {
		String language = (String) params.get("#LANGUAGE#");
		String fieldName = (String) params.get("#FIELD_NAME#");
		if (!EngineTools.isEmpty(queryManager.getQueryPreferenceManager().getQueryPreference().getFullTextClause())) {
			append("    ", " ");
			appendAndOrWhere();
			querySB.append("fts('").append(language).append("', ").append(fieldName).append(", '")
			        .append(queryManager.getQueryPreferenceManager().getQueryPreference().getFullTextClause()).append("') = true");
		}
	}
}
