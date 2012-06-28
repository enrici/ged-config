package fr.insa.ged.config.computedgroups;

import org.nuxeo.ecm.platform.computedgroups.AbstractAttributeBasedGroupComputer;
import org.nuxeo.ecm.platform.computedgroups.GroupComputer;

/**
 * {@link GroupComputer} implementation that uses the eduPersonAffiliation
 * field.
 *
 * @author Antoine Taillefer
 */
public class INSAAffiliationGroupComputer extends
        AbstractAttributeBasedGroupComputer {

    @Override
    protected String getAttributeForGroupComputation() {
        return "eduPersonAffiliation";
    }

}
