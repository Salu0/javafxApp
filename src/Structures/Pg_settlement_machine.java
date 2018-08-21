package Structures;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pg_settlement_machine {

    private Integer IDrecord;
    private Integer IDprevRecord;
    private Integer IDsettlement;
    private Integer IDstation;
    private Date date;
    private BigDecimal current_in;
    private BigDecimal current_out;
    private BigDecimal current_bet;
    private BigDecimal current_win;
    private BigDecimal current_game;
    private BigDecimal current_ticket_in;
    private BigDecimal current_ticket_out;
    private BigDecimal current_promo_in;
    private BigDecimal current_promo_out;
    private BigDecimal current_jackpot_out;
    private BigDecimal old_in;
    private BigDecimal old_out;
    private BigDecimal new_in;
    private BigDecimal new_out;
    private BigDecimal total_in;
    private BigDecimal total_out;
    private BigDecimal total_ticket_in;
    private BigDecimal total_ticket_out;
    private BigDecimal total_promo_in;
    private BigDecimal total_promo_out;
    private BigDecimal total_jackpot_out;
    private BigDecimal total_bet;
    private BigDecimal total_win;
    private BigDecimal total_game;
    private BigDecimal payoff;
    private BigDecimal missing_payoff;
    private Integer manualEntry;
    private Timestamp manualInserted;
    private Integer IDuser;

    public String insertQuery() {
        return "insert into pg_settlement_machine (IDrecord, IDprevRecord, IDsettlement, IDstation, date, current_in, current_out, current_bet, current_win, current_game, current_ticket_in, current_ticket_out, current_promo_in, current_promo_out, current_jackpot_out, old_in, old_out, new_in, new_out, total_in, total_out, total_ticket_in, total_ticket_out, total_promo_in, total_promo_out, total_jackpot_out, total_bet, total_win, total_game, payoff, missing_payoff, manualEntry, manualInserted, IDuser)" +
                " values (" + IDrecord + ", " + IDprevRecord + ", " + IDsettlement + ", " + IDstation + ", " + getDateAsString() + ", " + current_in + ", " + current_out + ", " + current_bet + ", " + current_win + ", " + current_game + ", " + current_ticket_in + ", " + current_ticket_out + ", " +  current_promo_in + ", " + current_promo_out + ", " + current_jackpot_out + ", " + old_in + ", " + old_out + ", " + new_in + ", " + new_out + ", " + total_in + ", " + total_out + ", " + total_ticket_in + ", " + total_ticket_out + ", " + total_promo_in + ", " + total_promo_out + ", " + total_jackpot_out + ", " + total_bet + ", " + total_win + ", " + total_game + ", " + payoff + ", " + missing_payoff + ", " + manualEntry + ", " + getManualInsertedAsString() + ", " + IDuser + ");";
    }

    private String getManualInsertedAsString() {
        if (manualInserted == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return getValidSQLString(sdf.format(manualInserted));
    }

    private String getDateAsString() {
        if (date == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return getValidSQLString(sdf.format(date));
    }

    private String getValidSQLString(String string) {
        if (string == null) { return null; }
        return "'" + string + "'";
    }
}

