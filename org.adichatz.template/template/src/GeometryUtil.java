package #{adichatz.package.name};

import static org.adichatz.engine.common.LogBroker.logError;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.io.ByteOrderValues;
import com.vividsolutions.jts.io.OutputStreamOutStream;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;
import com.vividsolutions.jts.io.WKBWriter;

public class GeometryUtil {
	public static Geometry getGeometryFromInputStream(Object value) {
		byte[] bufferValue = (byte[]) value;
		InputStream inputStream = new ByteArrayInputStream(bufferValue);

		Geometry dbGeometry = null;

		if (inputStream != null) {

			// convert the stream to a byte[] array
			// so it can be passed to the WKBReader
			byte[] buffer = new byte[255];

			int bytesRead = 0;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					baos.write(buffer, 0, bytesRead);
				}
				inputStream.close();
				byte[] geometryAsBytes = baos.toByteArray();
				baos.close();
				if (geometryAsBytes.length < 5) {
					logError("Invalid geometry inputStream - less than five bytes");
				}

				// first four bytes of the geometry are the SRID,
				// followed by the actual WKB. Determine the SRID
				// here
				byte[] sridBytes = new byte[4];
				System.arraycopy(geometryAsBytes, 0, sridBytes, 0, 4);
				boolean bigEndian = (geometryAsBytes[4] == 0x00);

				int srid = 0;
				if (bigEndian) {
					for (int i = 0; i < sridBytes.length; i++) {
						srid = (srid << 8) + (sridBytes[i] & 0xff);
					}
				} else {
					for (int i = 0; i < sridBytes.length; i++) {
						srid += (sridBytes[i] & 0xff) << (8 * i);
					}
				}

				// use the JTS WKBReader for WKB parsing
				WKBReader wkbReader = new WKBReader();

				// copy the byte array, removing the first four
				// SRID bytes
				byte[] wkb = new byte[geometryAsBytes.length - 4];
				System.arraycopy(geometryAsBytes, 4, wkb, 0, wkb.length);
				dbGeometry = wkbReader.read(wkb);
				dbGeometry.setSRID(srid);
			} catch (IOException | ParseException e) {
				logError(e);
			}

		}

		return dbGeometry;
	}

	public static byte[] getBytesFromCoordinates(double x, double y) {
		byte[] sridInByteArray = new byte[4];
		ByteOrderValues.putInt(0, sridInByteArray, ByteOrderValues.LITTLE_ENDIAN);
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		try {
			outStream.write(sridInByteArray);
			WKBWriter wkbWriter = new WKBWriter(2, ByteOrderValues.LITTLE_ENDIAN);
			wkbWriter.write(new GeometryFactory().createPoint(new Coordinate(x, y)), new OutputStreamOutStream(outStream));
			byte[] bytes = outStream.toByteArray();
			outStream.close();
			return bytes;
		} catch (IOException e) {
			logError(e);
			return null;
		}
	}
}
