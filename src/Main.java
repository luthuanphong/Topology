import java.util.HashMap;
import Converter.*;
import Kwsn.KwsnFileReader;

/**
 * Created by FredLu on 03/09/2017.
 */
public class Main {

    public static void main(String args[]){
        KwsnFileReader reader = KwsnFileReader.getInstance();
        HashMap<String,Object> data = reader.readKwsn("C:\\Users\\FredLu\\Desktop\\5-sensors.kwsn");
        Converter converter = new UnicastConverter(data);
        converter.outputPnmlFile("C:\\Users\\FredLu\\Desktop\\");
        converter.outputProcessFile("C:\\Users\\FredLu\\Desktop\\");
        converter.outputMinimizeProcessFile("C:\\Users\\FredLu\\Desktop\\");
    }

}
