package structures;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PgBar {
    private Integer IDbar;
    private Integer IDcompany;
    private Integer IDcashbookForCards;
    private Integer IDcashbookForMachines;
    private Integer IDliveGameCashBook;
    private Integer IDliveGameBM;
    private Integer IDliveGameSafeCashBook;
    private Integer IDmaintenanceGroup;
    private String name;
    private String street;
    private String city;
    private String zip;
    private String eet_company_name;
    private String eet_dic;
    private Integer eet_id_provoz;
    private Integer eet_testing_mode;
    private String eet_service_alias;
    private Integer eet_send_points;
    private String phone;
    private String email;
    private Integer send_sms;
    private Integer send_info_sms;
    private Integer has_tito;
    private String info_sms_phone;
    private BigDecimal info_sms_payout_limit;
    private BigDecimal bonus_new_player;
    private BigDecimal max_hour_loss;
    private Integer auto_gb_login;
    private Date date_start;
    private BigDecimal live_game_card_provision;
    private String live_game_company;
    private String profit_division_type;
    private Integer profit_division_percent;
    private BigDecimal profit_division_fix;
    private Timestamp last_action;
    private Integer guestbook_sheet_size;
    private Integer disable_minute_points;
    private Integer disable_profit_points;
    private Integer IDdivision;
    private Timestamp deleted;

    public PgBar(Integer IDbar, Integer IDcompany, Integer IDcashbookForCards, Integer IDcashbookForMachines, Integer IDliveGameCashBook, Integer IDliveGameBM, Integer IDliveGameSafeCashBook, Integer IDmaintenanceGroup, String name, String street, String city, String zip, String eet_company_name, String eet_dic, Integer eet_id_provoz, Integer eet_testing_mode, String eet_service_alias, Integer eet_send_points, String phone, String email, Integer send_sms, Integer send_info_sms, Integer has_tito, String info_sms_phone, BigDecimal info_sms_payout_limit, BigDecimal bonus_new_player, BigDecimal max_hour_loss, Integer auto_gb_login, Date date_start, BigDecimal live_game_card_provision, String live_game_company, String profit_division_type, Integer profit_division_percent, BigDecimal profit_division_fix, Timestamp last_action, Integer guestbook_sheet_size, Integer disable_minute_points, Integer disable_profit_points, Integer IDdivision, Timestamp deleted) {
        this.IDbar = IDbar;
        this.IDcompany = IDcompany;
        this.IDcashbookForCards = IDcashbookForCards;
        this.IDcashbookForMachines = IDcashbookForMachines;
        this.IDliveGameCashBook = IDliveGameCashBook;
        this.IDliveGameBM = IDliveGameBM;
        this.IDliveGameSafeCashBook = IDliveGameSafeCashBook;
        this.IDmaintenanceGroup = IDmaintenanceGroup;
        this.name = name;
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.eet_company_name = eet_company_name;
        this.eet_dic = eet_dic;
        this.eet_id_provoz = eet_id_provoz;
        this.eet_testing_mode = eet_testing_mode;
        this.eet_service_alias = eet_service_alias;
        this.eet_send_points = eet_send_points;
        this.phone = phone;
        this.email = email;
        this.send_sms = send_sms;
        this.send_info_sms = send_info_sms;
        this.has_tito = has_tito;
        this.info_sms_phone = info_sms_phone;
        this.info_sms_payout_limit = info_sms_payout_limit;
        this.bonus_new_player = bonus_new_player;
        this.max_hour_loss = max_hour_loss;
        this.auto_gb_login = auto_gb_login;
        this.date_start = date_start;
        this.live_game_card_provision = live_game_card_provision;
        this.live_game_company = live_game_company;
        this.profit_division_type = profit_division_type;
        this.profit_division_percent = profit_division_percent;
        this.profit_division_fix = profit_division_fix;
        this.last_action = last_action;
        this.guestbook_sheet_size = guestbook_sheet_size;
        this.disable_minute_points = disable_minute_points;
        this.disable_profit_points = disable_profit_points;
        this.IDdivision = IDdivision;
        this.deleted = deleted;
    }

    public String insertQuery() {
        return "insert into pg_bar (IDbar, IDcompany, IDcashbookForCards, IDcashbookForMachines, IDliveGameCashBook, IDliveGameBM, IDliveGameSafeCashBook, IDmaintenanceGroup, name, street, city, zip, eet_company_name, eet_dic, eet_id_provoz, eet_testing_mode, eet_service_alias, eet_send_points, phone, email, send_sms, send_info_sms, has_tito, info_sms_phone, info_sms_payout_limit, bonus_new_player, max_hour_loss, auto_gb_login, date_start, live_game_card_provision, live_game_company, profit_division_type, profit_division_percent, profit_division_fix, last_action, guestbook_sheet_size, disable_minute_points, disable_profit_points, IDdivision, deleted)" +
                " values (" + IDbar + ", " + IDcompany + ", " + IDcashbookForCards + ", " + IDcashbookForMachines + ", " + IDliveGameCashBook + ", " + IDliveGameBM + ", " + IDliveGameSafeCashBook + ", " + IDmaintenanceGroup + ", " + getValidSQLString(name) + ", " + getValidSQLString(street) + ", " + getValidSQLString(city) + ", " + getValidSQLString(zip) + ", " + getValidSQLString(eet_company_name) + ", " + getValidSQLString(eet_dic) + ", " + eet_id_provoz + ", " + eet_testing_mode + ", " + getValidSQLString(eet_service_alias) + ", " + eet_send_points + ", " + getValidSQLString(phone) + ", " + getValidSQLString(email) + ", " + send_sms + ", " + send_info_sms + ", " + has_tito + ", " + getValidSQLString(info_sms_phone) + ", " + info_sms_payout_limit + ", " + bonus_new_player + ", " + max_hour_loss + ", " + auto_gb_login + ", " + getDate_startAsString() + ", " + live_game_card_provision + ", " + getValidSQLString(live_game_company) + ", " + getValidSQLString(profit_division_type) + ", " + profit_division_percent + ", " + profit_division_fix + ", " + getLast_actionAsString() + ", " + guestbook_sheet_size + ", " + disable_minute_points + ", " + disable_profit_points + ", " + IDdivision + ", " + getDeletedAsString() + ");";
    }

    private String getDate_startAsString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return getValidSQLString(sdf.format(date_start));
    }

    private String getDeletedAsString() {
        if (deleted == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return getValidSQLString(sdf.format(deleted));
    }

    private String getLast_actionAsString() {
        if (last_action == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return getValidSQLString(sdf.format(last_action));
    }

    private String getValidSQLString(String string) {
        if (string == null) { return null; }
        return "'" + string + "'";
    }
}
