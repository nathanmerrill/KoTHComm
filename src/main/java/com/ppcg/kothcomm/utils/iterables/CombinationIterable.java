package com.ppcg.kothcomm.utils.iterables;

public class CombinationIterable<T> extends SubsequenceIterable<T> {

    public CombinationIterable(Iterable<T> iter, int length){
        super(iter, length);
    }

    public CombinationIterable(Iterable<T> iter){
        this(iter, 2);
    }

    protected void nextDigits(int index) {
        int digit = digits.get(index);

        int digitMax = pool.size() - digits.size() + index;
        if (digit >= digitMax) {
            if (index == 0){
                hasNext = false;
                return;
            }
            nextDigits(index - 1);
            digits.set(index, digits.get(index - 1) + 1);
        } else {
            digits.set(index, digit + 1);
        }
    }
}
