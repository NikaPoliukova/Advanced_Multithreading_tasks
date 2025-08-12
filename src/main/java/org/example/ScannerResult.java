package org.example;

public class ScannerResult {

    long fileCount;
    long folderCount;
    long totalSize;

    public ScannerResult() {
    }

    public ScannerResult(long fileCount, long folderCount, long totalSize) {
        this.fileCount = fileCount;
        this.folderCount = folderCount;
        this.totalSize = totalSize;
    }

    void add(ScannerResult other) {
        this.fileCount += other.fileCount;
        this.folderCount += other.folderCount;
        this.totalSize += other.totalSize;
    }
}
