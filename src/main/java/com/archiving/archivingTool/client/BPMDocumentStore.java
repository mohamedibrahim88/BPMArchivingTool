package com.archiving.archivingTool.client;

import com.archiving.archivingTool.dto.archiving.DocumentResponseDto;
import com.filenet.api.collection.ContentElementList;
import com.filenet.api.collection.DocumentSet;
import com.filenet.api.collection.VersionableSet;
import com.filenet.api.core.*;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;
import com.filenet.api.util.UserContext;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.security.auth.Subject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BPMDocumentStore {

    private final String basePath = "/data/export";

    public  Connection getCEConnection() {
        Connection conn = null;
        try {
            String ceURI = "http://192.168.1.20:9080/wsi/FNCEWS40MTOM";
            String username = "pcdeadmin";
            String password = "pcdeadmin";

            conn = Factory.Connection.getConnection(ceURI);

            Subject subject = UserContext.createSubject(conn, username, password, null);
            UserContext.get().pushSubject(subject);

        } catch (Exception e1) {
            e1.printStackTrace();
            throw new RuntimeException(e1.getMessage());
        }

        System.out.println("CE Connection: " + conn);
        return conn;
    }

    public static ObjectStore getObjectStore(Connection conn, String osName) {
        Domain domain = Factory.Domain.fetchInstance(conn, null, null);
        ObjectStore os = Factory.ObjectStore.fetchInstance(domain, osName, null);

        System.out.println("✅ Connected to ObjectStore: " + osName);
        return os;
    }

    public void exportDocumentsWithVersions(
            String instanceId,
            String exportFolder) throws Exception {

        String query = "SELECT Id FROM Document WHERE IBM_BPM_Document_ProcessInstanceIdString = '" + instanceId + "'";
        SearchSQL sql = new SearchSQL(query);
        SearchScope scope = new SearchScope(getObjectStore(getCEConnection(),"docs"));

        DocumentSet docs = (DocumentSet) scope.fetchObjects(sql, null, null, Boolean.TRUE);

        File folder = new File(exportFolder);
        if (!folder.exists()) folder.mkdirs();

        JSONArray metadataArray = new JSONArray();

        Iterator<?> it = docs.iterator();

        while (it.hasNext()) {

            Document doc = (Document) it.next();
            doc.refresh();

            VersionSeries vs = doc.get_VersionSeries();
            VersionableSet versions = vs.get_Versions();

            Iterator<?> vIt = versions.iterator();

            while (vIt.hasNext()) {

                Document versionDoc = (Document) vIt.next();
                versionDoc.refresh();
                // +versionDoc.get_Id().toString()
                String fileName =  versionDoc.get_Id().toString();
                String filePath = exportFolder + File.separator + fileName;

                // Download content
                ContentElementList contentList = versionDoc.get_ContentElements();
                if (contentList != null && !contentList.isEmpty()) {

                    ContentTransfer ct = (ContentTransfer) contentList.get(0);
                    InputStream stream = ct.accessContentStream();
                    FileOutputStream fos = new FileOutputStream(filePath);

                    byte[] buffer = new byte[4096];
                    int bytesRead;

                    while ((bytesRead = stream.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }

                    fos.close();
                    stream.close();
                }

                // Save metadata
                JSONObject meta = new JSONObject();
                meta.put("Id", versionDoc.get_Id().toString());
                meta.put("Name", versionDoc.get_Name());
                meta.put("Version", versionDoc.get_MajorVersionNumber());
                meta.put("Creator", versionDoc.get_Creator());
                meta.put("DateCreated", versionDoc.get_DateCreated());
                meta.put("InstanceId",versionDoc.getProperties().getStringValue("IBM_BPM_Document_ProcessInstanceIdString"));

                metadataArray.put(meta);
            }
        }

        writeMetadataToJson(metadataArray, exportFolder + File.separator + "metadata.json");

        System.out.println("Export Completed Successfully");
    }

    public static void writeMetadataToJson(JSONArray metadataArray, String filePath) throws Exception {

        FileWriter file = new FileWriter(filePath);
        file.write(metadataArray.toString(4));
        file.flush();
        file.close();

        System.out.println("Metadata file created: " + filePath);
    }


    /// / fetch document from file server

    public List<DocumentResponseDto> getDocuments(String instanceId) throws Exception {

        String instanceFolderPath = basePath + File.separator + instanceId;
        File instanceFolder = new File(instanceFolderPath);

        if (!instanceFolder.exists()) {
            throw new RuntimeException("Instance folder not found");
        }

        File metadataFile = new File(instanceFolderPath + File.separator + "metadata.json");

        if (!metadataFile.exists()) {
            throw new RuntimeException("metadata.json not found");
        }

        // Read metadata.json
        String content = new String(
                Files.readAllBytes(metadataFile.toPath()),
                StandardCharsets.UTF_8
        );

        JSONArray jsonArray = new JSONArray(content);

        List<DocumentResponseDto> result = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject obj = jsonArray.getJSONObject(i);

            DocumentResponseDto dto = new DocumentResponseDto();

            dto.setId(obj.getString("Id"));
            dto.setName(obj.getString("Name"));
            dto.setVersion(obj.getInt("Version"));
            dto.setCreator(obj.optString("Creator"));
            dto.setDateCreated(obj.optString("DateCreated"));
            dto.setInstanceId(obj.getString("InstanceId"));

            dto.setDownloadUrl(
                    "/api/instances/" + instanceId + "/documents/download/" + dto.getName()
            );

            result.add(dto);
        }

        return result;
    }
}
