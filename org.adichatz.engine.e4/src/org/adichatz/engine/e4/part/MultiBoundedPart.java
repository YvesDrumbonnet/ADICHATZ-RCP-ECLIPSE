package org.adichatz.engine.e4.part;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.e4.resource.PageManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import net.miginfocom.swt.MigLayout;

public class MultiBoundedPart extends BoundedPart {

	protected CTabFolder pagesTabFolder;

	@Override
	public void addPage(PageManager pageManager) {
		ScrolledForm scrolledForm;
		if (hasSeveralPages) {
			if (null == pagesTabFolder)
				addPagesTabFolder();

			CTabItem tabItem = new CTabItem(pagesTabFolder, SWT.NONE);
			tabItem.setData("#pageManager#", pageManager);
			scrolledForm = AdichatzApplication.getInstance().getFormToolkit().createScrolledForm(pagesTabFolder);
			tabItem.setControl(scrolledForm);
			tabItem.setText(pageManager.getTitle());
			tabItem.setImage(pageManager.getImage());
		} else
			scrolledForm = AdichatzApplication.getInstance().getFormToolkit().createScrolledForm(parent);
		pageManager.setManagedForm(new ManagedForm(AdichatzApplication.getInstance().getFormToolkit(), scrolledForm));
		Composite body = scrolledForm.getBody();
		body.setLayout(new MigLayout("wrap, ins 0", "grow,fill", "grow,fill"));

		pageManager.setParent(body);
	}

	protected void addPagesTabFolder() {
		pagesTabFolder = new CTabFolder(parent, SWT.BOTTOM | SWT.BORDER | SWT.FLAT);
		pagesTabFolder.setMaximizeVisible(true);
		pagesTabFolder.setMaximized(false);
		container = pagesTabFolder;
		AdichatzApplication.getInstance().getFormToolkit().adapt(pagesTabFolder);
		pagesTabFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				activatePage((PageManager) pagesTabFolder.getSelection().getData("#pageManager#"));
			}
		});
	}

	@Override
	public void setActivePageManager(PageManager activePageManager) {
		super.setActivePageManager(activePageManager);
		if (null != pagesTabFolder)
			for (CTabItem tabItem : pagesTabFolder.getItems())
				if (activePageManager.equals(tabItem.getData("#pageManager#"))) {
					pagesTabFolder.setSelection(tabItem);
					break;
				}
	}
}
