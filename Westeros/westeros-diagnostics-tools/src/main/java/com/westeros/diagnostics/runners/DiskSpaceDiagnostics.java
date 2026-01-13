package com.westeros.diagnostics.runners;

import com.westeros.diagnostics.services.contract.Diagnostics;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DiskSpaceDiagnostics implements IDiagnose {

    private static final double REQUIRED_FREE_SPACE_PERCENTAGE = 0.05;

    @Override
    public String getName() {
        return "Disk Space Diagnostics";
    }

    @Override
    public String getDescription() {
        return "Checks the available disk space on the partition.";
    }

    @Override
    public Diagnostics run() {
        Diagnostics diagnostics = new Diagnostics();
        diagnostics.setName(getName());
        diagnostics.setDescription(getDescription());

        try {
            File root = new File(".");
            long totalSpace = root.getTotalSpace();
            long freeSpace = root.getFreeSpace();

            long thresholdInfo = (long) (totalSpace * REQUIRED_FREE_SPACE_PERCENTAGE);

            if (freeSpace > thresholdInfo) {
                diagnostics.setSuccess(true);
                diagnostics.setErrorMessage(String.format("OK. Free space: %d MB (%.2f%%). Required > 5%%.",
                        freeSpace / 1024 / 1024,
                        ((double) freeSpace / totalSpace) * 100));
            } else {
                diagnostics.setSuccess(false);
                diagnostics.setErrorMessage(String.format("Low disk space. Available: %d MB (%.2f%%). Required > 5%%.",
                        freeSpace / 1024 / 1024,
                        ((double) freeSpace / totalSpace) * 100));
            }
        } catch (Exception e) {
            diagnostics.setSuccess(false);
            diagnostics.setErrorMessage("Failed to check disk space: " + e.getMessage());
        }

        return diagnostics;
    }
}
