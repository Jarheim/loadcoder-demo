package com.hm;

import com.loadcoder.load.chart.logic.ResultChart;
import com.loadcoder.load.chart.logic.RuntimeChart;
import com.loadcoder.load.scenario.ExecutionBuilder;
import com.loadcoder.load.scenario.Load;
import com.loadcoder.load.scenario.LoadBuilder;
import com.loadcoder.load.scenario.LoadScenarioTyped;
import com.loadcoder.result.Result;
import com.loadcoder.statics.SummaryUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static com.loadcoder.statics.LogbackLogging.getNewLogDir;
import static com.loadcoder.statics.LogbackLogging.setResultDestination;
import static com.loadcoder.statics.StopDecisions.duration;
import static com.loadcoder.statics.Time.SECOND;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthPerformanceTest {

    private DummyService dummyService = new DummyService();
    private DummyService2 dummyService2 = new DummyService2();

    @BeforeAll
    void setup() {
        System.setProperty("java.awt.headless", "false");
    }

    @Test
    void loginLogout() {
        setResultDestination(getNewLogDir("target", "AuthenticationPerformance"));

        LoadScenarioTyped<ThreadInstance> loadScenario = new LoadScenarioTyped<>() {

            @Override
            public void loadScenario(ThreadInstance t) {
                t.authenticationLogic.performLogin();
                t.authenticationLogic.performLogout();
            }

            @Override
            public ThreadInstance createInstance() {
                return new ThreadInstance(this, dummyService, dummyService2);
            }
        };

        Load load = new LoadBuilder(loadScenario).amountOfThreads(10).rampup(10 * SECOND)
                .stopDecision(duration(20 * SECOND)).build();

        RuntimeChart runtimeChart = new RuntimeChart();

        Result result = new ExecutionBuilder(load).storeAndConsumeResultRuntime(runtimeChart).build().execute()
                .andWait().getReportedResultFromResultFile();

        ResultChart resultChart = new ResultChart(result);
        SummaryUtils.printSimpleSummary(result, "Add Product To Cart - Performance");

        resultChart.waitUntilClosed();
        runtimeChart.waitUntilClosed();

    }
}
