package myoffers.prasad.com.myoffers;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by prasad on 9/3/18.
 */

public class MyOffer implements Serializable{
    private String merchantID;
    private String tagLine;
    private String offerDesc;
    private String category;
    private Date validFrom;
    private Date validTo;
    private int thumbnail;

    public MyOffer(String merchantID, String caption, String offerDesc, String category, Date validFrom, Date validTo, int thumbnail) {
        this.merchantID = merchantID;
        this.tagLine = caption;
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

    public String getCaption() {
        return tagLine;
    }

    public void setCaption(String caption) {
        this.tagLine = caption;
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
