package com.hm;

import com.loadcoder.load.scenario.design.ScenarioLogicTyped;

public class AuthenticationLogic extends ScenarioLogicTyped<ThreadInstance> {

    private DummyService dummyService;
    private DummyService2 dummyService2;

    public AuthenticationLogic(ThreadInstance type, DummyService dummyService, DummyService2 dummyService2) {
        super(type);
        this.dummyService = dummyService;
        this.dummyService2 = dummyService2;
    }

    void performLogin() {
        load("login", () ->
                dummyService.login())
                .handleResult(resultHandler -> {
                    if (resultHandler.getResponse() == 1) {
                        resultHandler.setStatus(true);
                    }
                })
                .perform();
    }

    void performLogout() {
        load("logout", () ->
                dummyService2.logout())
                .handleResult(resultHandler -> {
                    if (resultHandler.getResponse() == 0) {
                        resultHandler.setStatus(true);
                    }
                })
                .perform();
    }
}
