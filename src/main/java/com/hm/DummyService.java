package com.hm;

public class DummyService {

    public int login() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return 1;
    }
}
