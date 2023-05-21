package dk.dtu.compute.se.pisd.roborally.fileaccess;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;

import java.io.IOException;
import java.io.InputStream;
/**
 * @author Amaan Ahemd, Mohammad Haashir Khan
 */

//IOUtil-klassen indeholder hjælpefunktioner til håndtering af input/output-operationer.
public class IOUtil {

//readString-metoden modtager en InputStream og læser indholdet som en streng.
// Den bruger ByteSource fra Guava-biblioteket til at åbne en strøm til den modtagne InputStream.
// Derefter bruger den asCharSource(Charsets.UTF_8) for at læse strømmen som tekst i UTF-8-kodning
// og returnerer resultatet som en streng. Hvis der opstår en IOException, returneres en tom streng.
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

//readResource-metoden modtager en relativ ressourcesti (relativeResourcePath) og bruger ClassLoader til at hente ressourcen som en InputStream.
// Derefter kalder den readString-metoden med den modtagne InputStream og returnerer resultatet
        public static String readResource(String relativeResourcePath) {
            ClassLoader classLoader = dk.dtu.compute.se.pisd.roborally.fileaccess.IOUtil.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(relativeResourcePath);
            return dk.dtu.compute.se.pisd.roborally.fileaccess.IOUtil.readString(inputStream);
        }

    }

//Disse hjælpefunktioner gør det nemt at læse tekstindhold fra en InputStream eller en ressourcefil i projektet.
