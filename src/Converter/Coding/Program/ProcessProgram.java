package Converter.Coding.Program;

import Converter.Coding.Common.*;

public class ProcessProgram extends BaseProgram {

    private Variable buffer;
    private Variable queue;

    public ProcessProgram (Variable buffer, Variable queue) {
        this.buffer = buffer;
        this.queue = queue;
    }

    @Override
    public String getCode() {
        Variable random = new Variable(BaseType.INT,
                "random",
                Function.createFunction("randomInt","1", CommonVariable.SENSOR_MAX_PROCESSINFG_RATE));

        StringBuilder pro = new StringBuilder();

        //Check condition
        pro.append(Condition.createIFCondition(
                Operator.Compare(buffer.getVariableValue(),random.getVariableValue(),">="),
                Operator.Minus(buffer.getVariableName(),buffer.getVariableValue(),random.getVariableValue())));
        pro.append(System.lineSeparator());
        //Else
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
