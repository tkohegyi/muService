package website.magyar.muservice.web.provider.helper;

/**
 * Base class of Providers.
 */
public class ProviderBase {

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

}
