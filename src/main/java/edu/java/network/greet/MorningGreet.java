package edu.java.network.greet;

import edu.java.network.Greetadle;

public class MorningGreet extends Greetadle {
    @Override
    public String buildResponse(String userName) {
        return "Good Morning " + userName;
    }
}
