public class PatRec {
    private String PUID;
    private String LastName;
    private String FirstName;
    private String MiddleName;
    private String Birthday;
    private char Gender;
    private String Address;
    private String PhoneNum;
    private String NatID;
    private char DelIndicator;
    private String DelReason;

    public PatRec(String PUID, String lastName, String firstName, String middleName, String birthday, char gender, String address, String phoneNum, String natID) {
        this.PUID = PUID;
        LastName = lastName;
        FirstName = firstName;
        MiddleName = middleName;
        Birthday = birthday;
        Gender = gender;
        Address = address;
        PhoneNum = phoneNum;
        NatID = natID;
    }

    public PatRec(String PUID, String lastName, String firstName, String middleName, String birthday, char gender, String address, String phoneNum, String natID, char delIndicator, String delReason) {
        this.PUID = PUID;
        LastName = lastName;
        FirstName = firstName;
        MiddleName = middleName;
        Birthday = birthday;
        Gender = gender;
        Address = address;
        PhoneNum = phoneNum;
        NatID = natID;
        DelIndicator = delIndicator;
        DelReason = delReason;
    }

    public String getPUID() {
        return PUID;
    }

    public void setPUID(String PUID) {
        this.PUID = PUID;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public char getGender() {
        return Gender;
    }

    public void setGender(char gender) {
        Gender = gender;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        PhoneNum = phoneNum;
    }

    public String getNatID() {
        return NatID;
    }

    public void setNatID(String natID) {
        NatID = natID;
    }

    public char getDelIndicator() {
        return DelIndicator;
    }

    public void setDelIndicator(char delIndicator) {
        DelIndicator = delIndicator;
    }

    public String getDelReason() {
        return DelReason;
    }

    public void setDelReason(String delReason) {
        DelReason = delReason;
    }

}
