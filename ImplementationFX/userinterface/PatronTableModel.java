package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

//==============================================================================

public class PatronTableModel {

    private final SimpleStringProperty patronID;
    private final SimpleStringProperty patronName;
    private final SimpleStringProperty patronAddress;
    private final SimpleStringProperty patronCity;
    private final SimpleStringProperty status;
    private final SimpleStringProperty patronStateCode;
    private final SimpleStringProperty patronEmail;
    private final SimpleStringProperty patronZip;
    private final SimpleStringProperty patronDOB;


    public PatronTableModel(Vector<String> patronData){
        patronID = new SimpleStringProperty(patronData.get(0));
        patronName = new SimpleStringProperty(patronData.get(1));
        patronAddress = new SimpleStringProperty(patronData.get(2));
        patronCity = new SimpleStringProperty(patronData.get(3));
        patronStateCode = new SimpleStringProperty(patronData.get(4));
        patronZip = new SimpleStringProperty(patronData.get(5));
        patronEmail = new SimpleStringProperty(patronData.get(6));
        patronDOB = new SimpleStringProperty(patronData.get(7));
        status = new SimpleStringProperty(patronData.get(8));


    }

    public SimpleStringProperty patronIDProperty() {
        return patronID;
    }

    public SimpleStringProperty statusProperty() { return status; }
    public SimpleStringProperty patronNameProperty() { return patronName; }
    public SimpleStringProperty patronAddressProperty() { return patronAddress; }
    public SimpleStringProperty patronCityProperty() { return patronCity; }
    public SimpleStringProperty patronStateCodeProperty() { return patronStateCode; }
    public SimpleStringProperty patronZipProperty() { return patronZip; }
    public SimpleStringProperty patronEmailProperty(){ return patronEmail; }
    public SimpleStringProperty patronDOBProperty() { return patronDOB; }

    public String getPatronID() {return patronID.get(); }
    public String getStatus() { return status.get(); }
    public String getPatronName() { return patronName.get(); }
    public String getPatronAddress() { return patronAddress.get(); }
    public String getPatronCity() { return patronCity.get(); }
    public String getPatronStateCode() { return patronStateCode.get(); }
    public String getPatronZip() { return patronZip.get(); }
    public String getPatronDOB() { return patronDOB.get(); }
    public String getPatronEmail() { return patronEmail.get(); }

    public void setPatronID(String patronID){ this.patronID.set(patronID); }
    public void setStatus(String status) { this.status.set(status); }
    public void setPatronName(String patronName){ this.patronName.set(patronName); }
    public void setPatronAddress(String patronAddress){ this.patronAddress.set(patronAddress); }
    public void setPatronCity(String patronCity){ this.patronAddress.set(patronCity); }
    public void setPatronStateCode(String patronStateCode){ this.patronAddress.set(patronStateCode); }
    public void setPatronZip(String patronZip){ this.patronAddress.set(patronZip); }
    public void setPatronEmail(String patronEmail){ this.patronAddress.set(patronEmail); }
    public void setPatronDOB(String patronDOB){ this.patronAddress.set(patronDOB); }

}
