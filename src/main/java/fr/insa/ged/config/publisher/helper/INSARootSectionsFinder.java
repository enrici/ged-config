package fr.insa.ged.config.publisher.helper;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModelList;
import org.nuxeo.ecm.core.api.impl.DocumentModelListImpl;
import org.nuxeo.ecm.platform.publisher.helper.RootSectionsFinder;

public class INSARootSectionsFinder extends RootSectionsFinder {

    private static final Log log = LogFactory.getLog(INSARootSectionsFinder.class);

    public INSARootSectionsFinder(CoreSession userSession,
            Set<String> sectionRootTypes, Set<String> sectionTypes) {
        super(userSession, sectionRootTypes, sectionTypes);
    }

    @Override
    protected DocumentModelList getDefaultSectionRoots(CoreSession session)
            throws ClientException {
        DocumentModelList sectionRoots = new DocumentModelListImpl();
        for (String sectionRootNameType : sectionRootTypes) {
            DocumentModelList children = session.getChildren(
                    session.getRootDocument().getRef(), sectionRootNameType);
            sectionRoots.addAll(children);
        }
        return sectionRoots;
    }

}
