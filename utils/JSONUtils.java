package utils;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class JSONUtils {

	private static boolean checkDir(String dirPath) {
		File dir = new File(dirPath);
		if (dir.exists()) {
			return true;
		} else {
			if (!dir.mkdirs()) {
				dir.mkdir();
				return true;
			}
		}
		return false;
	}

	public static void writeJson(String result, String fileCache) {

		try {
			if (fileCache != null) {
				File f = new File(fileCache);
				checkDir(f.getParent());

					if (f.exists())
						f.delete();

					f.createNewFile();

					FileWriter fw = new FileWriter(f);
	                String strs[] = {result};
	                for (int i = 0; i < strs.length; i++) {
	                	fw.write(strs[i] + "\n");
	                }
	                fw.close();
					Log.e("cacheFile", f.getAbsolutePath());


			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public static JSONArray readJSONArraySD(String file_path) {
		try {

			// File dir = Environment.getExternalStorageDirectory();
			File yourFile = new File(file_path);
			FileInputStream stream = new FileInputStream(yourFile);
			String jString = null;
			try {
				FileChannel fc = stream.getChannel();
				MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
				/* Instead of using default, pass in a decoder. */
				jString = Charset.defaultCharset().decode(bb).toString();
			} finally {
				stream.close();
			}

			JSONArray jsonArray = new JSONArray(jString);
			return jsonArray;
		} catch (Exception e) {
			e.printStackTrace();
			writeJson("[]", file_path);
			return null;
		}

	}
	
	public static String stringJSONArraySD(String file_path) {
		try {

			// File dir = Environment.getExternalStorageDirectory();
			File yourFile = new File(file_path);
			FileInputStream stream = new FileInputStream(yourFile);
			String jString = null;
			try {
				FileChannel fc = stream.getChannel();
				MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
				/* Instead of using default, pass in a decoder. */
				jString = Charset.defaultCharset().decode(bb).toString();
			} finally {
				stream.close();
			}

			return jString;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static JSONObject readJSONObjectSD(String file_path) {
		try {

			// File dir = Environment.getExternalStorageDirectory();
			File yourFile = new File(file_path);
			FileInputStream stream = new FileInputStream(yourFile);
			String jString = null;
			try {
				FileChannel fc = stream.getChannel();
				MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
				/* Instead of using default, pass in a decoder. */
				jString = Charset.defaultCharset().decode(bb).toString();
			} finally {
				stream.close();
			}

			JSONObject jsonObject = new JSONObject(jString);
			return jsonObject;
		} catch (Exception e) {
			e.printStackTrace();
			writeJson("{}", file_path);
			return null;
		}

	}
	
	public static String stringJSONObjectSD(String file_path) {
		try {

			// File dir = Environment.getExternalStorageDirectory();
			File yourFile = new File(file_path);
			FileInputStream stream = new FileInputStream(yourFile);
			String jString = null;
			try {
				FileChannel fc = stream.getChannel();
				MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
				/* Instead of using default, pass in a decoder. */
				jString = Charset.defaultCharset().decode(bb).toString();
			} finally {
				stream.close();
			}

			return jString;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

    public static String loadJSONFromAsset(Context context,String filename) {
        String json = null;
        try {

            InputStream is = context.getAssets().open(filename);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}