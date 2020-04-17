package org.adichatz.jpa.controller;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.action.ButtonController;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.field.TextController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.listener.AListener;
import org.adichatz.engine.listener.AdiEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.jpa.extra.JPAUtil;
import org.adichatz.jpa.query.JPAQueryManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class FulltextButtonController extends ButtonController {

	private int cursor;

	private char[] characters;

	public FulltextButtonController(String id, ICollectionController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	@Override
	public void synchronize() {
		super.synchronize();
		if (isValid()) {
			ATabularController<?> tabularController = (ATabularController<?>) genCode.getContext().getRootCore()
					.getFromRegister("tableInclude:table");
			JPAQueryManager<?> queryManager = (JPAQueryManager<?>) tabularController.getQueryManager();
			queryManager.getQueryListeners().add(new AListener("#fullText#", IEventType.PRE_QUERY) {
				@Override
				public void handleEvent(AdiEvent event) {
					boolean valid = validFulltextClause();
					if (!valid) {
						queryManager.setQueryValid(false);
						EngineTools.openDialog(MessageDialog.ERROR, JPAUtil.getFromJpaBundle("query.fulltext.invalid.clause"),
								JPAUtil.getFromJpaBundle("query.fulltext.invalid.clause.message"));
					}
				}
			});
			SelectionAdapter fullTextButtonLSTN$0 = new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent event) {
					tabularController.refresh();
				}
			};
			getControl().addSelectionListener(fullTextButtonLSTN$0);
		}
	}

	private boolean validFulltextClause() {
		String fulltextClause = ((TextController) genCode.getFromRegister("fulltextText")).getControl().getText();
		if (!fulltextClause.isEmpty()) {
			cursor = 0;
			characters = fulltextClause.toCharArray();
			boolean findOperator = false;
			while (true) {
				if (cursor >= characters.length - 1)
					break;
				if ('\'' == characters[cursor] && '\'' == characters[++cursor]) {
					if (characters.length < cursor + 2)
						return false;
					int endExpression = fulltextClause.indexOf("''", cursor + 2);
					if (-1 == endExpression)
						return false;
					cursor = endExpression + 2;
					if (cursor == characters.length)
						break;
					findOperator = true;
				}
				if (' ' == characters[cursor])
					findOperator = true;
				if (findOperator) {
					findOperator = false;
					if (!skipSpaces())
						return false;
					if ('&' != characters[cursor] && '|' != characters[cursor])
						return false;
					if (!skipSpaces())
						return false;
				} else
					cursor++;
			}
			return true;
		}
		return true;
	}

	private boolean skipSpaces() {
		while (true) {
			if (++cursor >= characters.length)
				return false;
			if (' ' != characters[cursor])
				return true;
		}
	}
}
