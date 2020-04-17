package org.adichatz.studio.xjc.scenario;

import java.io.IOException;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.generator.EntityMetaModelGenerator;
import org.adichatz.generator.wrapper.ScenarioTreeWrapper;
import org.adichatz.scenario.ScenarioInput;

public class XjcEntityMetaModelGenerator extends EntityMetaModelGenerator {

	public XjcEntityMetaModelGenerator(ScenarioInput scenarioInput) {
		super(scenarioInput);
	}

	protected void setEntitySetClass() {
		entitySetClass = XjcEntitySet.class;
	}

	protected void createClassFile(ScenarioInput scenarioInput) {
		if ("adi://org.adichatz.studio/model/ScenarioTreeMM".equals(entityTree.getEntityURI()))
			beanClass = ScenarioTreeWrapper.class;
		createClassFile(scenarioInput,
				" extends " + getObjectName(AXjcEntityMetaModel.class) + "<" + getObjectName(beanClass) + ">");
	}

	@Override
	protected void addBlocks() throws IOException {
		super.addBlocks();
		String superEntityURI = entityTree.getSuperEntityURI();
		if (!EngineTools.isEmpty(superEntityURI)) {
			classBodyBuffer.append("@Override");
			classBodyBuffer.appendPlus("protected void addSuperFields(){");
			classBodyBuffer.append(getObjectName(AEntityMetaModel.class)
					+ "<?> superEntityMM = pluginEntity.getPluginEntityTree().getEntityMM(\"" + superEntityURI + "\");");
			classBodyBuffer.append("fieldMap.putAll(superEntityMM.getFieldMap());");
			classBodyBuffer.append("super.addSuperFields();");
			classBodyBuffer.appendMinus("}");
		}
	}

	@Override
	protected void postCreate() throws IOException {
		String superEntityURI = entityTree.getSuperEntityURI();
		if (!EngineTools.isEmpty(superEntityURI))
			classBodyBuffer.append("addSuperFields();");
	}

}
