public class LabReq {
    private String ReqUID;
    private String PUID;
    private String ReqDate;
    private String ReqTime;
    private String Result;
    private char DelIndicator;
    private String DelReason;

    public LabReq(String reqUID, String PUID, String reqDate, String reqTime, String result) {
        ReqUID = reqUID;
        this.PUID = PUID;
        ReqDate = reqDate;
        ReqTime = reqTime;
        Result = result;
    }

    public LabReq(String reqUID, String PUID, String reqDate, String reqTime, String result, char delIndicator, String delReason) {
        ReqUID = reqUID;
        this.PUID = PUID;
        ReqDate = reqDate;
        ReqTime = reqTime;
        Result = result;
        DelIndicator = delIndicator;
        DelReason = delReason;
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

    public String getReqUID() {
        return ReqUID;
    }

    public void setReqUID(String reqUID) {
        ReqUID = reqUID;
    }

    public String getPUID() {
        return PUID;
    }

    public void setPUID(String PUID) {
        this.PUID = PUID;
    }

    public String getReqDate() {
        return ReqDate;
    }

    public void setReqDate(String reqDate) {
        ReqDate = reqDate;
    }

    public String getReqTime() {
        return ReqTime;
    }

    public void setReqTime(String reqTime) {
        ReqTime = reqTime;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }
}
