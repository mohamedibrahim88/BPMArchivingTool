package com.archiving.archivingTool.service;

import com.archiving.archivingTool.dto.bpm.CoachDefinitionNodeDTO;
import com.archiving.archivingTool.entity.bpm.BpmCoachView;
import com.archiving.archivingTool.entity.bpm.LswProcess;
import com.archiving.archivingTool.repository.bpm.BpmCoachViewRepository;
import com.archiving.archivingTool.repository.bpm.LswProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.text.html.Option;
import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProcessXmlService {
    @Autowired
    private final LswProcessRepository processRepository;

    @Autowired
    private final BpmCoachViewRepository coachViewRepo;

//    public List<CoachDefinitionNodeDTO> parseCoachDefinitions(String id, String versionId) throws Exception {
//        // Fetching process data based on id and versionId
//        Optional<LswProcess> optional = processRepository.getCoachViewIDs(id, versionId);
//
//        // Handle the case where the process data is not found
//        if (optional.isEmpty()) {
//            throw new RuntimeException("Process not found with id: " + id);
//        }
//
//        // Fetching BLOB data (stored as byte[]) and converting it to String
//        byte[] blobData = optional.get().getXmlData();  // Assuming this is the field storing the BLOB
//        if (blobData == null) {
//            throw new RuntimeException("XML BLOB data is null for process with id: " + id);
//        }
//
//        String xmlContent = new String(blobData, StandardCharsets.UTF_8);  // Convert BLOB (byte[]) to String
//
//        // Parse the XML
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        factory.setNamespaceAware(true);  // Ensuring namespace awareness
//        DocumentBuilder builder = factory.newDocumentBuilder();
//        Document doc = builder.parse(new ByteArrayInputStream(xmlContent.getBytes(StandardCharsets.UTF_8)));
//
//        XPathFactory xPathFactory = XPathFactory.newInstance();
//        XPath xpath = xPathFactory.newXPath();
//
//        // Using NamespaceContext to resolve dynamic namespaces
//        NamespaceContext ctx = new NamespaceContext() {
//            public String getNamespaceURI(String prefix) {
//                if ("ns19".equals(prefix)) {
//                    return "http://www.ibm.com/bpm/CoachDesignerNG";  // Define namespace for ns19 prefix
//                }
//                return XMLConstants.NULL_NS_URI;
//            }
//
//            public String getPrefix(String uri) {
//                return null;
//            }
//
//            public Iterator<String> getPrefixes(String uri) {
//                return null;
//            }
//        };
//        xpath.setNamespaceContext(ctx);
//
//        // Evaluate XPath expression to find all layoutItems
//        NodeList layoutItems = (NodeList) xpath.evaluate(
//                "//*[local-name()='coachDefinition']//*[local-name()='layoutItem']",
//                doc,
//                XPathConstants.NODESET
//        );
//
//        List<CoachDefinitionNodeDTO> results = new ArrayList<>();
//
//        // Loop through each layoutItem node and extract the relevant data
//        for (int i = 0; i < layoutItems.getLength(); i++) {
//            Node item = layoutItems.item(i);
//
//            // Extract label and viewUUID using XPath queries
//            String label = xpath.evaluate(".//*[local-name()='configData'][*[local-name()='optionName']='@label']/*[local-name()='value']", item);
//            String viewUUID = xpath.evaluate(".//*[local-name()='viewUUID']", item);
//
//            // Add to results as DTO
//            results.add(new CoachDefinitionNodeDTO(label, viewUUID));
//        }
//
//        return results;  // Return the list of parsed coach definitions
//    }


//    public List<CoachDefinitionNodeDTO> parseCoachDefinitions(String id, String versionId) throws Exception {
//        Optional<LswProcess> optional = processRepository.getCoachViewIDs(id, versionId);
//
//        if (optional.isEmpty()) {
//            throw new RuntimeException("Process not found with id: " + id);
//        }
//
//        String xmlContent = new String(optional.get().getXmlData(), StandardCharsets.UTF_8);
//
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        factory.setNamespaceAware(true);
//        DocumentBuilder builder = factory.newDocumentBuilder();
//        Document doc = builder.parse(new ByteArrayInputStream(xmlContent.getBytes(StandardCharsets.UTF_8)));
//
//        XPath xpath = XPathFactory.newInstance().newXPath();
//
//        // Setup Namespace for ns19
//        NamespaceContext ctx = new NamespaceContext() {
//            public String getNamespaceURI(String prefix) {
//                if ("ns19".equals(prefix)) {
//                    return "http://www.ibm.com/bpm/CoachDesignerNG";
//                }
//                return XMLConstants.NULL_NS_URI;
//            }
//
//            public String getPrefix(String namespaceURI) {
//                return null;
//            }
//
//            public Iterator<String> getPrefixes(String namespaceURI) {
//                return null;
//            }
//        };
//        xpath.setNamespaceContext(ctx);
//
//        List<CoachDefinitionNodeDTO> results = new ArrayList<>();
//
//        // Find all layoutItems
//        NodeList layoutItems = (NodeList) xpath.evaluate(
//                "//*[local-name()='coachDefinition']//*[local-name()='layout']//*[local-name()='layoutItem']",
//                doc,
//                XPathConstants.NODESET
//        );
//
//        for (int i = 0; i < layoutItems.getLength(); i++) {
//            Node layoutItem = layoutItems.item(i);
//
//            // First: Try to extract label/viewUUID from layoutItem itself
//            String label = safeEvaluate(xpath,
//                    ".//*[local-name()='configData'][*[local-name()='optionName']='@label']/*[local-name()='value']",
//                    layoutItem);
//            String viewUUID = safeEvaluate(xpath, ".//*[local-name()='viewUUID']", layoutItem);
//
//            if (label != null && !label.isEmpty()) {
//                results.add(new CoachDefinitionNodeDTO(label, viewUUID));
//            }
//
//            // Then: Check for contentBoxContrib > contributions
//            NodeList contribs = (NodeList) xpath.evaluate(
//                    ".//*[local-name()='contentBoxContrib']//*[local-name()='contributions']",
//                    layoutItem,
//                    XPathConstants.NODESET
//            );
//
//            for (int j = 0; j < contribs.getLength(); j++) {
//                Node contrib = contribs.item(j);
//
//                String nestedLabel = safeEvaluate(xpath,
//                        ".//*[local-name()='configData'][*[local-name()='optionName']='@label']/*[local-name()='value']",
//                        contrib);
//                String nestedViewUUID = safeEvaluate(xpath, ".//*[local-name()='viewUUID']", contrib);
//
//                if (nestedLabel != null && !nestedLabel.isEmpty()) {
//                    results.add(new CoachDefinitionNodeDTO(nestedLabel, nestedViewUUID));
//                }
//            }
//        }
//
//        return results;
//    }
//
//    private String safeEvaluate(XPath xpath, String expression, Node contextNode) {
//        try {
//            return xpath.evaluate(expression, contextNode);
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public List<CoachDefinitionNodeDTO> getLayoutTree(String taskId, String versionId,String couchViewVersion) throws Exception {
//        Optional<LswProcess> optional = processRepository.getCoachViewIDs(taskId, versionId);
//
//        if (optional.isEmpty()) {
//            throw new RuntimeException("Process not found");
//        }
//
//        byte[] xmlBytes = optional.get().getXmlData();  // Make sure `getXmlData()` returns byte[]
//        String xml = new String(xmlBytes, StandardCharsets.UTF_8);
//
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        factory.setNamespaceAware(true);
//        DocumentBuilder builder = factory.newDocumentBuilder();
//        Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
//
//        XPathFactory xPathFactory = XPathFactory.newInstance();
//        XPath xpath = xPathFactory.newXPath();
//
//        xpath.setNamespaceContext(new NamespaceContext() {
//            public String getNamespaceURI(String prefix) {
//                if ("ns19".equals(prefix)) return "http://www.ibm.com/bpm/CoachDesignerNG";
//                return XMLConstants.NULL_NS_URI;
//            }
//
//            public String getPrefix(String uri) { return null; }
//
//            public Iterator<String> getPrefixes(String uri) { return null; }
//        });
//
//        NodeList layoutItems = (NodeList) xpath.evaluate(
//                "//*[local-name()='coachDefinition']//*[local-name()='layoutItem']",
//                doc,
//                XPathConstants.NODESET
//        );
//
//        List<CoachDefinitionNodeDTO> result = new ArrayList<>();
//        for (int i = 0; i < layoutItems.getLength(); i++) {
//            Node item = layoutItems.item(i);
//            if (item.getParentNode().getLocalName().equals("layout")) {
//                result.add(buildTree(item, xpath,couchViewVersion));
//            }
//        }
//
//        return result;
//    }
//
//    private CoachDefinitionNodeDTO buildTree(Node layoutItem, XPath xpath,String couchViewVersion ) throws XPathExpressionException {
//        String label = xpath.evaluate(".//*[local-name()='configData'][*[local-name()='optionName']='@label']/*[local-name()='value']", layoutItem);
//        String viewUUID = xpath.evaluate(".//*[local-name()='viewUUID']", layoutItem);
//        System.out.println("view uuid" + viewUUID);
//        viewUUID = viewUUID.replace("64.", "");
//        System.out.println("view uuid" + viewUUID);
//        Optional<BpmCoachView> bpmCouchView = bpmCoachViewRepository.getCoachView(viewUUID, couchViewVersion);
//
//        System.out.println("bpmcoach view " + bpmCouchView);
//        CoachDefinitionNodeDTO node = new CoachDefinitionNodeDTO();
//        node.setLabel(label);
//        if (!bpmCouchView.isEmpty()) {
//            if (bpmCouchView.get().getLastModifiedBy().equals("9"))
//            {
//                node.setControlType(bpmCouchView.get().getName());
//                node.setViewUUID(null);
//        }
//    }else {
//            node.setViewUUID(viewUUID);
//        }
//
//        // Recursively check for children under contentBoxContrib
//        NodeList contribs = (NodeList) xpath.evaluate(
//                ".//*[local-name()='contentBoxContrib']/*[local-name()='contributions']",
//                layoutItem,
//                XPathConstants.NODESET
//        );
//
//        List<CoachDefinitionNodeDTO> children = new ArrayList<>();
//        for (int i = 0; i < contribs.getLength(); i++) {
//            Node contrib = contribs.item(i);
//            children.add(buildTree(contrib, xpath,couchViewVersion));
//        }
//
//        node.setChildren(children.isEmpty() ? null : children);
//        return node;
//    }

//    public List<CoachDefinitionNodeDTO> getLayoutTree(String taskId, String versionId,String coachViewVersion) throws Exception {
//        LswProcess proc = processRepository.getCoachViewIDs(taskId, versionId)
//                .orElseThrow(() -> new RuntimeException("Process not found"));
//
//        String xml = new String(proc.getXmlData(), StandardCharsets.UTF_8);
//        Document doc = parseXml(xml);
//        XPath xpath = createXPath();
//
//        NodeList layoutItems = (NodeList) xpath.evaluate(
//                "//*[local-name()='coachDefinition']//*[local-name()='layoutItem']",
//                doc, XPathConstants.NODESET);
//
//        List<CoachDefinitionNodeDTO> result = new ArrayList<>();
//        for (int i = 0; i < layoutItems.getLength(); i++) {
//            Node item = layoutItems.item(i);
//            if ("layout".equals(item.getParentNode().getLocalName())) {
//                result.add(buildTree(item, xpath,versionId));
//            }
//        }
//        return result;
//    }
//
//    private CoachDefinitionNodeDTO buildTree(Node node, XPath xpath,String viewVersion) throws Exception {
//        String label = xpath.evaluate(
//                ".//*[local-name()='configData'][*[local-name()='optionName']='@label']/*[local-name()='value']",
//                node);
//        String uuid = xpath.evaluate(".//*[local-name()='viewUUID']", node);
//
//        CoachDefinitionNodeDTO dto = new CoachDefinitionNodeDTO(label, uuid, "",new ArrayList<>());
//
//        // 1) Load nested layout children
//        NodeList items = (NodeList) xpath.evaluate(
//                ".//*[local-name()='contentBoxContrib']//*[local-name()='contributions']",
//                node, XPathConstants.NODESET);
//        for (int i = 0; i < items.getLength(); i++) {
//            dto.getChildren().add(buildTree(items.item(i), xpath,viewVersion));
//        }
//
//        // 2) If there is a viewUUID, fetch and parse its BLOB children
//        if (uuid != null && !uuid.isEmpty()) {
//           Optional<BpmCoachView> view = bpmCoachViewRepo.getCoachView(uuid,viewVersion);
//            if (!view.isEmpty()  && view.get().getXmlData() != null) {
//                Document childDoc = parseXml(new String(view.get().getXmlData(), StandardCharsets.UTF_8));
//                XPath xp2 = createXPath();
//                NodeList childLayouts = (NodeList) xp2.evaluate(
//                        "//*[local-name()='layoutItem']",
//                        childDoc, XPathConstants.NODESET);
//                for (int j = 0; j < childLayouts.getLength(); j++) {
//                    dto.getChildren().add(buildTree(childLayouts.item(j), xp2,viewVersion));
//                }
//            }
//        }
//
//        return dto;
//    }
//
//    private Document parseXml(String xml) throws Exception {
//        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
//        f.setNamespaceAware(true);
//        DocumentBuilder b = f.newDocumentBuilder();
//        return b.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
//    }
//
//    private XPath createXPath() {
//        XPath xp = XPathFactory.newInstance().newXPath();
//        xp.setNamespaceContext(new NamespaceContext() {
//            public String getNamespaceURI(String p) {
//                return "ns19".equals(p) ? "http://www.ibm.com/bpm/CoachDesignerNG" : XMLConstants.NULL_NS_URI;
//            }
//            public String getPrefix(String uri) { return null; }
//            public Iterator<String> getPrefixes(String uri) { return null; }
//        });
//        return xp;
//    }

    ///////V3

    public List<CoachDefinitionNodeDTO> getLayoutTree(String taskId, String versionId) throws Exception {
        Optional<LswProcess> optional = processRepository.getCoachViewIDs(taskId, versionId);

        if (optional.isEmpty()) {
            throw new RuntimeException("Process not found");
        }

        // Parse the initial XML from BPM_PROCESS
        String xmlContent = new String(optional.get().getXmlData(), StandardCharsets.UTF_8);
        Document doc = parseXml(xmlContent);
        XPath xpath = createXPath();

        NodeList layoutItems = (NodeList) xpath.evaluate(
                "//*[local-name()='coachDefinition']//*[local-name()='layoutItem']",
                doc,
                XPathConstants.NODESET
        );

        List<CoachDefinitionNodeDTO> result = new ArrayList<>();
        for (int i = 0; i < layoutItems.getLength(); i++) {
            Node node = layoutItems.item(i);
            if (node.getParentNode().getLocalName().equals("layout")) {
                result.add(buildTree(node, xpath));
            }
        }

        return result;
    }

    private String safeEvaluate(XPath xpath, String expression, Node contextNode) {
        try {
            return xpath.evaluate(expression, contextNode);
        } catch (Exception e) {
            return null;
        }
    }
    private CoachDefinitionNodeDTO buildTree(Node node, XPath xpath) throws Exception {
        String label = safeEvaluate(xpath,
                ".//*[local-name()='configData'][*[local-name()='optionName']='@label']/*[local-name()='value']",
                node);
        String viewUUID = safeEvaluate(xpath, ".//*[local-name()='viewUUID']", node);
        viewUUID = viewUUID.replace("64.", "");
        String binding = safeEvaluate(xpath, ".//*[local-name()='binding']", node); // 👈 extract binding

        CoachDefinitionNodeDTO dto = new CoachDefinitionNodeDTO();
        dto.setLabel(label);
        dto.setViewUUID(viewUUID);
        dto.setBinding(binding);
        dto.setChildren(new ArrayList<>());

        // First: Add any layout-level children
        NodeList layoutContribs = (NodeList) xpath.evaluate(
                ".//*[local-name()='contentBoxContrib']//*[local-name()='contributions']",
                node, XPathConstants.NODESET);
        for (int j = 0; j < layoutContribs.getLength(); j++) {
            dto.getChildren().add(buildTree(layoutContribs.item(j), xpath));
        }

        // If viewUUID exists, consult BPM_COACH_VIEW:
        if (viewUUID != null && !viewUUID.isEmpty()) {
            Optional<BpmCoachView> view = coachViewRepo.getCoachView(viewUUID);
            if (!view.isEmpty() && view.get().getXmlData() != null) {
                // If user=9, it's a leaf: set controlType, clear viewUUID, no further recurse
                if ("9".equals(view.get().getLastModifiedBy())) {
                    dto.setControlType(view.get().getName());
                    dto.setViewUUID(null);
                } else {
                    // Not leaf: parse nested XML and add children
                    Document childDoc = parseXml(new String(view.get().getXmlData(), StandardCharsets.UTF_8));
                    XPath childXp = createXPath();
                    NodeList nested = (NodeList) childXp.evaluate(
                            "//*[local-name()='layoutItem']",
                            childDoc, XPathConstants.NODESET);
                    for (int k = 0; k < nested.getLength(); k++) {
                        dto.getChildren().add(buildTree(nested.item(k), childXp));
                    }
                }
            }
        }

        return dto;
    }
    private Document parseXml(String xmlContent) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new ByteArrayInputStream(xmlContent.getBytes(StandardCharsets.UTF_8)));
    }

    private XPath createXPath() {
        XPath xpath = XPathFactory.newInstance().newXPath();

        // Add namespace context for ns19 (CoachDesignerNG)
        xpath.setNamespaceContext(new NamespaceContext() {
            @Override
            public String getNamespaceURI(String prefix) {
                if ("ns19".equals(prefix)) {
                    return "http://www.ibm.com/bpm/CoachDesignerNG";
                }
                return XMLConstants.NULL_NS_URI;
            }

            @Override
            public String getPrefix(String namespaceURI) {
                return null;
            }

            @Override
            public Iterator<String> getPrefixes(String namespaceURI) {
                return null;
            }
        });

        return xpath;
    }

}



