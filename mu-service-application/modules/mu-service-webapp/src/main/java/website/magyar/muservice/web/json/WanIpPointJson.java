package website.magyar.muservice.web.json;

import website.magyar.muservice.database.business.helper.DateTimeConverter;
import website.magyar.muservice.helper.JsonField;

import java.text.ParseException;

/**
 * Json structure for a single WAN IP data point.
 */
public class WanIpPointJson {
    @JsonField
    public Long order;   // epoch milliseconds
    @JsonField
    public String status;

    public WanIpPointJson(String dateTime, String status) throws ParseException {
        DateTimeConverter dateTimeConverter = new DateTimeConverter();
        this.order = dateTimeConverter.getDateTime(dateTime).getTime();
        this.status = status;
    }
}
