package Converter;

import Converter.Channel.BaseChannel;
import Converter.Channel.BroadcastChannel;
import Converter.Channel.UnicastChannel;
import Converter.Sensor.BaseSensor;
import Converter.Sensor.SensorFactory;
import Kwsn.Link;
import Kwsn.Sensor;
import Pnml.Pnml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BroadcastConverter extends Converter {

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
                if (!links.get(i).IsConverted) {
                    BaseChannel channel = new BroadcastChannel(links, links.get(i), pnml, baseSensors);
                    channel.ConvertToPnml();
                }
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
