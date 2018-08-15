package framework;

public class BoolFunctions {

    public static boolean checkExpressionOnBoolValue(Boolean bool){
        if(bool){
            bool = Boolean.TRUE;
        }else{
            bool = Boolean.FALSE;
        }
        return bool;
    }
}
