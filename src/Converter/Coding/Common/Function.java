package Converter.Coding.Common;

public class Function {

    /**
     *
     * @param funcName
     * @param parameter1
     * @param parameter2
     * @return
     */
    public static String createFunction (String funcName,String parameter1,String parameter2) {
        StringBuilder fucntion = new StringBuilder();
        fucntion.append(funcName)
                .append("(")
                .append(parameter1)
                .append(", ")
                .append(parameter2)
                .append(")");
        return funcName.toString();
    }
}
