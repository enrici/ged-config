package fr.insa.ged.config.computedgroups;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nuxeo.ecm.platform.computedgroups.AbstractGroupComputer;
import org.nuxeo.ecm.platform.computedgroups.GroupComputer;
import org.nuxeo.ecm.platform.usermanager.NuxeoPrincipalImpl;

/**
 * {@link GroupComputer} implementation that uses the eduPersonAffiliation
 * field.
 *
 * @author Antoine Taillefer
 */
public class INSAAffiliationGroupComputer extends AbstractGroupComputer {

    public static final String ETUDIANTS_GROUP_NAME = "etudiants";

    public static final String PERSONNELS_GROUP_NAME = "personnels";

    public static final String[] COMPUTED_GROUP_NAMES = { ETUDIANTS_GROUP_NAME,
            PERSONNELS_GROUP_NAME };

    public static final String ETUDIANTS_EDU_PERSON_AFFILIATION = "student";

    public static final String[] PERSONNELS_EDU_PERSON_AFFILIATION = {
            "faculty", "staff", "employee", "researcher" };

    protected String getAttributeForGroupComputation() {
        return "eduPersonAffiliation";
    }

    public List<String> getAllGroupIds() throws Exception {

        return Arrays.asList(COMPUTED_GROUP_NAMES);
    }

    public List<String> getGroupMembers(String groupName) throws Exception {

        List<String> memberIds = new ArrayList<String>();

        // if (ETUDIANTS_GROUP_NAME.equals(groupName)) {
        // Map<String, Serializable> filter = new HashMap<String,
        // Serializable>();
        // filter.put(getAttributeForGroupComputation(),
        // ETUDIANTS_EDU_PERSON_AFFILIATION);
        // DocumentModelList users = getUM().searchUsers(filter, null);
        // for (DocumentModel user : users) {
        // memberIds.add(user.getId());
        // }
        // } else if (PERSONNELS_GROUP_NAME.equals(groupName)) {
        // for (String eduPersonAffiliation : PERSONNELS_EDU_PERSON_AFFILIATION)
        // {
        // Map<String, Serializable> filter = new HashMap<String,
        // Serializable>();
        // filter.put(getAttributeForGroupComputation(),
        // eduPersonAffiliation);
        // DocumentModelList users = getUM().searchUsers(filter, null);
        // for (DocumentModel user : users) {
        // memberIds.add(user.getId());
        // }
        // }
        // }

        return memberIds;
    }

    public List<String> getGroupsForUser(NuxeoPrincipalImpl nuxeoPrincipal)
            throws Exception {
        List<String> grpNames = new ArrayList<String>();

        String property = (String) nuxeoPrincipal.getModel().getProperty(
                getUM().getUserSchemaName(), getAttributeForGroupComputation());
        if (ETUDIANTS_EDU_PERSON_AFFILIATION.equals(property)) {
            grpNames.add(ETUDIANTS_GROUP_NAME);
        } else if (Arrays.asList(PERSONNELS_EDU_PERSON_AFFILIATION).contains(
                property)) {
            grpNames.add(PERSONNELS_GROUP_NAME);
        }

        return grpNames;
    }

    public List<String> getParentsGroupNames(String groupName) throws Exception {
        return null;
    }

    public List<String> getSubGroupsNames(String groupName) throws Exception {
        return null;
    }

    @Override
    public List<String> searchGroups(Map<String, Serializable> filter,
            Set<String> fulltext) throws Exception {

        List<String> groups = new ArrayList<String>();
        String grpName = (String) filter.get(getUM().getGroupIdField());
        if (grpName != null) {
            for (String computedGroupName : COMPUTED_GROUP_NAMES) {
                if (computedGroupName.startsWith(grpName.toLowerCase())) {
                    groups.add(computedGroupName);
                }
            }
        }
        return groups;
    }

}
