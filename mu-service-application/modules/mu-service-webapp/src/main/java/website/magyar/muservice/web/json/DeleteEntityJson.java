package website.magyar.muservice.web.json;

import website.magyar.muservice.helper.JsonField;

/**
 * Json structure to be used to delete an entity.
 * Since every database record has its own unique id, specifying the id is enough.
 */
public class DeleteEntityJson {
    @JsonField
    public String entityId;
}
