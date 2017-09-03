import Converter.Converter;
import Converter.UnicastConverter;
import Kwsn.KwsnFileReader;

import java.util.HashMap;

public class main {

    public static void main(String []args) {
        KwsnFileReader reader = KwsnFileReader.getInstance();
        HashMap<String,Object> data = reader.readKwsn("");
        Converter converter = new UnicastConverter();
        converter.setTopologyData(data);
        converter.outputPnmlFile("");
        converter.outputProcessFile("");
        converter.outputMinimizeProcessFile("");
    }

}
