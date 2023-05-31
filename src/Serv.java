public class Serv {
    private String ServiceCode;
    private String Description;
    private float Price;
    private char DelIndicator;
    private String DelReason;

    public Serv(String serviceCode, String description, float price) {
        ServiceCode = serviceCode;
        Description = description;
        Price = price;
    }

    public Serv(String serviceCode, String description, float price, char delIndicator, String delReason) {
        ServiceCode = serviceCode;
        Description = description;
        Price = price;
        DelIndicator = delIndicator;
        DelReason = delReason;
    }

    public String getServiceCode() {
        return ServiceCode;
    }

    public void setServiceCode(String serviceCode) {
        ServiceCode = serviceCode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
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
