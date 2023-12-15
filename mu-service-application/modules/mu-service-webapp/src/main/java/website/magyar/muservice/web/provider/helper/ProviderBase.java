package website.magyar.muservice.web.provider.helper;

import website.magyar.muservice.database.business.BusinessWithTestHeadData;
import website.magyar.muservice.database.tables.TestHead;
import website.magyar.muservice.database.tables.TestHeadData;
import website.magyar.muservice.web.json.ListJson;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Base class of Providers.
 */
public class ProviderBase {

    protected final Set<String> drawables = new HashSet<>(Arrays.asList("dht11", "ds18b20"));

    /**
     * Detects if the two long is different. Any of them can be null too.
     * If both of them is null, that means they are the same.
     *
     * @param oldLongValue is one value
     * @param newLongValue is the other value
     * @return true if different, otherwise false
     */
    protected boolean isLongChanged(final Long oldLongValue, final Long newLongValue) {
        boolean changed = ((!((oldLongValue == null) && (newLongValue == null))) //if both null, it was not changed
                && (oldLongValue == null || !oldLongValue.equals(newLongValue)));  //if old is null then the new is not ...
        return changed;
    }

    protected ListJson getListJson(BusinessWithTestHeadData businessWithTestHeadData, TestHead testHead) {
        var listJson = new ListJson();
        listJson.id = testHead.getId().toString();
        listJson.description = testHead.getDescription();
        listJson.type = testHead.getType();
        listJson.drawable = drawables.contains(listJson.type);
        TestHeadData testHeadData = businessWithTestHeadData.getLastData(testHead.getHeadId());
        listJson.lastInformation = testHeadData.getInformation();
        listJson.lastInformationDate = testHeadData.getTimestamp();
        return listJson;
    }

}
