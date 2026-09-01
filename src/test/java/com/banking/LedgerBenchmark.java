package com.banking;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
public class LedgerBenchmark {

    private Ledger ledger;
    private Transaction transaction;

    @Setup
    public void setUp() {
        ledger = new Ledger();
        // Give ACC-1 a massive balance so it survives millions of test transfers
        ledger.registerAccount(new Account("ACC-1", "Ahmed", 1000000000000000L));
        ledger.registerAccount(new Account("ACC-2", "Alice", 0L));
        // Only transfer 1 cent per test
        transaction = new Transaction("TX-BENCH", "ACC-1", "ACC-2", 1);
    }

    @Benchmark
    public void testProcessTransaction() {
        ledger.processTransaction(transaction);
    }

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(LedgerBenchmark.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}