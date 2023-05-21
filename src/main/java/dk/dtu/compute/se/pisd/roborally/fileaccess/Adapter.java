package dk.dtu.compute.se.pisd.roborally.fileaccess;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
/**
 * @author Amaan Ahemd, Mohammad Haashir Khan
 */

//Adapter-klassen implementerer JsonSerializer<E> og JsonDeserializer<E> interfaces.
// Den bruges til at tilpasse serialisering og deserialisering af objekter af typen E ved hjælp af Gson-biblioteket.
public class Adapter<E> implements JsonSerializer<E>, JsonDeserializer<E>{

    //I serialize-metoden bliver objektet src serialiseret til en JsonElement.
    // Først oprettes en JsonObject (retValue), hvor klassenavnet (className)
    // for objektet src tilføjes som en egenskab med nøglen CLASSNAME.
    // Derefter bliver selve objektet serialiseret ved hjælp af context.serialize(src) og tilføjet som en egenskab med nøglen INSTANCE.
    // Til sidst returneres retValue.
    private static final String CLASSNAME = "CLASSNAME";
    private static final String INSTANCE  = "INSTANCE";

    @Override
    //I deserialize-metoden bliver den modtagne JsonElement (json) omdannet til en JsonObject (jsonObject).
    // Derefter bliver værdien for nøglen CLASSNAME hentet ud og gemt i className-variablen.
    // Ved hjælp af Class.forName(className) bliver Class-objektet for den pågældende klasse indlæst.
    // Til sidst bliver INSTANCE-egenskaben deserialiseret ved at kalde context.deserialize(jsonObject.get(INSTANCE), klass).
    public JsonElement serialize(E src, Type typeOfSrc,
                                 JsonSerializationContext context) {

        JsonObject retValue = new JsonObject();
        String className = src.getClass().getName();
        retValue.addProperty(CLASSNAME, className);
        JsonElement elem = context.serialize(src);
        retValue.add(INSTANCE, elem);
        return retValue;
    }

    @Override
    public E deserialize(JsonElement json, Type typeOfT,
                         JsonDeserializationContext context) throws JsonParseException  {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
        String className = prim.getAsString();

        Class<?> klass;
        try {
            klass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new JsonParseException(e.getMessage());
        }
        return context.deserialize(jsonObject.get(INSTANCE), klass);




    }
}