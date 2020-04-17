package org.adichatz.generator;

import java.io.IOException;

import org.adichatz.engine.data.AEntity;
import org.adichatz.engine.validation.ErrorPath;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.wrapper.QueryPreferenceWrapper;
import org.adichatz.jpa.editor.EditorOutlineInput;
import org.adichatz.jpa.query.JPAQueryManager;
import org.adichatz.jpa.query.QueryToolInput;
import org.adichatz.jpa.wrapper.ControllerPreferenceWrapper;
import org.adichatz.jpa.wrapper.QueryPaginationWrapper;
import org.adichatz.jpa.wrapper.QueryParameterWrapper;
import org.adichatz.jpa.wrapper.RecentPreferenceWrapper;
import org.adichatz.jpa.xjc.ColumnPreferenceType;
import org.adichatz.jpa.xjc.FilterType;
import org.adichatz.jpa.xjc.QueryOpenClauseType;
import org.adichatz.scenario.ScenarioInput;
import org.adichatz.scenario.ScenarioResources;

public class JPAPluginEntityTreeGenerator extends PluginEntityTreeGenerator {
	public JPAPluginEntityTreeGenerator(ScenarioInput scenarioInput) {
		super(scenarioInput);
	}

	@Override
	protected void addon(ScenarioResources modelSR, PluginEntityWrapper pluginEntity, String entityName) throws IOException {
		String entityClassName = null;
		if ("adi://org.adichatz.jpa/query.model/ColumnPreferenceMM".equals(pluginEntity.getEntityURI()))
			entityClassName = ColumnPreferenceType.class.getName();
		if ("adi://org.adichatz.jpa/query.model/QueryPreferenceMM".equals(pluginEntity.getEntityURI()))
			entityClassName = QueryPreferenceWrapper.class.getName();
		if ("adi://org.adichatz.jpa/query.model/ControllerPreferenceMM".equals(pluginEntity.getEntityURI()))
			entityClassName = ControllerPreferenceWrapper.class.getName();
		if ("adi://org.adichatz.jpa/query.model/RecentPreferenceMM".equals(pluginEntity.getEntityURI()))
			entityClassName = RecentPreferenceWrapper.class.getName();
		if ("adi://org.adichatz.jpa/query.model/QueryToolInputMM".equals(pluginEntity.getEntityURI()))
			entityClassName = QueryToolInput.class.getName();
		if ("adi://org.adichatz.jpa/query.model/QueryManagerMM".equals(pluginEntity.getEntityURI()))
			entityClassName = JPAQueryManager.class.getName();
		if ("adi://org.adichatz.jpa/query.model/ParameterMM".equals(pluginEntity.getEntityURI()))
			entityClassName = QueryParameterWrapper.class.getName();
		if ("adi://org.adichatz.jpa/query.model/PaginationMM".equals(pluginEntity.getEntityURI()))
			entityClassName = QueryPaginationWrapper.class.getName();
		if ("adi://org.adichatz.jpa/query.model/OpenClauseMM".equals(pluginEntity.getEntityURI()))
			entityClassName = QueryOpenClauseType.class.getName();
		if ("adi://org.adichatz.jpa/query.model/ViewerFilterMM".equals(pluginEntity.getEntityURI()))
			entityClassName = FilterType.class.getName();
		if ("adi://org.adichatz.jpa/jpa/EditorOutlineInputMM".equals(pluginEntity.getEntityURI()))
			entityClassName = EditorOutlineInput.class.getName();
		if ("adi://org.adichatz.jpa/jpa/EntityMM".equals(pluginEntity.getEntityURI()))
			entityClassName = AEntity.class.getName();
		if ("adi://org.adichatz.jpa/jpa/ErrorPathMM".equals(pluginEntity.getEntityURI()))
			entityClassName = ErrorPath.class.getName();
		classBodyBuffer.append("pluginEntityClassMap.put(\"" + entityClassName + "\", " + entityName + ");");
	}

}
