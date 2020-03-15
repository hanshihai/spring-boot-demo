package com.hans.boot.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@EnableAutoConfiguration
public class MainResource {


    @RequestMapping("/sample")
    public String main(@RequestParam("text") String text) {
        try {
            System.out.println("Here is the input parameter :" + text);
            String result = Util.load("sample.json");
            return result;
        }catch(Exception e) {
            throw new RuntimeException("Failed to handle request /sample", e);
        }
    }

    @RequestMapping(value="/mediaEntries/tests", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<List<Long>> checkParameters(@RequestParam("entryIds") Long[] entryIds) {
        List<Long> result = new ArrayList<>();
        Arrays.stream(entryIds).forEach(id -> result.add(id));
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value="/mediaEntries/tests", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Long>> checkParameters(@RequestBody Long[] entryIds) {
        List<Long> result = new ArrayList<>();
        Arrays.stream(entryIds).forEach(id -> result.add(id));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/mediaEntries/zipfiles")
    public StreamingResponseBody downloadZipFiles(HttpServletResponse response) throws FileNotFoundException {
        logger.info("got here: downloadFiles for entry ");

        /*MediaEntry mediaEntry = mediaEntriesRepository.findOne(entryId);

        if (mediaEntry == null) {
            throw new NotFoundException("MediaEntry is not found");
        }*/

        response.setContentType("application/x-msdownload;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"downloads.zip\"");

        InputStream[] inputStreams = new InputStream[3];
        InputStream input1 = new FileInputStream(new File("C:/repository/prints/passort-recipe.pdf"));
        InputStream input2 = new FileInputStream(new File("C:/repository/elpworkspace/.metadata/version.ini"));
        inputStreams[0] = input1;
        inputStreams[1] = input2;
        inputStreams[2] = input1;

        String[] names = new String[] {"passort-recipe.pdf", "version.ini", "version.ini"};
        //InputStream inputStream = storageService.loadFile(mediaEntry);

        return new StreamingResponseBody() {
            @Override
            public void writeTo(OutputStream outputStream) throws IOException {
                ZipOutputStream zipOutpuStream = new ZipOutputStream(outputStream);
                int index = 0;
                byte[] data = new byte[1024];
                for(InputStream inputStream : inputStreams) {
                    zipOutpuStream.putNextEntry(new ZipEntry(""+index+"/"+names[index]));
                    int nRead;
                    while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                        zipOutpuStream.write(data, 0, nRead);
                    }
                    zipOutpuStream.flush(); //notice it impacts the close entry or not?
                    zipOutpuStream.closeEntry();
                    index++;
                }
                zipOutpuStream.close();
            }
        };
    }

}

