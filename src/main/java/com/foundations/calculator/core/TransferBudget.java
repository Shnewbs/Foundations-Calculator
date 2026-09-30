package com.foundations.calculator.core;
/** Tick-scoped packet budget shared by all sides and all cached capability views. */
public final class TransferBudget {
    private long tick=Long.MIN_VALUE,used;
    public long available(long now,long limit){if(now!=tick){tick=now;used=0;}return Math.max(0,limit-used);}
    /** Read-only observation for diagnostics; does not roll or consume the budget. */
    public long remaining(long now,long limit){return Math.max(0,now==tick?limit-used:limit);}
    public void consume(long now,long amount){available(now,Long.MAX_VALUE);used+=Math.min(Math.max(0,amount),Long.MAX_VALUE-used);}
}
