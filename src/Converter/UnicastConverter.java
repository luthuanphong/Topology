package Converter;

import Converter.Channel.BaseChannel;
import Converter.Channel.UnicastChannel;
import Converter.Sensor.BaseSensor;
import Converter.Sensor.SensorFactory;
import Kwsn.Link;
import Kwsn.Sensor;
import Pnml.Pnml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.*;

public class UnicastConverter extends Converter {


    @Override
    public void outputPnmlFile(String folderPath) {

        SensorFactory sensorFactory =  new SensorFactory();
        List<Sensor> sensors = getListSensor();
        List<Link> links = getListChannel();
        List<BaseSensor> baseSensors = new ArrayList<>();
        Pnml pnml = new Pnml();

        if( sensors != null ){
            for ( int i = 0 ; i < sensors.size() ; i++) {
                BaseSensor sensor = sensorFactory.getSensor(pnml,sensors.get(i));
                sensor.convertToPnml();
                baseSensors.add(sensor);
            }
        }

        if ( links != null ) {
            for ( int i = 0 ; i < links.size() ; i++) {
                BaseChannel channel = new UnicastChannel(links.get(i),pnml,baseSensors);
                channel.ConvertToPnml();
            }
        }

        try {
            JAXBContext context = JAXBContext.newInstance(Pnml.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(pnml,new File(folderPath+"temp.pnml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void outputProcessFile(String folderPath) {

    }

    @Override
    public void outputMinimizeProcessFile(String folderPath) {

    }
}
