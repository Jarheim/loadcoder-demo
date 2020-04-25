package com.hm;

public class DummyService2 {

    public int logout() {
        try {
            Thread.sleep(900);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
