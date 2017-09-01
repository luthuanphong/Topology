package Converter.Coding.Program;

import Converter.Coding.Common.*;

public class SensorSendProgram extends BaseProgram {

    private Variable buffer;

    private Variable queue;

    public SensorSendProgram(Variable buffer, Variable queue) {
        this.buffer = buffer;
        this.queue = queue;
    }

    @Override
    public String getCode() {
        Variable random = new Variable(BaseType.INT,
                "random",
                Function.createFunction("randomInt","1", CommonVariable.SENSOR_MAX_PROCESSINFG_RATE));

        StringBuilder pro = new StringBuilder();
        //Create random variable
        pro.append(random.toString()).append(System.lineSeparator());

        //Check random
        pro.append(Condition.createIFCondition(
                Operator.Compare(buffer.getVariableName(),random.getVariableName(),">="),
                Operator.Minus(buffer.getVariableName(),buffer.getVariableName(),random.getVariableName())));
        pro.append(System.lineSeparator());
        //else
        StringBuilder elseCommand = new StringBuilder();
        elseCommand.append(Operator.AssignValue(random.getVariableName(),buffer.getVariableValue()));
        elseCommand.append(System.lineSeparator());
        elseCommand.append(Operator.AssignValue(buffer.getVariableName(),"0"));
        elseCommand.append(System.lineSeparator());
        pro.append(Condition.createELSECondition(elseCommand.toString()));
        //Increase queue
        pro.append(Operator.Add(queue.getVariableName(),queue.getVariableName(),random.getVariableName()));
        pro.append(System.lineSeparator());
        //Check congestion
        pro.append(Condition.createIFCondition(
                Operator.Compare(queue.getVariableName(),CommonVariable.SENSOR_MAX_QUEUE_SIZE,">"),
                Operator.AssignValue(CommonVariable.CONGESTION,"TRUE")));
        pro.append(System.lineSeparator());
        return pro.toString();
    }
}
