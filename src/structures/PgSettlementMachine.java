package structures;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PgSettlementMachine {

    private Integer idRecord;
    private Integer idPrevRecord;
    private Integer idSettlement;
    private Integer idStation;
    private Date date;
    private BigDecimal currentIn;
    private BigDecimal currentOut;
    private BigDecimal currentBet;
    private BigDecimal currentWin;
    private BigDecimal currentGame;
    private BigDecimal currentTicketIn;
    private BigDecimal currentTicketOut;
    private BigDecimal currentPromoIn;
    private BigDecimal currentPromoOut;
    private BigDecimal currentJackpotOut;
    private BigDecimal oldIn;
    private BigDecimal oldOut;
    private BigDecimal newIn;
    private BigDecimal newOut;
    private BigDecimal totalIn;
    private BigDecimal totalOut;
    private BigDecimal totalTicketIn;
    private BigDecimal totalTicketOut;
    private BigDecimal totalPromoIn;
    private BigDecimal totalPromoOut;
    private BigDecimal totalJackpotOut;
    private BigDecimal totalBet;
    private BigDecimal totalWin;
    private BigDecimal totalGame;
    private BigDecimal payoff;
    private BigDecimal missingPayoff;
    private Integer manualEntry;
    private Timestamp manualInserted;
    private Integer idUser;

    public PgSettlementMachine(Integer idRecord, Integer idPrevRecord, Integer idSettlement, Integer idStation, Date date, BigDecimal currentIn, BigDecimal currentOut, BigDecimal currentBet, BigDecimal currentWin, BigDecimal currentGame, BigDecimal currentTicketIn, BigDecimal currentTicketOut, BigDecimal currentPromoIn, BigDecimal currentPromoOut, BigDecimal currentJackpotOut, BigDecimal oldIn, BigDecimal oldOut, BigDecimal newIn, BigDecimal newOut, BigDecimal totalIn, BigDecimal totalOut, BigDecimal totalTicketIn, BigDecimal totalTicketOut, BigDecimal totalPromoIn, BigDecimal totalPromoOut, BigDecimal totalJackpotOut, BigDecimal totalBet, BigDecimal totalWin, BigDecimal totalGame, BigDecimal payoff, BigDecimal missingPayoff, Integer manualEntry, Timestamp manualInserted, Integer idUser) {
        this.idRecord = idRecord;
        this.idPrevRecord = idPrevRecord;
        this.idSettlement = idSettlement;
        this.idStation = idStation;
        this.date = date;
        this.currentIn = currentIn;
        this.currentOut = currentOut;
        this.currentBet = currentBet;
        this.currentWin = currentWin;
        this.currentGame = currentGame;
        this.currentTicketIn = currentTicketIn;
        this.currentTicketOut = currentTicketOut;
        this.currentPromoIn = currentPromoIn;
        this.currentPromoOut = currentPromoOut;
        this.currentJackpotOut = currentJackpotOut;
        this.oldIn = oldIn;
        this.oldOut = oldOut;
        this.newIn = newIn;
        this.newOut = newOut;
        this.totalIn = totalIn;
        this.totalOut = totalOut;
        this.totalTicketIn = totalTicketIn;
        this.totalTicketOut = totalTicketOut;
        this.totalPromoIn = totalPromoIn;
        this.totalPromoOut = totalPromoOut;
        this.totalJackpotOut = totalJackpotOut;
        this.totalBet = totalBet;
        this.totalWin = totalWin;
        this.totalGame = totalGame;
        this.payoff = payoff;
        this.missingPayoff = missingPayoff;
        this.manualEntry = manualEntry;
        this.manualInserted = manualInserted;
        this.idUser = idUser;
    }

    public String insertQuery() {
        return "insert into pg_settlement_machine (IDprevRecord, IDsettlement, IDstation, date, current_in, current_out, current_bet, current_win, current_game, current_ticket_in, current_ticket_out, current_promo_in, current_promo_out, current_jackpot_out, old_in, old_out, new_in, new_out, total_in, total_out, total_ticket_in, total_ticket_out, total_promo_in, total_promo_out, total_jackpot_out, total_bet, total_win, total_game, payoff, missing_payoff, manualEntry, manualInserted, IDuser)" +
                " values (" + idPrevRecord + ", " + idSettlement + ", " + idStation + ", " + getDateAsValidSQL() + ", " + currentIn + ", " + currentOut + ", " + currentBet + ", " + currentWin + ", " + currentGame + ", " + currentTicketIn + ", " + currentTicketOut + ", " +  currentPromoIn + ", " + currentPromoOut + ", " + currentJackpotOut + ", " + oldIn + ", " + oldOut + ", " + newIn + ", " + newOut + ", " + totalIn + ", " + totalOut + ", " + totalTicketIn + ", " + totalTicketOut + ", " + totalPromoIn + ", " + totalPromoOut + ", " + totalJackpotOut + ", " + totalBet + ", " + totalWin + ", " + totalGame + ", " + payoff + ", " + missingPayoff + ", " + manualEntry + ", " + getManualInsertedAsString() + ", " + getIdUser() + ");";
    }

    @Override
    public String toString() {
        return "PgSettlementMachine{" +
                "idRecord=" + idRecord +
                ", idPrevRecord=" + idPrevRecord +
                ", idSettlement=" + idSettlement +
                ", idStation=" + idStation +
                ", date=" + date +
                ", currentIn=" + currentIn +
                ", currentOut=" + currentOut +
                ", currentBet=" + currentBet +
                ", currentWin=" + currentWin +
                ", currentGame=" + currentGame +
                ", currentTicketIn=" + currentTicketIn +
                ", currentTicketOut=" + currentTicketOut +
                ", currentPromoIn=" + currentPromoIn +
                ", currentPromoOut=" + currentPromoOut +
                ", currentJackpotOut=" + currentJackpotOut +
                ", oldIn=" + oldIn +
                ", oldOut=" + oldOut +
                ", newIn=" + newIn +
                ", newOut=" + newOut +
                ", totalIn=" + totalIn +
                ", totalOut=" + totalOut +
                ", totalTicketIn=" + totalTicketIn +
                ", totalTicketOut=" + totalTicketOut +
                ", totalPromoIn=" + totalPromoIn +
                ", totalPromoOut=" + totalPromoOut +
                ", totalJackpotOut=" + totalJackpotOut +
                ", totalBet=" + totalBet +
                ", totalWin=" + totalWin +
                ", totalGame=" + totalGame +
                ", payoff=" + payoff +
                ", missingPayoff=" + missingPayoff +
                ", manualEntry=" + manualEntry +
                ", manualInserted=" + manualInserted +
                ", idUser=" + idUser +
                '}';
    }

    private String getManualInsertedAsString() {
        if (manualInserted == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return getValidSQLString(sdf.format(manualInserted));
    }

    public String getDate() {
        if (date == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public String getDateAsValidSQL() {
        if (date == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return getValidSQLString(sdf.format(date));
    }

    private String getValidSQLString(String string) {
        if (string == null) { return null; }
        return "'" + string + "'";
    }

    public Integer getIdRecord() {
        return idRecord;
    }

    public void setIdRecord(Integer idRecord) {
        this.idRecord = idRecord;
    }

    public Integer getIdPrevRecord() {
        return idPrevRecord;
    }

    public void setIdPrevRecord(Integer idPrevRecord) {
        this.idPrevRecord = idPrevRecord;
    }

    public Integer getIdSettlement() {
        return idSettlement;
    }

    public void setIdSettlement(Integer idSettlement) {
        this.idSettlement = idSettlement;
    }

    public Integer getIdStation() {
        return idStation;
    }

    public void setIdStation(Integer idStation) {
        this.idStation = idStation;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getCurrentIn() {
        return currentIn;
    }

    public void setCurrentIn(BigDecimal currentIn) {
        this.currentIn = currentIn;
    }

    public BigDecimal getCurrentOut() {
        return currentOut;
    }

    public void setCurrentOut(BigDecimal currentOut) {
        this.currentOut = currentOut;
    }

    public BigDecimal getCurrentBet() {
        return currentBet;
    }

    public void setCurrentBet(BigDecimal currentBet) {
        this.currentBet = currentBet;
    }

    public BigDecimal getCurrentWin() {
        return currentWin;
    }

    public void setCurrentWin(BigDecimal currentWin) {
        this.currentWin = currentWin;
    }

    public BigDecimal getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(BigDecimal currentGame) {
        this.currentGame = currentGame;
    }

    public BigDecimal getCurrentTicketIn() {
        return currentTicketIn;
    }

    public void setCurrentTicketIn(BigDecimal currentTicketIn) {
        this.currentTicketIn = currentTicketIn;
    }

    public BigDecimal getCurrentTicketOut() {
        return currentTicketOut;
    }

    public void setCurrentTicketOut(BigDecimal currentTicketOut) {
        this.currentTicketOut = currentTicketOut;
    }

    public BigDecimal getCurrentPromoIn() {
        return currentPromoIn;
    }

    public void setCurrentPromoIn(BigDecimal currentPromoIn) {
        this.currentPromoIn = currentPromoIn;
    }

    public BigDecimal getCurrentPromoOut() {
        return currentPromoOut;
    }

    public void setCurrentPromoOut(BigDecimal currentPromoOut) {
        this.currentPromoOut = currentPromoOut;
    }

    public BigDecimal getCurrentJackpotOut() {
        return currentJackpotOut;
    }

    public void setCurrentJackpotOut(BigDecimal currentJackpotOut) {
        this.currentJackpotOut = currentJackpotOut;
    }

    public BigDecimal getOldIn() {
        return oldIn;
    }

    public void setOldIn(BigDecimal oldIn) {
        this.oldIn = oldIn;
    }

    public BigDecimal getOldOut() {
        return oldOut;
    }

    public void setOldOut(BigDecimal oldOut) {
        this.oldOut = oldOut;
    }

    public BigDecimal getNewIn() {
        return newIn;
    }

    public void setNewIn(BigDecimal newIn) {
        this.newIn = newIn;
    }

    public BigDecimal getNewOut() {
        return newOut;
    }

    public void setNewOut(BigDecimal newOut) {
        this.newOut = newOut;
    }

    public BigDecimal getTotalIn() {
        return totalIn;
    }

    public void setTotalIn(BigDecimal totalIn) {
        this.totalIn = totalIn;
    }

    public BigDecimal getTotalOut() {
        return totalOut;
    }

    public void setTotalOut(BigDecimal totalOut) {
        this.totalOut = totalOut;
    }

    public BigDecimal getTotalTicketIn() {
        return totalTicketIn;
    }

    public void setTotalTicketIn(BigDecimal totalTicketIn) {
        this.totalTicketIn = totalTicketIn;
    }

    public BigDecimal getTotalTicketOut() {
        return totalTicketOut;
    }

    public void setTotalTicketOut(BigDecimal totalTicketOut) {
        this.totalTicketOut = totalTicketOut;
    }

    public BigDecimal getTotalPromoIn() {
        return totalPromoIn;
    }

    public void setTotalPromoIn(BigDecimal totalPromoIn) {
        this.totalPromoIn = totalPromoIn;
    }

    public BigDecimal getTotalPromoOut() {
        return totalPromoOut;
    }

    public void setTotalPromoOut(BigDecimal totalPromoOut) {
        this.totalPromoOut = totalPromoOut;
    }

    public BigDecimal getTotalJackpotOut() {
        return totalJackpotOut;
    }

    public void setTotalJackpotOut(BigDecimal totalJackpotOut) {
        this.totalJackpotOut = totalJackpotOut;
    }

    public BigDecimal getTotalBet() {
        return totalBet;
    }

    public void setTotalBet(BigDecimal totalBet) {
        this.totalBet = totalBet;
    }

    public BigDecimal getTotalWin() {
        return totalWin;
    }

    public void setTotalWin(BigDecimal totalWin) {
        this.totalWin = totalWin;
    }

    public BigDecimal getTotalGame() {
        return totalGame;
    }

    public void setTotalGame(BigDecimal totalGame) {
        this.totalGame = totalGame;
    }

    public BigDecimal getPayoff() {
        return payoff;
    }

    public void setPayoff(BigDecimal payoff) {
        this.payoff = payoff;
    }

    public BigDecimal getMissingPayoff() {
        return missingPayoff;
    }

    public void setMissingPayoff(BigDecimal missingPayoff) {
        this.missingPayoff = missingPayoff;
    }

    public Integer getManualEntry() {
        return manualEntry;
    }

    public void setManualEntry(Integer manualEntry) {
        this.manualEntry = manualEntry;
    }

    public Timestamp getManualInserted() {
        return manualInserted;
    }

    public void setManualInserted(Timestamp manualInserted) {
        this.manualInserted = manualInserted;
    }

    public Integer getIdUser() {
        if (date == null)
            return null;
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
}

