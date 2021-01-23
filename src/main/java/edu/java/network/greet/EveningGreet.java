package edu.java.network.greet;

import edu.java.network.Greetadle;

public class EveningGreet extends Greetadle {
    @Override
    public String buildResponse(String userName) {
        return "Good evening " + userName;
    }
}
