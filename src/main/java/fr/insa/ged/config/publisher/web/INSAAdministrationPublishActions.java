package fr.insa.ged.config.publisher.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.platform.publisher.helper.RootSectionsFinder;
import org.nuxeo.ecm.platform.publisher.web.AdministrationPublishActions;

import fr.insa.ged.config.publisher.helper.INSARootSectionsFinderHelper;

@Name("adminPublishActions")
@Scope(ScopeType.CONVERSATION)
@Install(precedence = Install.DEPLOYMENT)
public class INSAAdministrationPublishActions extends
        AdministrationPublishActions {

    private static final long serialVersionUID = -4075227258654134432L;

    private static final Log log = LogFactory.getLog(INSAAdministrationPublishActions.class);

    protected RootSectionsFinder getRootFinder() {
        if (rootFinder == null) {
            rootFinder = INSARootSectionsFinderHelper.getRootSectionsFinder(documentManager);
        }
        return rootFinder;
    }

    @Override
    public String getFormattedPath(DocumentModel documentModel)
            throws ClientException {
        List<String> pathFragments = new ArrayList<String>();
        getPathFragments(documentModel, pathFragments);
        return formatPathFragments(pathFragments);
    }

    @Override
    protected void getPathFragments(DocumentModel documentModel,
            List<String> pathFragments) throws ClientException {
        String pathElementName = documentModel.getTitle();
        String translatedPathElement = resourcesAccessor.getMessages().get(
                pathElementName);
        pathFragments.add(translatedPathElement);
        if ("Domain".equals(documentModel.getType())
                || "DomainPublication".equals(documentModel.getType())) {
            return;
        }

        DocumentModel parentDocument;
        try {
            parentDocument = documentManager.getDocument(documentModel.getParentRef());
        } catch (Exception e) {
            log.error("Error building path", e);
            return;
        }
        getPathFragments(parentDocument, pathFragments);
    }
}
