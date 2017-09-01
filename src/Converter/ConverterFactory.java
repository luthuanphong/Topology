package Converter;

public class ConverterFactory {

    public Converter createConverter (Class c) {
        if (c == BroadcastConverter.class) {
            return new BroadcastConverter();
        } else if ( c == UnicastConverter.class ) {
            return new UnicastConverter();
        }else {
            return null;
        }
    }
}
