package com.septech.centauri.lib;

import java.text.DecimalFormat;

public class Formatting {

    public static DecimalFormat getMoneyDecimalFormat() {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setMinimumFractionDigits(2);
        df.setGroupingUsed(false);

        return df;
    }
}
