package GUI;

import Structures.Pg_station;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TextField;

import java.sql.Timestamp;
import java.util.Calendar;

final class ModulesController {

    static FilteredList<Pg_station>  tableFilter(TextField filterField, ObservableList<Pg_station> masterData) {

        FilteredList<Pg_station> filteredData = new FilteredList<>(masterData, predicate -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(pg_station -> {
                String lowerCaseFilter = newValue.toLowerCase();
                if (newValue.isEmpty()) { return true; }
                if ((pg_station.getDateLastInAsTimestamp() != null) && (newValue.length() > 2) && ((newValue.charAt(0) == '>') || (newValue.charAt(0) == '<'))) {
                    String possibleDate = newValue.substring(1);
                    if (newValue.charAt(1) != '=') {
                        try {
                            if ((newValue.charAt(0) == '>') && pg_station.getDateLastInAsTimestamp().after(Timestamp.valueOf(possibleDate))) {
                                return true;
                            } else if ((newValue.charAt(0) == '<') && Timestamp.valueOf(possibleDate).after(pg_station.getDateLastInAsTimestamp())) {
                                return true;
                            }
                        } catch (IllegalArgumentException e1) {
                        }
                    }
                    if ((newValue.charAt(1) == '=')) {
                        String secondPossibleDate = newValue.substring(2);
                        try { //<=2017-12-19 03:30:20
                            if ((newValue.charAt(0) == '>') && (pg_station.getDateLastInAsTimestamp().after(Timestamp.valueOf(secondPossibleDate)) || pg_station.getDateLastInAsTimestamp().equals(Timestamp.valueOf(secondPossibleDate)))) {
                                return true;
                            } else if ((newValue.charAt(0) == '<') && (pg_station.getDateLastInAsTimestamp().after(Timestamp.valueOf(secondPossibleDate)) || pg_station.getDateLastInAsTimestamp().equals(Timestamp.valueOf(secondPossibleDate)))) {
                                return true;
                            }
                        } catch (IllegalArgumentException e1) {
                        }
                    }
                } else if ((pg_station.getName() != null) && pg_station.getName().toLowerCase().contains(lowerCaseFilter)) { return true; }
                else if (pg_station.getBarName().toLowerCase().contains(lowerCaseFilter)) { return true; }
                else if (pg_station.getIdStation().toString().toLowerCase().contains(lowerCaseFilter)) { return true; }
                else if ((pg_station.getInMultiplier() != null) && pg_station.getInMultiplier().toString().toLowerCase().contains(lowerCaseFilter)) { return true; }
                else if ((pg_station.getOutMultiplier() != null) && pg_station.getOutMultiplier().toString().toLowerCase().contains(lowerCaseFilter)) { return true; }
                else if ((pg_station.getAvailableBalance() != null) && pg_station.getAvailableBalance().toString().toLowerCase().contains(lowerCaseFilter)) { return true; }
                return false;
            });
        });
        return filteredData;
    }

    static Timestamp getDateSinceInactive() {
        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowTime);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        nowTime.setTime(cal.getTime().getTime());
        return nowTime;
    }
}

