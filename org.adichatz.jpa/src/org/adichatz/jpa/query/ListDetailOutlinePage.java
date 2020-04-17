package org.adichatz.jpa.query;

import static org.adichatz.jpa.extra.JPAUtil.getFromJpaBundle;

import org.adichatz.engine.e4.part.AMultiOutlinePage;
import org.adichatz.engine.e4.part.BoundedPart;
import org.adichatz.jpa.editor.EditorOutlinePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;

import net.miginfocom.swt.MigLayout;

public class ListDetailOutlinePage extends AMultiOutlinePage implements IQueryOutlinePage {
	private QueryOutlinePage queryOutlinePage;

	private EditorOutlinePage editorOutlinePage;

	public ListDetailOutlinePage(BoundedPart boundedPart) {
		super(boundedPart);
		queryOutlinePage = new QueryOutlinePage(boundedPart);
		editorOutlinePage = new EditorOutlinePage(boundedPart, false);
	}

	@Override
	protected void createTabItems() {
		CTabItem tabItem = new CTabItem(pageTabFolder, SWT.NONE);
		tabItem.setText(getFromJpaBundle("outline.query"));
		tabItem.setData(PAGE_SELECTOR, queryOutlinePage);

		tabItem = new CTabItem(pageTabFolder, SWT.NONE);
		tabItem.setText(getFromJpaBundle("outline.editor"));
		tabItem.setData(PAGE_SELECTOR, editorOutlinePage);
		pageTabFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				CTabItem tabItem = pageTabFolder.getSelection();
				if (null == tabItem.getControl()) {
					switch (pageTabFolder.getSelectionIndex()) {
					case 0:
						Composite queryOutlineComposite = toolkit.createComposite(pageTabFolder);
						queryOutlineComposite.setLayout(new MigLayout("wrap, ins 0", "grow,fill", "grow,fill"));
						tabItem.setControl(queryOutlineComposite);
						queryOutlinePage.createControl(queryOutlineComposite);
						break;
					case 1:
						Composite editorOutlineComposite = toolkit.createComposite(pageTabFolder);
						editorOutlineComposite.setLayout(new MigLayout("wrap, ins 0", "grow,fill", "grow,fill"));
						tabItem.setControl(editorOutlineComposite);
						editorOutlinePage.createControl(editorOutlineComposite);
						editorOutlineComposite.layout();
					default:
						break;
					}
					getActiveOutlinePage().refresh();
				}
			}
		});
	}

	public QueryOutlinePage getQueryOutlinePage() {
		return queryOutlinePage;
	}

	public EditorOutlinePage getEditorOutlinePage() {
		return editorOutlinePage;
	}

	@Override
	public QueryToolBindingService getBindingService() {
		return queryOutlinePage.getBindingService();
	}

	@Override
	public QueryToolInput<?> getQueryToolInput() {
		return queryOutlinePage.getQueryToolInput();
	}

	@Override
	public boolean hasError() {
		return queryOutlinePage.hasError();
	}
}
