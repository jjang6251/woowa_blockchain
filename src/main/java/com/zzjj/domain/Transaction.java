package com.zzjj.domain;

import java.util.UUID;

public class Transaction {
    private final String id;
    private final String from;
    private final String to;
    private final double amount;
    private final String signature;

    public Transaction(String from, String to, double amount, String signature) {
        this.id = UUID.randomUUID().toString();
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.signature = signature;
    }

    public String getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public double getAmount() {
        return amount;
    }

    public String getSignature() {
        return signature;
    }

    @Override
    public String toString() {
        return String.format("Tx[id=%s, %s->%s : %.2f]", id, from, to, amount);
    }

    public String compactRepresentation() {
        return id + ":" + from + ":" + to + ":" + amount;
    }
}
