package com.hm;

import com.loadcoder.load.scenario.Scenario;
import com.loadcoder.load.scenario.design.TypeInstanceBase;

public class ThreadInstance extends TypeInstanceBase {

    final AuthenticationLogic authenticationLogic;

    public ThreadInstance(Scenario theActualScenario, DummyService dummyService, DummyService2 dummyService2) {
        super(theActualScenario);
        authenticationLogic = new AuthenticationLogic(this, dummyService, dummyService2);
    }
}
