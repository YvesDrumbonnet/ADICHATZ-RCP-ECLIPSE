package org.adichatz.jpa.recent;

import static org.adichatz.jpa.extra.JPAUtil.getFromJpaBundle;

import java.text.DateFormat;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.AController;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.jpa.wrapper.RecentPreferenceWrapper;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.DecoratingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.nebula.widgets.pgroup.PGroup;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import net.miginfocom.swt.MigLayout;

public class RecentPreferenceColumnViewerToolTipSupport extends ColumnViewerToolTipSupport {
	private static ImageDescriptor PREFERENCE_IMAGE_DESCRIPTOR = AdichatzApplication.getInstance().getFormToolkit()
			.getRegisteredImageDescriptor("IMG_PREFERENCE");

	/** The preference image. */
	private static Image PREFERENCE_IMAGE = PREFERENCE_IMAGE_DESCRIPTOR.createImage();

	/**
	 * Instantiates a new view column viewer tool tip support.
	 *
	 * @param viewer
	 *            the viewer
	 */
	public RecentPreferenceColumnViewerToolTipSupport(ColumnViewer viewer) {
		super(viewer, ToolTip.NO_RECREATE, false);
		viewer.setLabelProvider(new RecentDecoratingStyledCellLabelProvider(new RecentLabelProvider()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ColumnViewerToolTipSupport#createViewerToolTipContentArea(org.eclipse.swt.widgets.Event,
	 * org.eclipse.jface.viewers.ViewerCell, org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Composite createViewerToolTipContentArea(Event event, ViewerCell cell, Composite parent) {
		AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();
		Composite content = toolkit.createComposite(parent);
		content.setLayout(new MigLayout("wrap 1", "grow,fill", "grow,fill"));

		PGroup editorPGroup = toolkit.createPGroup(content, SWT.SMOOTH);
		Object recentElement = cell.getElement();
		editorPGroup.setImage(cell.getImage());
		editorPGroup.setText(cell.getText());
		Composite editorComposite = toolkit.createComposite(editorPGroup);
		editorComposite.setLayout(new MigLayout("wrap 2", "[align right]10[grow,fill]", "grow,fill"));
		if (recentElement instanceof IRecentOpenEditor) {
			String description = RecentUtil.getRecentToolTipText((IRecentOpenEditor) recentElement);
			Label descriptionLBL = toolkit.createLabel(editorComposite, description);
			descriptionLBL.setFont(EngineTools.getModifiedFont(descriptionLBL.getFont(), SWT.BOLD));
		} else if (recentElement instanceof RecentPreferenceWrapper) {
			RecentPreferenceWrapper<?> recentPreference = (RecentPreferenceWrapper<?>) recentElement;
			toolkit.createLabel(editorComposite, getFromJpaBundle("preference.title"));
			Text titleText = toolkit.createText(editorComposite, recentPreference.getPreferenceTree().getTitle());
			titleText.setFont(EngineTools.getModifiedFont(titleText.getFont(), SWT.BOLD));
			toolkit.createLabel(editorComposite, getFromJpaBundle("recent.preference.file.name"));
			Text fileNameText = toolkit.createText(editorComposite, recentPreference.getPreferenceFile().getName());
			fileNameText.setFont(EngineTools.getModifiedFont(fileNameText.getFont(), SWT.BOLD));
			toolkit.createLabel(editorComposite, getFromJpaBundle("recent.editor.last.open"));
			Text updated = toolkit.createText(editorComposite, DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM)
					.format(recentPreference.getUpdated().toGregorianCalendar().getTime()));
			updated.setFont(EngineTools.getModifiedFont(fileNameText.getFont(), SWT.BOLD));
		} else {
			RecentPreferenceWrapper<?> recentPreference = (RecentPreferenceWrapper<?>) recentElement;
			toolkit.createLabel(editorComposite, getFromJpaBundle("preference.title"));
			Text titleText = toolkit.createText(editorComposite, recentPreference.getTitle());
			titleText.setFont(EngineTools.getModifiedFont(titleText.getFont(), SWT.BOLD));
			toolkit.createLabel(editorComposite, getFromJpaBundle("recent.preference.file.name"));
			Text fileNameText = toolkit.createText(editorComposite, recentPreference.getPreferenceFile().getName());
			fileNameText.setFont(EngineTools.getModifiedFont(fileNameText.getFont(), SWT.BOLD));
			toolkit.createLabel(editorComposite, getFromJpaBundle("recent.editor.last.open"));
			Text lasOpenText = toolkit.createText(editorComposite,
					DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM)
							.format(recentPreference.getUpdated().toGregorianCalendar().getTime()));
			lasOpenText.setFont(EngineTools.getModifiedFont(fileNameText.getFont(), SWT.BOLD));
			if (null == recentPreference.getPreferenceTree()) {
				titleText.setForeground(AController.ERROR_COLOR);
				fileNameText.setForeground(AController.ERROR_COLOR);
				lasOpenText.setForeground(AController.ERROR_COLOR);
			}
		}

		return content;
	}

	// ============================================================================================================================================================
	class RecentDecoratingStyledCellLabelProvider extends DecoratingStyledCellLabelProvider {

		/** The label provider. */
		private final RecentLabelProvider labelProvider;

		/**
		 * Instantiates a new view decorating styled cell label provider.
		 *
		 * @param labelProvider
		 *            the label provider
		 */
		private RecentDecoratingStyledCellLabelProvider(RecentLabelProvider labelProvider) {
			super(labelProvider, null, null);
			this.labelProvider = labelProvider;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipDisplayDelayTime(java.lang.Object)
		 */
		@Override
		public int getToolTipDisplayDelayTime(Object object) {
			return 500;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipFont(java.lang.Object)
		 */
		@Override
		public Font getToolTipFont(Object object) {
			if (labelProvider instanceof CellLabelProvider) {
				return ((CellLabelProvider) labelProvider).getToolTipFont(object);
			}
			return super.getToolTipFont(object);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipImage(java.lang.Object)
		 */
		public Image getToolTipImage(Object element) {
			return labelProvider.getImage(element);
		}

	}

	// ============================================================================================================================================================

	/**
	 * The Class RecentLabelProvider.
	 */
	static class RecentLabelProvider extends ColumnLabelProvider implements IStyledLabelProvider {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
		 */
		@Override
		public String getText(Object element) {
			if (element instanceof IRecentOpenEditor)
				return ((IRecentOpenEditor) element).getLabel();
			if (element instanceof RecentPreferenceWrapper)
				return ((RecentPreferenceWrapper<?>) element).getPreferenceTree().getTitle();
			String value = (element instanceof RecentPreferenceSet) ? ((RecentPreferenceSet) element).getQueryURI()
					: (String) element;
			String[] instaceKeys = EngineTools.getInstanceKeys(value);
			return instaceKeys[2].concat(" - ").concat((String) value);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getImage(java.lang.Object)
		 */
		@Override
		public Image getImage(Object element) {
			if (element instanceof IRecentOpenEditor)
				return RecentUtil.getImage(((IRecentOpenEditor) element));
			if (element instanceof RecentPreferenceWrapper)
				return PREFERENCE_IMAGE;
			if (element instanceof RecentPreferenceWrapper || element instanceof RecentPreferenceSet)
				return AdichatzApplication.getInstance().getFormToolkit().getRegisteredImage("IMG_QUERY");
			return null;
		}

		@Override
		public Color getForeground(Object element) {
			if (element instanceof RecentPreferenceWrapper && null == ((RecentPreferenceWrapper<?>) element).getPreferenceTree())
				return AController.ERROR_COLOR;
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider#getStyledText(java.lang.Object)
		 */
		@Override
		public StyledString getStyledText(Object element) {
			return new StyledString(getText(element));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipText(java.lang.Object)
		 */
		@Override
		public String getToolTipText(Object element) {
			return getText(element);
		}

	}

}
