package fr.insa.ged.config.listeners;

import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.LifeCycleConstants;
import org.nuxeo.ecm.core.api.event.DocumentEventTypes;
import org.nuxeo.ecm.core.event.Event;
import org.nuxeo.ecm.core.event.EventContext;
import org.nuxeo.ecm.core.event.impl.DocumentEventContext;
import org.nuxeo.ecm.platform.publisher.listeners.DomainEventsListener;

/**
 * Implement the same behavior as the {@link DomainEventsListener} for INSA
 * custom Domain type: "DomainPublication".
 *
 * @author Antoine Taillefer
 */
public class INSADomainEventsListener extends DomainEventsListener {

    public void handleEvent(Event event) throws ClientException {
        EventContext ctx = event.getContext();
        if (ctx instanceof DocumentEventContext) {
            DocumentEventContext docCtx = (DocumentEventContext) ctx;
            DocumentModel doc = docCtx.getSourceDocument();
            if (doc != null
                    && ("Domain".equals(doc.getType()) || "DomainPublication".equals(doc.getType()))) {
                String eventName = event.getName();
                if (DocumentEventTypes.DOCUMENT_CREATED.equals(eventName)) {
                    registerNewPublicationTrees(doc);
                } else if (DocumentEventTypes.DOCUMENT_UPDATED.equals(eventName)) {
                    // re-register in case of title update for instance
                    unregisterPublicationTrees(doc);
                    registerNewPublicationTrees(doc);
                } else if (DocumentEventTypes.DOCUMENT_REMOVED.equals(eventName)) {
                    unregisterPublicationTrees(doc);
                } else if (LifeCycleConstants.TRANSITION_EVENT.equals(eventName)) {
                    handleDomainLifeCycleChanged(docCtx, doc);
                }
            }
        }
    }
}
