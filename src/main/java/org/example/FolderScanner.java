package org.example;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class FolderScanner extends RecursiveTask<ScannerResult> {
    Path pathToFolder;

    public FolderScanner(Path pathToFolder) {
        this.pathToFolder = pathToFolder;
    }

    @Override
    protected ScannerResult compute() {
        ScannerResult stats = new ScannerResult();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(pathToFolder)) {
            List<FolderScanner> listSubTasks = new ArrayList<>();
            for (Path path : stream) {
                if (Files.isDirectory(path)) {
                    stats.folderCount++;
                    FolderScanner subTask = new FolderScanner(path);
                    subTask.fork();
                    listSubTasks.add(subTask);
                } else if (Files.isRegularFile(path)) {
                    stats.fileCount++;
                    stats.totalSize += Files.size(path);
                }
            }
            for (FolderScanner task : listSubTasks) {
                stats.add(task.join());
            }
        } catch (IOException e) {
            System.err.println("Error while work with : " + pathToFolder);
        }
        return stats;
    }
}
