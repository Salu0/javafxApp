package Structures;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Pg_station {

    private Integer idStation;
    private Integer idType;
    private Integer idCashDesk;
    private Integer idStationGroup;
    private Integer idWatcher;
    private String externIdentifier;
    private Integer externIdentifierDesk;
    private String externIdentifierPi;
    private String fwDesk;
    private String name;
    private BigDecimal multiplier;
    private String serialNumber;
    private String note;
    private Timestamp dateStart;
    private Timestamp dateEnd;
    private Timestamp lastAction;
    private Integer deleted;
    private Integer submitIn;
    private Integer sequence;
    private Timestamp lastStationResponse;
    private Timestamp lastNotFinalResponse;
    private Timestamp lastGameResponse;
    private BigDecimal inMultiplier;
    private BigDecimal outMultiplier;
    private BigDecimal betMultiplier;
    private BigDecimal winMultiplier;
    private BigDecimal gameMultiplier;
    private Integer filter;
    private Integer quiet;
    private Integer level;
    private Timestamp dateLastIn;
    private BigDecimal lastInValue;
    private Timestamp dateLastOut;
    private BigDecimal lastOutValue;
    private Integer sendSms;
    private Integer isButton;
    private BigDecimal payoffTemp;
    private Integer isServer2;
    private Integer router;
    private String ip;
    private Integer temporaryBlocked;
    private Timestamp lastRouterContact;
    private String connectionType;
    private String currency;
    private String barName;
    private BigDecimal availableBalance;
    private final BooleanProperty withdrawn = new SimpleBooleanProperty();

    public Pg_station(Integer idStation, Integer idCashDesk, String name, Integer deleted, BigDecimal inMultiplier, BigDecimal outMultiplier, BigDecimal betMultiplier, BigDecimal winMultiplier, BigDecimal gameMultiplier, Timestamp dateLastIn, BigDecimal availableBalance, String barName) {
        this.idStation = idStation;
        this.idCashDesk = idCashDesk;
        this.name = name;
        this.deleted = deleted;
        this.inMultiplier = inMultiplier;
        this.outMultiplier = outMultiplier;
        this.betMultiplier = betMultiplier;
        this.winMultiplier = winMultiplier;
        this.gameMultiplier = gameMultiplier;
        this.dateLastIn = dateLastIn;
        this.availableBalance = availableBalance;
        this.barName = barName;
        if (deleted == 1) {
            setWithdrawn(true);
        } else if (deleted == 0) {
            setWithdrawn(false);
        }
    }

    public String insertQuery() {
        return "insert into pg_station (idStation, idType, idCashDesk, idStationGroup, idWatcher, externIdentifier, externIdentifierDesk, externIdentifierPi, fwDesk, name, multiplier, serialNumber, note, dateStart, dateEnd, lastAction, deleted, submitIn, sequence, lastStationResponse, lastNotFinalResponse, lastGameResponse, inMultiplier, outMultiplier, betMultiplier, winMultiplier, gameMultiplier, filter, quiet, level, dateLastIn, lastInValue, dateLastOut, lastOutValue, sendSms, isButton, payoffTemp, isServer2, router, ip, temporaryBlocked, lastRouterContact, connectionType, currency)" +
                " values (" + idStation + ", " + idType + ", " + idCashDesk + ", " + idStationGroup + ", " + idWatcher + ", " + getValidSQLString(externIdentifier) + ", " + externIdentifierDesk + ", " + getValidSQLString(externIdentifierPi) + ", " + getValidSQLString(fwDesk) + ", " + getValidSQLString(name) + ", " + multiplier + ", " + getValidSQLString(serialNumber) + ", " + getValidSQLString(note) + ", " + getDateStart() + ", " + getDateEnd() + ", " + getLastAction() + ", " + deleted + ", " + submitIn + ", " + sequence + ", " + getLastStationResponse() + ", " + getLastNotFinalResponse() + ", " + getLastGameResponse() + ", " + inMultiplier + ", " + outMultiplier + ", " + betMultiplier + ", " + winMultiplier + ", " + gameMultiplier + ", " + filter + ", " + quiet + ", " + level + ", " + getDateLastIn() + ", " + lastInValue + ", " + getDateLastOut() + ", " + lastOutValue + ", " + sendSms + ", " + isButton + ", " + payoffTemp + ", " + isServer2 + ", " + router + ", " + getValidSQLString(ip) + ", " + temporaryBlocked + ", " + getLastRouterContact() + ", " + getValidSQLString(connectionType) + ", " + getValidSQLString(currency) + ");";
    }

    public String getDateStart() {
        if (dateStart == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return getValidSQLString(sdf.format(dateStart));
    }

    public String getDateEnd() {
        if (dateEnd == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return getValidSQLString(sdf.format(dateEnd));
    }

    public String getLastAction() {
        if (lastAction == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return getValidSQLString(sdf.format(lastAction));
    }

    public String getLastStationResponse() {
        if (lastStationResponse == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return getValidSQLString(sdf.format(lastStationResponse));
    }

    public String getLastNotFinalResponse() {
        if (lastNotFinalResponse == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return getValidSQLString(sdf.format(lastNotFinalResponse));
    }

    public String getLastGameResponse() {
        if (lastGameResponse == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return getValidSQLString(sdf.format(lastGameResponse));
    }

    public String getDateLastIn() {
        if (dateLastIn == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return getValidSQLString(sdf.format(dateLastIn));
    }

    public Timestamp getDateLastInAsTimestamp() {
        return dateLastIn;
    }

    public String getDateLastOut() {
        if (dateLastOut == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return getValidSQLString(sdf.format(dateLastOut));
    }

    public String getLastRouterContact() {
        if (lastRouterContact == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return getValidSQLString(sdf.format(lastRouterContact));
    }

    private String getValidSQLString(String string) {
        if (string == null) { return null; }
        return "'" + string + "'";
    }

    public Integer getIdStation() {
        return idStation;
    }

    public Integer getIdType() {
        return idType;
    }

    public Integer getIdCashDesk() {
        return idCashDesk;
    }

    public Integer getIdStationGroup() {
        return idStationGroup;
    }

    public Integer getIdWatcher() {
        return idWatcher;
    }

    public String getExternIdentifier() {
        return externIdentifier;
    }

    public Integer getExternIdentifierDesk() {
        return externIdentifierDesk;
    }

    public String getExternIdentifierPi() {
        return externIdentifierPi;
    }

    public String getFwDesk() {
        return fwDesk;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getMultiplier() {
        return multiplier;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getNote() {
        return note;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public Integer getSubmitIn() {
        return submitIn;
    }

    public Integer getSequence() {
        return sequence;
    }

    public BigDecimal getInMultiplier() {
        return inMultiplier;
    }

    public BigDecimal getOutMultiplier() {
        return outMultiplier;
    }

    public BigDecimal getBetMultiplier() {
        return betMultiplier;
    }

    public BigDecimal getWinMultiplier() {
        return winMultiplier;
    }

    public BigDecimal getGameMultiplier() {
        return gameMultiplier;
    }

    public Integer getFilter() {
        return filter;
    }

    public Integer getQuiet() {
        return quiet;
    }

    public Integer getLevel() {
        return level;
    }

    public BigDecimal getLastInValue() {
        return lastInValue;
    }

    public BigDecimal getLastOutValue() {
        return lastOutValue;
    }

    public Integer getSendSms() {
        return sendSms;
    }

    public Integer getIsButton() {
        return isButton;
    }

    public BigDecimal getPayoffTemp() {
        return payoffTemp;
    }

    public Integer getIsServer2() {
        return isServer2;
    }

    public Integer getRouter() {
        return router;
    }

    public String getIp() {
        return ip;
    }

    public Integer getTemporaryBlocked() {
        return temporaryBlocked;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "Pg_station{" +
                "idStation=" + idStation +
                ", idType=" + idType +
                ", idCashDesk=" + idCashDesk +
                ", idStationGroup=" + idStationGroup +
                ", idWatcher=" + idWatcher +
                ", externIdentifier='" + externIdentifier + '\'' +
                ", externIdentifierDesk=" + externIdentifierDesk +
                ", externIdentifierPi='" + externIdentifierPi + '\'' +
                ", fwDesk='" + fwDesk + '\'' +
                ", name='" + name + '\'' +
                ", multiplier=" + multiplier +
                ", serialNumber='" + serialNumber + '\'' +
                ", note='" + note + '\'' +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", lastAction=" + lastAction +
                ", deleted=" + deleted +
                ", submitIn=" + submitIn +
                ", sequence=" + sequence +
                ", lastStationResponse=" + lastStationResponse +
                ", lastNotFinalResponse=" + lastNotFinalResponse +
                ", lastGameResponse=" + lastGameResponse +
                ", inMultiplier=" + inMultiplier +
                ", outMultiplier=" + outMultiplier +
                ", betMultiplier=" + betMultiplier +
                ", winMultiplier=" + winMultiplier +
                ", gameMultiplier=" + gameMultiplier +
                ", filter=" + filter +
                ", quiet=" + quiet +
                ", level=" + level +
                ", dateLastIn=" + dateLastIn +
                ", lastInValue=" + lastInValue +
                ", dateLastOut=" + dateLastOut +
                ", lastOutValue=" + lastOutValue +
                ", sendSms=" + sendSms +
                ", isButton=" + isButton +
                ", payoffTemp=" + payoffTemp +
                ", isServer2=" + isServer2 +
                ", router=" + router +
                ", ip='" + ip + '\'' +
                ", temporaryBlocked=" + temporaryBlocked +
                ", lastRouterContact=" + lastRouterContact +
                ", connectionType='" + connectionType + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }

    public void setIdStation(Integer idStation) {
        this.idStation = idStation;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public void setIdCashDesk(Integer idCashDesk) {
        this.idCashDesk = idCashDesk;
    }

    public void setIdStationGroup(Integer idStationGroup) {
        this.idStationGroup = idStationGroup;
    }

    public void setIdWatcher(Integer idWatcher) {
        this.idWatcher = idWatcher;
    }

    public void setExternIdentifier(String externIdentifier) {
        this.externIdentifier = externIdentifier;
    }

    public void setExternIdentifierDesk(Integer externIdentifierDesk) {
        this.externIdentifierDesk = externIdentifierDesk;
    }

    public void setExternIdentifierPi(String externIdentifierPi) {
        this.externIdentifierPi = externIdentifierPi;
    }

    public void setFwDesk(String fwDesk) {
        this.fwDesk = fwDesk;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMultiplier(BigDecimal multiplier) {
        this.multiplier = multiplier;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setDateStart(Timestamp dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateEnd(Timestamp dateEnd) {
        this.dateEnd = dateEnd;
    }

    public void setLastAction(Timestamp lastAction) {
        this.lastAction = lastAction;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public void setSubmitIn(Integer submitIn) {
        this.submitIn = submitIn;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public void setLastStationResponse(Timestamp lastStationResponse) {
        this.lastStationResponse = lastStationResponse;
    }

    public void setLastNotFinalResponse(Timestamp lastNotFinalResponse) {
        this.lastNotFinalResponse = lastNotFinalResponse;
    }

    public void setLastGameResponse(Timestamp lastGameResponse) {
        this.lastGameResponse = lastGameResponse;
    }

    public void setInMultiplier(BigDecimal inMultiplier) {
        this.inMultiplier = inMultiplier;
    }

    public void setOutMultiplier(BigDecimal outMultiplier) {
        this.outMultiplier = outMultiplier;
    }

    public void setBetMultiplier(BigDecimal betMultiplier) {
        this.betMultiplier = betMultiplier;
    }

    public void setWinMultiplier(BigDecimal winMultiplier) {
        this.winMultiplier = winMultiplier;
    }

    public void setGameMultiplier(BigDecimal gameMultiplier) {
        this.gameMultiplier = gameMultiplier;
    }

    public void setFilter(Integer filter) {
        this.filter = filter;
    }

    public void setQuiet(Integer quiet) {
        this.quiet = quiet;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setDateLastIn(Timestamp dateLastIn) {
        this.dateLastIn = dateLastIn;
    }

    public void setLastInValue(BigDecimal lastInValue) {
        this.lastInValue = lastInValue;
    }

    public void setDateLastOut(Timestamp dateLastOut) {
        this.dateLastOut = dateLastOut;
    }

    public void setLastOutValue(BigDecimal lastOutValue) {
        this.lastOutValue = lastOutValue;
    }

    public void setSendSms(Integer sendSms) {
        this.sendSms = sendSms;
    }

    public void setIsButton(Integer isButton) {
        this.isButton = isButton;
    }

    public void setPayoffTemp(BigDecimal payoffTemp) {
        this.payoffTemp = payoffTemp;
    }

    public void setIsServer2(Integer isServer2) {
        this.isServer2 = isServer2;
    }

    public void setRouter(Integer router) {
        this.router = router;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setTemporaryBlocked(Integer temporaryBlocked) {
        this.temporaryBlocked = temporaryBlocked;
    }

    public void setLastRouterContact(Timestamp lastRouterContact) {
        this.lastRouterContact = lastRouterContact;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public StringProperty namePropertyProperty() {
        return nameProperty;
    }

    public void setNameProperty(String nameProperty) {
        this.nameProperty.set(nameProperty);
    }

    public BigDecimal getAvailableBalance() {
        if (availableBalance == null)
            return null;
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getBarName() {
        return barName;
    }

    public void setBarName(String barName) {
        this.barName = barName;
    }

    private StringProperty nameProperty = new SimpleStringProperty();

    public StringProperty getNameProperty() {return nameProperty;}

    public final BooleanProperty withdrawnProperty() {
            return this.withdrawn;
    }

    public final boolean isWithdrawn() {
        return this.withdrawnProperty().get();
    }

    public final void setWithdrawn(final boolean withdrawn) {
        this.withdrawnProperty().set(withdrawn);
    }
}

