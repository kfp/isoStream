package sand.sperlazzasaurus.com.isoStream.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

/**
 * configuration class
 * @author elliott
 *
 */
@Configuration
public class AppConfig {

	
	static{
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
		
		System.out.println("loaded first part.");
		
		NativeLibrary.addSearchPath(
			RuntimeUtil.getLibVlcLibraryName(), "/usr/lib"
		);
	    Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
	    System.out.println("Loaded second part.");
	}
	
	private static final String APP_PROP_LOCATION = "/var/isoStream/isoStream.properties";
	
	@Bean(name = "appProps")
	public Properties getAppProps() throws IOException{
		Properties props = new Properties();
		props.load(new FileInputStream(APP_PROP_LOCATION));
		
		return props;
	}
}
