package fr.insa.ged.config.publisher.web;

import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.security.SecurityConstants;
import org.nuxeo.ecm.platform.publisher.web.PublishActionsBean;

@Name("publishActions")
@Scope(ScopeType.CONVERSATION)
@Install(precedence = Install.DEPLOYMENT)
public class INSAPublishActionsBean extends PublishActionsBean {

    private static final long serialVersionUID = -444222430408813621L;

    public String getFormattedPath(String path) throws ClientException {
        DocumentModel docModel = getDocumentModelFor(path);
        return docModel != null ? getFormattedPath(docModel) : path;
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
        if (documentManager.hasPermission(documentModel.getParentRef(),
                SecurityConstants.READ)) {
            parentDocument = documentManager.getDocument(documentModel.getParentRef());
            getPathFragments(parentDocument, pathFragments);
        }
    }

}
