package website.magyar.muservice.web.json;

import website.magyar.muservice.database.business.helper.DateTimeConverter;
import website.magyar.muservice.helper.JsonField;

import java.text.ParseException;

/**
 * Json structure that is used to provide information for a person.
 */
public class SeriesJson {
    @JsonField
    public Long order;
    @JsonField
    public Double dValue;
    @JsonField
    public String dateTime;
    @JsonField
    public String value;

    public SeriesJson(String dateTime, Double value) throws ParseException {
        DateTimeConverter dateTimeConverter = new DateTimeConverter();
        this.order = dateTimeConverter.getDateTime(dateTime).getTime();
        this.dValue = value;
        this.dateTime = dateTime;
        this.value = value.toString();
    }
}
