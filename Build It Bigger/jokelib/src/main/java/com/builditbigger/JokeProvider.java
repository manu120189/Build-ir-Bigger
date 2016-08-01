package com.builditbigger;

import java.util.ArrayList;

public class JokeProvider implements Joker {
    private ArrayList<String> jokes = new ArrayList<>();
    {
        jokes.add("Joke 1");
        jokes.add("Joke 2");
        jokes.add("Joke 3");
    }

    public String getRandomJoke(){
        return jokes.get(Utils.randInt(0, jokes.size() - 1));
    }
}
