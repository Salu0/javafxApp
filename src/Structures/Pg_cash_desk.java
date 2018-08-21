package Structures;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Pg_cash_desk {
    private Integer IDcashDesk;
    private Integer IDbar;
    private String name;
    private String currency;
    private Integer multi_service;
    private String ip_addr;
    private Timestamp last_ping;
    private Integer IDserviceLogged;
    private Integer error_rate;
    private Integer bs_waiting_messages;
    private Integer bs_error_log_lines;
    private Integer gui_error_log_lines;
    private Float db_size;
    private Float home_free_space;
    private Float var_free_space;
    private String barserver_version;
    private String bargui_version;
    private Timestamp gui_started;
    private Float avg_ping;
    private Float avg_icmp_ping;
    private Integer glasspane_rate;
    private Integer is_new;
    private Integer sync_failed;
    private Integer failed_ping_rate;
    private Integer failed_icmp_ping_rate;
    private Integer reset_local_db;
    private Integer can_update;
    private Integer has_live_game;
    private BigDecimal live_game_origin_cash;
    private BigDecimal live_game_origin_chips;
    private Integer import_slot_counters;
    private Integer count_float;
    private Integer IDcashbookForCards;
    private String eet_phone;
    private Integer IDcashDeskEndTransfer;
    private BigDecimal starting_cash;
    private Integer has_exchange;

    public String insertQuery() {
        return "insert into pg_cash_desk (IDcashDesk, IDbar, name, currency, multi_service, ip_addr, last_ping, IDserviceLogged, error_rate, bs_waiting_messages, bs_error_log_lines, gui_error_log_lines, db_size, home_free_space, var_free_space, barserver_version, bargui_version, gui_started, avg_ping, avg_icmp_ping, glasspane_rate, is_new, sync_failed, failed_ping_rate, failed_icmp_ping_rate, reset_local_db, can_update, has_live_game, live_game_origin_cash, live_game_origin_chips, import_slot_counters, count_float, IDcashbookForCards, eet_phone, IDcashDeskEndTransfer, starting_cash, has_exchange)" +
                " values (" + IDcashDesk + ", " + IDbar + ", " + getValidSQLString(name) + ", " + getValidSQLString(currency) + ", " + multi_service + ", " + getValidSQLString(ip_addr) + ", " + getLast_pingAsString() + ", " + IDserviceLogged + ", " + error_rate + ", " + bs_waiting_messages + ", " + bs_error_log_lines + ", " + gui_error_log_lines + ", " + db_size + ", " + home_free_space + ", " + var_free_space + ", " + getValidSQLString(barserver_version) + ", " + getValidSQLString(bargui_version) + ", " + getGui_startedAsString() + ", " + avg_ping + ", " + avg_icmp_ping + ", " + glasspane_rate + ", " + is_new + ", " + sync_failed + ", " + failed_ping_rate + ", " + failed_icmp_ping_rate + ", " + reset_local_db + ", " + can_update + ", " + has_live_game + ", " + live_game_origin_cash + ", " + live_game_origin_chips + ", " + import_slot_counters + ", " + count_float + ", " + IDcashbookForCards + ", " + getValidSQLString(eet_phone) + ", " + IDcashDeskEndTransfer + ", " + starting_cash + ", " + has_exchange + ");";
    }

    private String getGui_startedAsString() {
        if (gui_started == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return getValidSQLString(sdf.format(gui_started));
    }

    private String getLast_pingAsString() {
        if (last_ping == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return getValidSQLString(sdf.format(last_ping));
    }

    private String getValidSQLString(String string) {
        if (string == null) { return null; }
        return "'" + string + "'";
    }
}

