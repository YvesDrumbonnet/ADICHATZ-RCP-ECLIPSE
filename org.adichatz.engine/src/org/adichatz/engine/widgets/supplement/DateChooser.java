package org.adichatz.engine.widgets.supplement;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;

import java.util.Calendar;
import java.util.Date;

import org.adichatz.engine.common.AdichatzApplication;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Shell;

import net.miginfocom.swt.MigLayout;

public class DateChooser {
	private Calendar calendar;

	public void createContents(Control parentControl, int style, Date value, Runnable runnable) {
		final Shell dialog = new Shell(parentControl.getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		dialog.setLayout(new MigLayout("wrap 2"));
		final DateTime dateTime = new DateTime(dialog, SWT.CALENDAR | SWT.BORDER);
		if (null != value) {
			calendar = Calendar.getInstance();
			calendar.setTime(value);
			dateTime.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		}
		dateTime.setLayoutData("spanx 2");
		final DateTime time = new DateTime(dialog, SWT.TIME | SWT.LONG);
		if (0 == (style & SWT.TIME)) {
			time.setVisible(false);
		} else if (null != value) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(value);
			time.setTime(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
		}

		Button ok = new Button(dialog, SWT.IMAGE_BMP);
		ok.setImage(AdichatzApplication.getInstance().getFormToolkit().getRegisteredImage("IMG_ACCEPT"));
		ok.setToolTipText(getFromEngineBundle("field.acceptToolTip"));
		ok.setLayoutData("align right");
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				calendar = Calendar.getInstance();
				calendar.set(Calendar.YEAR, dateTime.getYear());
				calendar.set(Calendar.MONTH, dateTime.getMonth());
				calendar.set(Calendar.DAY_OF_MONTH, dateTime.getDay());

				if (0 != (style & SWT.TIME)) {
					calendar.set(Calendar.HOUR_OF_DAY, time.getHours());
					calendar.set(Calendar.MINUTE, time.getMinutes());
					calendar.set(Calendar.SECOND, time.getSeconds());
				} else {
					calendar.set(Calendar.HOUR_OF_DAY, 0);
					calendar.set(Calendar.MINUTE, 0);
					calendar.set(Calendar.SECOND, 0);
				}
				dialog.close();
				runnable.run();
			}
		});
		dialog.setDefaultButton(ok);
		dialog.pack();

		/*
		 * Compute position if the Dialog window
		 */
		Point toolbarPoint = parentControl.toDisplay(0, 0);
		Rectangle clientArea = parentControl.getDisplay().getClientArea();
		Point newPoint = new Point(Math.min(clientArea.width - 255, toolbarPoint.x - 25),
				Math.min(clientArea.height - 225, toolbarPoint.y + 25));
		dialog.setLocation(newPoint);

		dialog.open();
	}

	public Calendar getCalendar() {
		return calendar;
	}
}
