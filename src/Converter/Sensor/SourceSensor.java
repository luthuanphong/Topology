package Converter.Sensor;

import Converter.Coding.Common.BaseType;
import Converter.Coding.Common.Variable;
import Kwsn.Sensor;
import Pnml.Pnml;
import Pnml.Place;
import Pnml.Transition;
import Pnml.Arc;
import Pnml.ArcDirection;

public class SourceSensor extends BaseSensor {

    public SourceSensor (Pnml pnml, Sensor sensor) {
        //Initialize all information
        this.pnml = pnml;
        this.sensor = sensor;
        this.sensor.pnmlSensor = this;
        this.buffer = new Variable(BaseType.INT,"Buffer_"+this.sensor.Id,"0");
        this.queue = new Variable(BaseType.INT,"Queue_"+this.sensor.Id,"0");
        this.Type = SensorType.SOURCE;
    }

    /**
     * Convert sensor data to pnml
     */
    @Override
    public void convertToPnml() {

        this.InputPlace = new Place();
        this.InputPlace.id = "src_in_"+this.sensor.Id;
        this.InputPlace.token = 1;
        this.InputPlace.label = "Source input place";

        this.IntermediatePlace = new Place();
        this.IntermediatePlace.id = "src_int_"+this.sensor.Id;
        this.IntermediatePlace.label = "Source intermediate place";

        this.OutputPlace = new Place();
        this.OutputPlace.id = "src_out_"+this.sensor.Id;
        this.OutputPlace.label = "Source ouput place";

        Transition sourceGenerate = new Transition();
        sourceGenerate.id = "source_gen";
        sourceGenerate.label = "Source generate";

        Transition sourceSend = new Transition();
        sourceSend.id = "source_send";
        sourceSend.label = "Source send";

        Arc input_generate = new Arc();
        input_generate.id = "src_gen_place_to_transition";
        input_generate.direction = ArcDirection.PLACE_TO_TRANSITION;
        input_generate.place = this.InputPlace.id;
        input_generate.transition = sourceGenerate.id;
        input_generate.weight = 1;

        Arc generate_int = new Arc();
        generate_int.id = "gen_int_transition_to_place";
        generate_int.direction = ArcDirection.TRANSITION_TO_PLACE;
        generate_int.place = this.IntermediatePlace.id;
        generate_int.transition = sourceGenerate.id;
        generate_int.weight = 1;

        Arc int_send = new Arc();
        int_send.id = "int_send_place_to_transition";
        int_send.direction = ArcDirection.PLACE_TO_TRANSITION;
        int_send.place = this.IntermediatePlace.id;
        int_send.transition = sourceSend.id;
        int_send.weight = 1;

        Arc send_out = new Arc();
        send_out.id = "send_out_transition_to_place";
        send_out.direction = ArcDirection.TRANSITION_TO_PLACE;
        send_out.place = this.OutputPlace.id;
        send_out.transition = sourceSend.id;
        send_out.weight = 1;

        this.pnml.net.places.add(this.InputPlace);
        this.pnml.net.places.add(this.IntermediatePlace);
        this.pnml.net.places.add(this.OutputPlace);

        this.pnml.net.transitions.add(sourceGenerate);
        this.pnml.net.transitions.add(sourceSend);

        this.pnml.net.arcs.add(input_generate);
        this.pnml.net.arcs.add(generate_int);
        this.pnml.net.arcs.add(int_send);
        this.pnml.net.arcs.add(send_out);
    }
}
