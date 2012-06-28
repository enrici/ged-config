package fr.insa.ged.config.publisher.helper;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.schema.FacetNames;
import org.nuxeo.ecm.core.schema.TypeService;
import org.nuxeo.runtime.api.Framework;

public class INSARootSectionsFinderHelper {

    private static final Log log = LogFactory.getLog(INSARootSectionsFinderHelper.class);

    private static Set<String> sectionRootTypes;

    private static Set<String> sectionTypes;

    private INSARootSectionsFinderHelper() {
        // Helper class
    }

    public static INSARootSectionsFinder getRootSectionsFinder(
            CoreSession coreSession) {
        return new INSARootSectionsFinder(coreSession, getSectionRootTypes(),
                getSectionTypes());
    }

    public static Set<String> getSectionRootTypes() {
        if (sectionRootTypes == null) {
            sectionRootTypes = getTypeNamesForFacet(FacetNames.MASTER_PUBLISH_SPACE);
            if (sectionRootTypes == null) {
                sectionRootTypes = new HashSet<String>();
            }
        }
        return sectionRootTypes;
    }

    public static Set<String> getTypeNamesForFacet(String facetName) {
        TypeService schemaService;
        try {
            // XXX should use getService(SchemaManager.class)
            schemaService = (TypeService) Framework.getRuntime().getComponent(
                    TypeService.NAME);
        } catch (Exception e) {
            log.error("Exception in retrieving publish spaces : ", e);
            return null;
        }

        Set<String> publishRoots = schemaService.getTypeManager().getDocumentTypeNamesForFacet(
                facetName);
        if (publishRoots == null || publishRoots.isEmpty()) {
            return null;
        }
        return publishRoots;
    }

    public static Set<String> getSectionTypes() {
        if (sectionTypes == null) {
            sectionTypes = getTypeNamesForFacet(FacetNames.MASTER_PUBLISH_SPACE);
            if (sectionTypes == null) {
                sectionTypes = new HashSet<String>();
            }
        }
        return sectionTypes;
    }

}
