package dk.dtu.compute.se.pisd.roborally.fileaccess;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;

import java.io.IOException;
import java.io.InputStream;
/**
 * @author Amaan Ahemd, Mohammad Haashir Khan
 */
    public class IOUtil {


        public static String readString(InputStream inputStream) {

            ByteSource byteSource = new ByteSource() {
                @Override
                public InputStream openStream() throws IOException {
                    return inputStream;
                }
            };

            try {
                return byteSource.asCharSource(Charsets.UTF_8).read();
            } catch (IOException e) {
                return "";
            }
        }


        public static String readResource(String relativeResourcePath) {
            ClassLoader classLoader = dk.dtu.compute.se.pisd.roborally.fileaccess.IOUtil.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(relativeResourcePath);
            return dk.dtu.compute.se.pisd.roborally.fileaccess.IOUtil.readString(inputStream);
        }

    }


