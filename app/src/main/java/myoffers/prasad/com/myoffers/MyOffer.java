package myoffers.prasad.com.myoffers;

import java.util.Date;

/**
 * Created by prasad on 9/3/18.
 */

public class MyOffer {
    private String merchantID;
    private String tagLine;
    private String offerDesc;
    private String category;
    private Date validFrom;
    private Date validTo;
    private int thumbnail;

    public MyOffer(String merchantID, String tagLine, String offerDesc, String category, Date validFrom, Date validTo, int thumbnail) {
        this.merchantID = merchantID;
        this.tagLine = tagLine;
        this.offerDesc = offerDesc;
        this.category = category;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.thumbnail = thumbnail;
    }

    public String getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(String merchantID) {
        this.merchantID = merchantID;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public String getOfferDesc() {
        return offerDesc;
    }

    public void setOfferDesc(String offerDesc) {
        this.offerDesc = offerDesc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
