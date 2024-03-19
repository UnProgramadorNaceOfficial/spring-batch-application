package com.batch.steps;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Slf4j
public class ItemDescompressStep implements Tasklet {

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        log.info("-------------------> Inicio del paso de decompresióon del archivo <-------------------");

        Resource resource = resourceLoader.getResource("classpath:files/persons.zip");
        String filePath = resource.getFile().getAbsolutePath();

        ZipFile zipFile = new ZipFile(filePath);

        File destDir = new File(resource.getFile().getParent(), "destino");

        if(!destDir.exists()) {
            destDir.mkdir();
        }

        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            File file = new File(destDir, entry.getName());

            if (entry.isDirectory()) {
                file.mkdirs();
            } else {

                InputStream is = zipFile.getInputStream(entry);
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int len;

                while ((len = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                is.close();
            }
        }

        zipFile.close();

        log.info("-------------------> Fin del paso de decompresióon del archivo <-------------------");

        return RepeatStatus.FINISHED;
    }

}
