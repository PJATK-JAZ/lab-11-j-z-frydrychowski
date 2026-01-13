package com.westeros.diagnostics;

import com.westeros.diagnostics.runners.IDiagnose;
import com.westeros.diagnostics.services.contract.Diagnostics;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;


@Component
@RequiredArgsConstructor
public class DatabaseConnectivityDiagnostics implements IDiagnose {

    private final DataSource dataSource;

    @Override
    public String getName() {
        return "Database Connectivity Diagnostics (Web API)";
    }

    @Override
    public String getDescription() {
        return "Checks if the Web API can connect to the database.";
    }

    @Override
    public Diagnostics run() {
        Diagnostics diagnostics = new Diagnostics();
        diagnostics.setName(getName());
        diagnostics.setDescription(getDescription());

        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(2)) {
                diagnostics.setSuccess(true);
                diagnostics.setErrorMessage("Connected to database successfully.");
            } else {
                diagnostics.setSuccess(false);
                diagnostics.setErrorMessage("Database connection is invalid.");
            }
        } catch (Exception e) {
            diagnostics.setSuccess(false);
            diagnostics.setErrorMessage("Database connection failed: " + e.getMessage());
        }

        return diagnostics;
    }
}