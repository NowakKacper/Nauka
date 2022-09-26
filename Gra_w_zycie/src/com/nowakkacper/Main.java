package com.nowakkacper;

import com.nowakkacper.gameframes.GameFrame;

public class Main {

    public static void main(String[] args) {
        //Martwa komórka, która ma dokładnie 3 żywych sąsiadów, staje się żywa w następnej jednostce czasu
        //Żywa komórka z 2 albo 3 żywymi sąsiadami pozostaje nadal żywa; przy innej liczbie sąsiadów umiera

        new GameFrame();
    }
}
