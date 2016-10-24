package com.vmlens.stressTest.setup;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.IOUtils;

import sun.misc.Resource;
import sun.net.www.ParseUtil;

public class StressTestClassLoader extends URLClassLoader {

	private StressTestClassLoader(URL[] urls) {
		super(urls,null);
	}

	public static StressTestClassLoader create() {
		final String s = System.getProperty("java.class.path");
		final File[] path = (s == null) ? new File[0] : getClassPath(s);

		return new StressTestClassLoader(pathToURLs(path));

	}
	
	private static final Map<String,ByteArrayHolder> cache = new ConcurrentHashMap<String,ByteArrayHolder>();
	
	
	
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
	
		
		    
		
		
	
			return super.loadClass(name);
	
	
		
	}
	
	
	
	
	
	
	
	
	protected Class<?> findClass(final String name)
	        throws ClassNotFoundException
	    {
		
		 try{
		
			 
		if( cache.containsKey(name)  )	 
		{
			 byte[] bytes = cache.get(name).getArray();
			 return defineClass(name , bytes , 0 , bytes.length);
		}
			 
			 
			 
		 String path = name.replace('.', '/').concat(".class");
		
		
		 byte[] bytes = IOUtils.toByteArray( this.getResourceAsStream(path));
		
		 
		 cache.put(name , new ByteArrayHolder(bytes));
		
		 return defineClass(name , bytes , 0 , bytes.length);
		
	       
		 }
		 catch(IOException e )
         {
			    throw new ClassNotFoundException(name);
         }
	        
	        
	    }
	
	
	
	
	
	
	
	
	
	
	
	

	
	// Start Taken from http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/6-b27/sun/misc/Launcher.java#Launcher.getFileURL%28java.io.File%29
	
	

	static URL getFileURL(File file) {

		try {

			file = file.getCanonicalFile();

		} catch (IOException e) {
		}

		try {

			return ParseUtil.fileToEncodedURL(file);

		} catch (MalformedURLException e) {

			// Should never happen since we specify the protocol...

			throw new InternalError();

		}

	}

	private static URL[] pathToURLs(File[] path) {

		URL[] urls = new URL[path.length];

		for (int i = 0; i < path.length; i++) {

			urls[i] = getFileURL(path[i]);

		}

		// DEBUG

		// for (int i = 0; i < urls.length; i++) {

		// System.out.println("urls[" + i + "] = " + '"' + urls[i] + '"');

		// }

		return urls;

	}

	private static File[] getClassPath(String cp) {

		File[] path;

		if (cp != null) {

			int count = 0, maxCount = 1;

			int pos = 0, lastPos = 0;

			// Count the number of separators first

			while ((pos = cp.indexOf(File.pathSeparator, lastPos)) != -1) {

				maxCount++;

				lastPos = pos + 1;

			}

			path = new File[maxCount];

			lastPos = pos = 0;

			// Now scan for each path component

			while ((pos = cp.indexOf(File.pathSeparator, lastPos)) != -1) {

				if (pos - lastPos > 0) {

					path[count++] = new File(cp.substring(lastPos, pos));

				} else {

					// empty path component translates to "."

					path[count++] = new File(".");

				}

				lastPos = pos + 1;

			}

			// Make sure we include the last path component

			if (lastPos < cp.length()) {

				path[count++] = new File(cp.substring(lastPos));

			} else {

				path[count++] = new File(".");

			}

			// Trim array to correct size

			if (count != maxCount) {

				File[] tmp = new File[count];

				System.arraycopy(path, 0, tmp, 0, count);

				path = tmp;

			}

		} else {

			path = new File[0];

		}

		// DEBUG

		// for (int i = 0; i < path.length; i++) {

		// System.out.println("path[" + i + "] = " + '"' + path[i] + '"');

		// }

		return path;

	}

}
