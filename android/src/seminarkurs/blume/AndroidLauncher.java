package seminarkurs.blume;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import seminarkurs.blume.MyGdxGame;

public class AndroidLauncher extends AndroidApplication{
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useImmersiveMode = true;
		String hwid_raw = Build.VERSION.RELEASE + Build.BRAND + Build.DEVICE + Build.FINGERPRINT;

		String hwid = "";

		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(hwid_raw.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
			}
			hwid = sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}

		String deviceName = "";
		try {
			BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
			deviceName = myDevice.getName();
		} catch(Exception e) {
			e.printStackTrace();
		}

		initialize(new MyGdxGame(hwid, deviceName), config);
	}

}
